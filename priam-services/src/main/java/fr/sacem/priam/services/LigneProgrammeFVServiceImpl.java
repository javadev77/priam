package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.fv.LigneProgrammeFVService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

import static fr.sacem.priam.common.TypeUtilisationEnum.FV_FONDS_06;
import static fr.sacem.priam.services.LigneProgrammeCMSServiceImpl.AJOUT;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service("ligneProgrammeFVService")
public class LigneProgrammeFVServiceImpl implements LigneProgrammeService, LigneProgrammeFVService {

    public static final String IDE_12 = "ide12";
    public static final String MANUEL = "MANUEL";
    public static final String AUTOMATIQUE = "AUTOMATIQUE";
    public static final String CORRIGE = "CORRIGE";
    private static final String SOMME = "SOMME";
    public static final Long NBRDIF_FD12 = 1L;
    private static final Double MT_FD06 = 0.0;
    public static final int SELECTION = 1;
    public static final String POINTS_MONTANT = "pointsMontant";
    public static final String CDE_UTIL = "XXX";
    public static final String CDE_CISAC_058 = "058";


    @Autowired
    private LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private FichierService fichierService;

    @Override
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeFVDao.findIDE12sByProgramme(ide12, programme);
    }

    @Override
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeFVDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Override
    /*@Transactional*/
    @Transactional("transactionManager")
    public Page<SelectionCMSDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {
        Programme programme = programmeDao.findOne(criteria.getNumProg());

        Sort customSort = createCustomSort(pageable, programme);
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), customSort);

        return  ligneProgrammeFVDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(), pageRequest);
    }

    protected Sort createCustomSort(Pageable pageable, Programme programme) {
        Sort sort = pageable.getSort();

        if(sort == null)
            return sort;

        Sort.Order sortBy = sort.iterator().next();

        String sortProp = sortBy.getProperty();
        if("pointsMontant".equals(sortProp) ||
                "sum(nbrDifEdit)".equals(sortProp) ||
                "sum(mtEdit)".equals(sortProp)||
                "mt".equals(sortProp)){
            String typeUtilCode = programme.getTypeUtilisation().getCode();
            Sort.Direction direction = sortBy.getDirection();
            if (FV_FONDS_06.getCode().equals(typeUtilCode)) {
                sort = JpaSort.unsafe(direction, "mtEdit");
            }

            if (TypeUtilisationEnum.FV_FONDS_12.getCode().equals(typeUtilCode)) {
                sort = JpaSort.unsafe(direction, "nbrDifEdit");
            }
        }

        return sort;
    }

    @Override
    public Map<String,Object> calculerCompteurs(String numProg, String statut) {
        Map<String, Object> result = new HashMap<>();
        result.put(AUTOMATIQUE, 0L);
        result.put(CORRIGE, 0L);
        result.put(MANUEL, 0L);
        result.put(SOMME, 0.0d);

        Integer selection = SELECTION;

        List<Object> prog = ligneProgrammeFVDao.compterOuvres(numProg, selection);

        for (Object indicateur : prog) {
            Object[] indObjects = (Object[]) indicateur;
            result.put((String) indObjects[1], ((BigInteger) indObjects[0]).longValue());
        }
        result.put(SOMME, ligneProgrammeFVDao.calculerPointsMontantOeuvres(numProg, selection));

        return result;
    }

    @Override
    public void modifierPointsTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes, Boolean isSelected, UserDTO userDTO) {
        Programme prog = programmeDao.findByNumProg(numProg);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                String ajout = obj.get(AJOUT);

                LigneProgrammeFV inputLigneFV = createLigneProgrammeFVFromInput(numProg, obj);
                inputLigneFV.setSelectionEnCours(isSelected);

                if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD12.getCode())) {
                    String nbrDifValue = obj.get(POINTS_MONTANT);
                    Long nbrDifEdit = nbrDifValue != null && !nbrDifValue.equals("") ? Long.valueOf(nbrDifValue) : 0L;
                    inputLigneFV.setNbrDifEdit(nbrDifEdit);
                    List<LigneProgrammeFV> oeuvresAuto = ligneProgrammeFVDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneFV.getIde12());

                    if(AUTOMATIQUE.equals(ajout)) {
                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            Long sumTotal = sumOfNbrDif(oeuvresAuto);
                            if( !nbrDifEdit.equals(sumTotal)) {
                                //inputLigneCMS.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());
                                ajouterOeuvreManuel(inputLigneFV, userDTO);
                            }
                        }
                    } else if(CORRIGE.equals(ajout)){
                        correctionOeuvreCorrige(numProg, inputLigneFV, userDTO);
                    } else {
                        ligneProgrammeFVDao.updatePointsTemporaireByNumProgramme(numProg, inputLigneFV.getIde12(), nbrDifEdit);
                    }
                } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD06.getCode())) {
                    String mtValue = obj.get(POINTS_MONTANT);
                    Double mtEdit = mtValue != null && !mtValue.equals("") ? Double.valueOf(mtValue) : 0.0d;
                    inputLigneFV.setMtEdit(mtEdit);
                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeFV> oeuvresAuto = ligneProgrammeFVDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneFV.getIde12());

                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            Double sumTotal = sumOfMt(oeuvresAuto);
                            if( !mtEdit.equals(sumTotal)) {
                                ajouterOeuvreManuel(inputLigneFV, userDTO);
                            }
                        }
                    } else if(CORRIGE.equals(ajout)){
                        correctionOeuvreCorrige(numProg, inputLigneFV, userDTO);
                    } else {
                        ligneProgrammeFVDao.updatePointsMtTemporaireByNumProgramme(numProg, inputLigneFV.getIde12(), mtEdit);
                    }

                }

            }
        }
    }

    @Transactional
    @Override
    public void ajouterOeuvreManuel(LigneProgrammeFV input, UserDTO userDTO) {
        Programme programme = programmeDao.findOne(input.getNumProg());

        /*Journal journal = createJournal(input, programme);
        journal.setUtilisateur(userDTO.getUserId());
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();

        situationApres.setSituation(getPointsOrMtAsString(programme, input));*/

        List<LigneProgrammeFV> founds = ligneProgrammeFVDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
        if(founds != null && !founds.isEmpty()) {
            if(!isBackToEtatAuto(programme, input, founds)){
                input.setAjout(CORRIGE);
                LigneProgrammeFV oeuvreManuel = createOeuvreManuel(input, programme);
                founds.forEach( found -> {
                    found.setOeuvreManuel(oeuvreManuel);
                    ligneProgrammeFVDao.saveAndFlush(found);
                });
            }


            /*SareftrTyputil typeUtilisation = programme.getTypeUtilisation();
            if(TypeUtilisationPriam.SONOFRA.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfMt(founds)));
                situationApres.setSituation(String.valueOf(input.getMt()));
            } else if(TypeUtilisationPriam.SONOANT.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfNbrDif(founds)));
                situationApres.setSituation(String.valueOf(input.getNbrDif()));
            }

            journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());*/
        } else {
            LigneProgrammeFV oeuvreCorrigeFound = ligneProgrammeFVDao.findOeuvreCorrigeByIde12(input.getNumProg(), input.getIde12());
            List<LigneProgrammeFV> oeuvresAutoLinkCorrige = ligneProgrammeFVDao.findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
            if(oeuvreCorrigeFound != null) {
                /*situationAvant.setSituation(getPointsOrMtAsString(programme, oeuvreCorrigeFound));
                journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());*/
                if(!isBackToEtatAuto(programme, input, oeuvresAutoLinkCorrige)){
                    oeuvreCorrigeFound.setAjout(CORRIGE);
                    updateOeuvre(input, programme, oeuvreCorrigeFound);
                } else {
                    deleteOeuvreCorrigeLinkAuto(input.getNumProg(), input);
                }
            } else {
                LigneProgrammeFV oeuvreManuelFound = ligneProgrammeFVDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
                if(oeuvreManuelFound != null) {
                    oeuvreManuelFound.setAjout(MANUEL);
                    //situationAvant.setSituation(getPointsOrMtAsString(programme, oeuvreManuelFound));
                    updateOeuvre(input, programme, oeuvreManuelFound);

                    //journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());

                } else {
                    input.setAjout(MANUEL);

                    input.setMtEdit(input.getMt());
                    input.setNbrDifEdit(input.getNbrDif());
                    createOeuvreManuel(input, programme);

                    //situationAvant.setSituation("0");
                    //journal.setEvenement(TypeLog.AJOUT_OEUVRE.getEvenement());

                }
            }
        }

        /*journal.getListSituationAvant().add(situationAvant);
        journal.getListSituationApres().add(situationApres);
        journalDao.saveAndFlush(journal);*/
    }


    private void deleteOeuvreCorrigeLinkAuto(String numProg, LigneProgrammeFV inputLigneFV) {
        LigneProgrammeFV oeuvreManuelFound = ligneProgrammeFVDao.findOeuvreCorrigeByIde12(numProg, inputLigneFV.getIde12());
        deleteOeuvreManuel(oeuvreManuelFound);
    }

    @Override
    public void deleteOeuvreManuel(LigneProgrammeFV oeuvreManuelFound) {
        if(oeuvreManuelFound != null) {

            List<LigneProgrammeFV> oeuvresAutoByIdOeuvreManuel = ligneProgrammeFVDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            for (LigneProgrammeFV oeuvreAuto : oeuvresAutoByIdOeuvreManuel) {
                //oeuvreAuto.setSelection(FALSE);
                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
                oeuvreAuto.setMtEdit(oeuvreAuto.getMt());
                oeuvreAuto.setNbrDifEdit(oeuvreAuto.getNbrDif());
                ligneProgrammeFVDao.save(oeuvreAuto);
            }

            ligneProgrammeFVDao.delete(oeuvreManuelFound);
            ligneProgrammeFVDao.flush();
        }
    }

    private void updateOeuvre(LigneProgrammeFV input, Programme programme, LigneProgrammeFV oeuvreCorrigeFound) {
        oeuvreCorrigeFound.setCdeCisac(CDE_CISAC_058);
        oeuvreCorrigeFound.setCdeFamilTypUtil(programme.getFamille().getCode());
        oeuvreCorrigeFound.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        //oeuvreCorrigeFound.setNbrDif(input.getNbrDif());
        oeuvreCorrigeFound.setNbrDifEdit(input.getNbrDifEdit());
        //oeuvreCorrigeFound.setMt(input.getMt());
        oeuvreCorrigeFound.setMtEdit(input.getMtEdit());
        oeuvreCorrigeFound.setOeuvreManuel(null);
        oeuvreCorrigeFound.setDateInsertion(new Date());
        oeuvreCorrigeFound.setCdeTypIde12(input.getCdeTypIde12());

        ligneProgrammeFVDao.save(oeuvreCorrigeFound);
    }

    @Override
    public LigneProgrammeFV createOeuvreManuel(LigneProgrammeFV input, Programme programme) {
        String numProg = input.getNumProg();
        input.setFichier(fichierService.getOrCreateFichierLink(numProg));
        input.setCdeCisac(CDE_CISAC_058);
        input.setCdeFamilTypUtil(programme.getFamille().getCode());
        input.setCdeUtil(CDE_UTIL);
        input.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD06.toString())) {
            input.setNbrDif(NBRDIF_FD12);
        }
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD12.toString())) {
            input.setMt(MT_FD06);
        }

        input.setDateInsertion(new Date());
        input.setCdeTypIde12(input.getCdeTypIde12());

        //input.setSelectionEnCours(TRUE);
        //input.setSelection(TRUE);


        return ligneProgrammeFVDao.saveAndFlush(input);
    }

    private boolean isBackToEtatAuto(Programme programme, LigneProgrammeFV ligneProgrammeFV, List<LigneProgrammeFV> oeuvresAutoLink){
        boolean result = false;
        if(programme.getTypeUtilisation().getCode().equals(FV_FONDS_06.getCode())){
            if(ligneProgrammeFV.getMt()!=null){
                if(ligneProgrammeFV.getMt().equals(sumOfMt(oeuvresAutoLink))){
                    result = true;
                }
            } else {
                if(ligneProgrammeFV.getMtEdit().equals(sumOfMt(oeuvresAutoLink))){
                    result = true;
                }
            }
        } else {
            if(ligneProgrammeFV.getNbrDif()!=null){
                if(ligneProgrammeFV.getNbrDif().equals(sumOfNbrDif(oeuvresAutoLink))){
                    result = true;
                }
            } else {
                if(ligneProgrammeFV.getNbrDifEdit().equals(sumOfNbrDif(oeuvresAutoLink))){
                    result = true;
                }
            }
        }
        return result;
    }

    private double sumOfMt(List<LigneProgrammeFV> oeuvresAuto) {
        return oeuvresAuto.stream().mapToDouble(LigneProgrammeFV::getMt).sum();
    }

    private long sumOfNbrDif(List<LigneProgrammeFV> oeuvresAuto) {
        return oeuvresAuto.stream().mapToLong(LigneProgrammeFV::getNbrDif).sum();
    }

    private LigneProgrammeFV createLigneProgrammeFVFromInput(String numProg, Map<String, String> input) {
        LigneProgrammeFV ligneProgrammeFV = new LigneProgrammeFV();


        String ide12Value = input.get(IDE_12);
        Long ide12 = StringUtils.isNotEmpty(ide12Value) ? Long.parseLong(ide12Value) : 0L;

        ligneProgrammeFV.setTitreOeuvre(input.get("titreOeuvre"));
        ligneProgrammeFV.setRoleParticipant1(input.get("roleParticipant1"));
        ligneProgrammeFV.setNomParticipant1(input.get("nomParticipant1"));
        ligneProgrammeFV.setNumProg(numProg);
        ligneProgrammeFV.setIde12(ide12);
        ligneProgrammeFV.setNumProg(numProg);

        return ligneProgrammeFV;
    }

    @Override
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                ligneProgrammeFVDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), 1);
            }
        }
    }

    @Override
    @Transactional
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
        LigneProgrammeFV oeuvreManuelFound = new LigneProgrammeFV();
        if(selectedLigneProgramme.getAjout().equals(MANUEL)){
            oeuvreManuelFound = ligneProgrammeFVDao.findOeuvreManuelByIde12(numProg, ide12);
        } else if(selectedLigneProgramme.getAjout().equals(CORRIGE)) {
            oeuvreManuelFound = ligneProgrammeFVDao.findOeuvreCorrigeByIde12(numProg, ide12);
        }

        deleteOeuvreManuel(oeuvreManuelFound);

    }

    @Override
    @Transactional
    public void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected) {
        for (Map<String, String>  obj:  unselected) {
            if(obj != null && !obj.isEmpty()) {
                ligneProgrammeFVDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), 0);
            }
        }

    }

    @Override
    @Transactional
    public void enregistrerEdition(String numProg) {
        Programme prog = programmeDao.findByNumProg(numProg);

        ligneProgrammeFVDao.updateSelection(numProg, TRUE);
        ligneProgrammeFVDao.updateSelection(numProg, FALSE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD06.getCode())) {
            ligneProgrammeFVDao.updatePointsMt(numProg);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD12.getCode())) {
            ligneProgrammeFVDao.updatePoints(numProg);
        }

        ligneProgrammeFVDao.flush();
    }

    private void correctionOeuvreCorrige(String numProg, LigneProgrammeFV inputLigneFV, UserDTO userDTO) {
        Programme programme = programmeDao.findByNumProg(numProg);

        List<LigneProgrammeFV> oeuvresAutoLinkCorrige = ligneProgrammeFVDao.findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(numProg, inputLigneFV.getIde12());
        LigneProgrammeFV currentOeuvreCorrige = ligneProgrammeFVDao.findOeuvreCorrigeByIde12(numProg, inputLigneFV.getIde12());
        if(programme.getTypeUtilisation().getCode().equals(FV_FONDS_06.getCode())){
            if(!oeuvresAutoLinkCorrige.isEmpty()) {
                Double sumTotal = sumOfMt(oeuvresAutoLinkCorrige);
                if(!inputLigneFV.getMtEdit().equals(currentOeuvreCorrige.getMtEdit())) {
                    /*Journal journal = saveJournal(programme, inputLigneCMS, userDTO);
                    SituationApres situationApres = new SituationApres();
                    situationApres.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationApres().add(situationApres)*/;

                    if(!inputLigneFV.getMtEdit().equals(sumTotal)){
                        ligneProgrammeFVDao.updatePointsMtTemporaireByNumProgramme(numProg, inputLigneFV.getIde12(), inputLigneFV.getMtEdit());
                    } else {
                        deleteOeuvreCorrigeLinkAuto(numProg, inputLigneFV);
                    }


                    inputLigneFV.setMtEdit(currentOeuvreCorrige.getMtEdit());
                    /*SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationAvant().add(situationAvant);

                    journalDao.saveAndFlush(journal);*/

                }
            }
        } else if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationEnum.FV_FONDS_12.getCode())){
            if(!oeuvresAutoLinkCorrige.isEmpty()) {
                Long sumTotal = sumOfNbrDif(oeuvresAutoLinkCorrige);
                if(!inputLigneFV.getNbrDifEdit().equals(currentOeuvreCorrige.getNbrDifEdit())) {
                    /*Journal journal = saveJournal(programme, inputLigneCMS, userDTO);
                    SituationApres situationApres = new SituationApres();
                    situationApres.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationApres().add(situationApres);*/


                    if (!inputLigneFV.getNbrDifEdit().equals(sumTotal)) {
                        ligneProgrammeFVDao.updatePointsTemporaireByNumProgramme(numProg, inputLigneFV.getIde12(), inputLigneFV.getNbrDifEdit());
                    } else {
                        deleteOeuvreCorrigeLinkAuto(numProg, inputLigneFV);
                    }

                    inputLigneFV.setNbrDifEdit(currentOeuvreCorrige.getNbrDifEdit());

                    /*SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationAvant().add(situationAvant);

                    journalDao.saveAndFlush(journal);*/

                }
            }
        }
    }

    @Override
    public void enregistrerEdition(ValdierSelectionProgrammeInput input, UserDTO userDTO) {

    }

    @Override
    @Transactional
    public void annulerEdition(String numProg, String utilisateur) {
        Programme prog = programmeDao.findByNumProg(numProg);

        List<LigneProgrammeFV> oeuvresManuelsEnCoursEdition = ligneProgrammeFVDao.findOeuvresManuelsEnCoursEdition(numProg);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> deleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeFVDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeFVDao.updateSelectionTemporaire(numProg, TRUE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD06.getCode())) {
            ligneProgrammeFVDao.updatePointsMtTemporaire(numProg, TRUE);
            ligneProgrammeFVDao.updatePointsMtTemporaire(numProg, FALSE);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.FD12.getCode())) {
            ligneProgrammeFVDao.updateNbrDifTemporaire(numProg, FALSE);
            ligneProgrammeFVDao.updateNbrDifTemporaire(numProg, TRUE);
        }

        ligneProgrammeFVDao.flush();
    }

    @Override
    @Transactional
    public void annulerSelection(String numProg, String utilisateur) {
        List<LigneProgrammeFV> allOeuvresManuelsByNumProg = ligneProgrammeFVDao.findAllOeuvresManuelsByNumProg(numProg);
        allOeuvresManuelsByNumProg.forEach( oeuvreManuel -> deleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeFVDao.updateSelectionTemporaireByNumProgramme(numProg, TRUE);
        ligneProgrammeFVDao.updateSelection(numProg, TRUE);

        ligneProgrammeFVDao.flush();
    }
}
