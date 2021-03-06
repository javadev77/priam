package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.CatalogueOctavDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import fr.sacem.priam.model.journal.JournalBuilder;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cms.LigneProgrammeCMSService;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.journal.annotation.TypeLog;
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

import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_TYPE_CMS_ANF;
import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_TYPE_CMS_FR;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by benmerzoukah on 07/12/2017.
 */
@Service("ligneProgrammeCMSService")
public class LigneProgrammeCMSServiceImpl implements LigneProgrammeService, LigneProgrammeCMSService {
    public static final String IDE_12 = "ide12";
    public static final String CDE_UTIL = "XXX";
    public static final String CDE_CISAC_058 = "058";
    public static final String MANUEL = "MANUEL";
    public static final String AUTOMATIQUE = "AUTOMATIQUE";
    public static final String CORRIGE = "CORRIGE";
    private static final String SOMME = "SOMME";
    public static final Long NBRDIF_SONOFRA = 1L;
    public static final int SELECTION = 1;
    private static final Double MT_SONOANT = 0.0;
    public static final String POINTS_MONTANT = "pointsMontant";
    public static final String AJOUT = "ajout";
    public static final String MT = "mt";

    @Autowired
    private LigneProgrammeCMSDao ligneProgrammeCMSDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private CatalogueOctavDao catalogueOctavDao;

    @Autowired
    private JournalDao journalDao;

    @Autowired
    private FichierService fichierService;

    @Override
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeCMSDao.findIDE12sByProgramme(ide12, programme);
    }

    @Override
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeCMSDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Override
    @Transactional(value="transactionManager")
    public Page<SelectionCMSDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {
        Programme programme = programmeDao.findOne(criteria.getNumProg());

        Sort customSort = createCustomSort(pageable, programme);
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), customSort);

        return  ligneProgrammeCMSDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
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
            if (TypeUtilisationEnum.CMS_FRA.getCode().equals(typeUtilCode)) {
                sort = JpaSort.unsafe(direction, "mtEdit");
            }

            if (TypeUtilisationEnum.CMS_ANT.getCode().equals(typeUtilCode)) {
                sort = JpaSort.unsafe(direction, "nbrDifEdit");
            }
        }

        return sort;
    }

    @Override
    @Transactional(value="transactionManager")
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                ligneProgrammeCMSDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), 1);
            }
        }

    }

    @Override
    @Transactional(value="transactionManager")
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
        LigneProgrammeCMS oeuvreManuelFound = new LigneProgrammeCMS();
        if(selectedLigneProgramme.getAjout().equals(MANUEL)){
            oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreManuelByIde12(numProg, ide12);
        } else if(selectedLigneProgramme.getAjout().equals(CORRIGE)) {
            oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreCorrigeByIde12(numProg, ide12);
        }

        deleteOeuvreManuel(oeuvreManuelFound);

    }

    @Override
    public void deleteOeuvreManuel(LigneProgrammeCMS oeuvreManuelFound) {
        if(oeuvreManuelFound != null) {

            List<LigneProgrammeCMS> oeuvresAutoByIdOeuvreManuel = ligneProgrammeCMSDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            for (LigneProgrammeCMS oeuvreAuto : oeuvresAutoByIdOeuvreManuel) {
                //oeuvreAuto.setSelection(FALSE);
                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
                oeuvreAuto.setMtEdit(oeuvreAuto.getMt());
                oeuvreAuto.setNbrDifEdit(oeuvreAuto.getNbrDif());
                ligneProgrammeCMSDao.save(oeuvreAuto);
            }

            ligneProgrammeCMSDao.delete(oeuvreManuelFound);
            ligneProgrammeCMSDao.flush();
        }
    }

    @Override
    @Transactional(value="transactionManager")
    public void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected) {
        for (Map<String, String>  obj:  unselected) {
            if(obj != null && !obj.isEmpty()) {
                ligneProgrammeCMSDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), 0);
            }
        }

    }

    @Override
    @Transactional(value="transactionManager")
    public void enregistrerEdition(String numProg) {

    }

    @Override
    @Transactional(value="transactionManager")
    public void enregistrerEdition(ValdierSelectionProgrammeInput input, UserDTO userDTO) {
        String numProg = input.getNumProg();
        Programme prog = programmeDao.findByNumProg(numProg);

        ligneProgrammeCMSDao.updateSelection(numProg, TRUE);
        ligneProgrammeCMSDao.updateSelection(numProg, FALSE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.getCode())) {
            ligneProgrammeCMSDao.updatePointsMt(numProg);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.getCode())) {
            ligneProgrammeCMSDao.updatePoints(numProg);
        }

        ligneProgrammeCMSDao.flush();
    }

    @Override
    @Transactional(value="transactionManager")
    public void annulerEdition(String numProg, String utilisateur) {
        Programme prog = programmeDao.findByNumProg(numProg);

        List<LigneProgrammeCMS> oeuvresManuelsEnCoursEdition = ligneProgrammeCMSDao.findOeuvresManuelsEnCoursEdition(numProg);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> deleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeCMSDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeCMSDao.updateSelectionTemporaire(numProg, TRUE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.getCode())) {
            ligneProgrammeCMSDao.updatePointsMtTemporaire(numProg, TRUE);
            ligneProgrammeCMSDao.updatePointsMtTemporaire(numProg, FALSE);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.getCode())) {
            ligneProgrammeCMSDao.updateNbrDifTemporaire(numProg, FALSE);
            ligneProgrammeCMSDao.updateNbrDifTemporaire(numProg, TRUE);
        }

        ligneProgrammeCMSDao.flush();
    }

    @Override
    @Transactional(value="transactionManager")
    public void annulerSelection(String numProg, String utilisateur) {
        List<LigneProgrammeCMS> allOeuvresManuelsByNumProg = ligneProgrammeCMSDao.findAllOeuvresManuelsByNumProg(numProg);
        allOeuvresManuelsByNumProg.forEach( oeuvreManuel -> deleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeCMSDao.updateSelectionTemporaireByNumProgramme(numProg, TRUE);
        ligneProgrammeCMSDao.updateSelection(numProg, TRUE);

        ligneProgrammeCMSDao.flush();
    }


    @Override
    public Map<String,Object> calculerCompteurs(String numProg, String statut) {
        Map<String, Object> result = new HashMap<>();
        result.put(AUTOMATIQUE, 0L);
        result.put(CORRIGE, 0L);
        result.put(MANUEL, 0L);
        result.put(SOMME, 0.0d);

        Integer selection = SELECTION;

        List<Object> prog = ligneProgrammeCMSDao.compterOuvres(numProg, selection);

        for (Object indicateur : prog) {
            Object[] indObjects = (Object[]) indicateur;
            result.put((String) indObjects[1], ((BigInteger) indObjects[0]).longValue());
        }
           result.put(SOMME, ligneProgrammeCMSDao.calculerPointsMontantOeuvres(numProg, selection));

        return result;
    }

    @Transactional(value="transactionManager")
    @Override
    public void ajouterOeuvreManuel(LigneProgrammeCMS input, UserDTO userDTO) {
        Programme programme = programmeDao.findOne(input.getNumProg());

        Journal journal = createJournal(input, programme);
        journal.setUtilisateur(userDTO.getUserId());
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();

        situationApres.setSituation(getPointsOrMtAsString(programme, input));

        List<LigneProgrammeCMS> founds = ligneProgrammeCMSDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
        if(founds != null && !founds.isEmpty()) {
            if(!isBackToEtatAuto(programme, input, founds)){
                input.setAjout(CORRIGE);
                LigneProgrammeCMS oeuvreManuel = createOeuvreManuel(input, programme);
                founds.forEach( found -> {
                    found.setOeuvreManuel(oeuvreManuel);
                    ligneProgrammeCMSDao.saveAndFlush(found);
                });
            }


            SareftrTyputil typeUtilisation = programme.getTypeUtilisation();
            if(TypeUtilisationPriam.SONOFRA.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfMt(founds)));
            } else if(TypeUtilisationPriam.SONOANT.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfNbrDif(founds)));
            }

            journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
        } else {
            LigneProgrammeCMS oeuvreCorrigeFound = ligneProgrammeCMSDao.findOeuvreCorrigeByIde12(input.getNumProg(), input.getIde12());
            List<LigneProgrammeCMS> oeuvresAutoLinkCorrige = ligneProgrammeCMSDao.findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
            if(oeuvreCorrigeFound != null) {
                situationAvant.setSituation(getPointsOrMtAsString(programme, oeuvreCorrigeFound));
                journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
                if(!isBackToEtatAuto(programme, input, oeuvresAutoLinkCorrige)){
                    oeuvreCorrigeFound.setAjout(CORRIGE);
                    updateOeuvre(input, programme, oeuvreCorrigeFound);
                } else {
                    deleteOeuvreCorrigeLinkAuto(input.getNumProg(), input);
                }
            } else {
                LigneProgrammeCMS oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
                if(oeuvreManuelFound != null) {
                    if(!Objects.equals(oeuvreManuelFound.getMtEdit(), input.getMtEdit())) {
                        oeuvreManuelFound.setAjout(MANUEL);
                        situationAvant.setSituation(getPointsOrMtAsString(programme, oeuvreManuelFound));
                        updateOeuvre(input, programme, oeuvreManuelFound);

                        journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
                    } else {
                        journal = null;
                    }

                } else {
                    input.setAjout(MANUEL);
                    input.setMtEdit(input.getMtEdit());
                    input.setNbrDifEdit(input.getNbrDif());
                    createOeuvreManuel(input, programme);

                    situationAvant.setSituation("0");
                    journal.setEvenement(TypeLog.AJOUT_OEUVRE.getEvenement());

                }
            }
        }

        if(journal != null) {
            journal.getListSituationAvant().add(situationAvant);
            journal.getListSituationApres().add(situationApres);
            journalDao.saveAndFlush(journal);
        }

    }

    private String getPointsOrMtAsString(Programme prog, LigneProgrammeCMS input) {
        SareftrTyputil typeUtilisation = prog.getTypeUtilisation();
        if(TypeUtilisationPriam.SONOFRA.getCode().equals(typeUtilisation.getCode())) {
            return String.valueOf(input.getMtEdit());
        } else if(TypeUtilisationPriam.SONOANT.getCode().equals(typeUtilisation.getCode())) {
            return  String.valueOf(input.getNbrDifEdit());
        }
        return "";
    }

    private void updateOeuvre(LigneProgrammeCMS input, Programme programme, LigneProgrammeCMS oeuvreCorrigeFound) {
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

        ligneProgrammeCMSDao.save(oeuvreCorrigeFound);
    }

    private Journal createJournal(LigneProgrammeCMS input, Programme prog) {
        JournalBuilder jb = new JournalBuilder(input.getNumProg(), input.getIde12(), input.getUtilisateur());



        return jb.build();
    }

    @Override
    public LigneProgrammeCMS createOeuvreManuel(LigneProgrammeCMS input, Programme programme) {
        String numProg = input.getNumProg();
        input.setFichier(fichierService.getOrCreateFichierLink(numProg));
        input.setCdeCisac(CDE_CISAC_058);
        input.setCdeFamilTypUtil(programme.getFamille().getCode());
        input.setCdeUtil(CDE_UTIL);
        input.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.toString())) {
            input.setNbrDif(NBRDIF_SONOFRA);
        }
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.toString())) {
            input.setMt(MT_SONOANT);
        }

        input.setDateInsertion(new Date());
        input.setCdeTypIde12(input.getCdeTypIde12());

        //input.setSelectionEnCours(TRUE);
        //input.setSelection(TRUE);


        return ligneProgrammeCMSDao.saveAndFlush(input);
    }

    @Override
    @Transactional(value="transactionManager")
    public boolean isEligible(Long ide12, String typeCMS){
        boolean result = false;
        CatalogueOctav oeuvreCatalogueOctav = null;
        if(CATALOGUE_TYPE_CMS_ANF.equals(typeCMS)){
            oeuvreCatalogueOctav = catalogueOctavDao.findByIde12(ide12, CATALOGUE_TYPE_CMS_ANF);
        } else {
            oeuvreCatalogueOctav = catalogueOctavDao.findByIde12(ide12, CATALOGUE_TYPE_CMS_FR);
        }
        if (oeuvreCatalogueOctav!=null){
            result = true;
        }
        return result;
    }

    @Override
    public void modifierPointsTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes, boolean isSelected, UserDTO userDTO) {
        Programme prog = programmeDao.findByNumProg(numProg);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                String ajout = obj.get(AJOUT);

                LigneProgrammeCMS inputLigneCMS = createLigneProgrammeCMSFromInput(numProg, obj);
                inputLigneCMS.setSelectionEnCours(isSelected);

                if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.getCode())) {
                    String nbrDifValue = obj.get(POINTS_MONTANT);
                    Long nbrDifEdit = nbrDifValue != null && !nbrDifValue.equals("") ? Long.valueOf(nbrDifValue) : 0L;
                    inputLigneCMS.setNbrDifEdit(nbrDifEdit);
                    List<LigneProgrammeCMS> oeuvresAuto = ligneProgrammeCMSDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCMS.getIde12());

                    if(AUTOMATIQUE.equals(ajout)) {
                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            Long sumTotal = sumOfNbrDif(oeuvresAuto);
                            if( !nbrDifEdit.equals(sumTotal)) {
                                //inputLigneCMS.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());
                                ajouterOeuvreManuel(inputLigneCMS, userDTO);
                            }
                        }
                    } else if(CORRIGE.equals(ajout)){
                        correctionOeuvreCorrige(numProg, inputLigneCMS, userDTO);
                    } else {
                        //ligneProgrammeCMSDao.updatePointsTemporaireByNumProgramme(numProg, inputLigneCMS.getIde12(), nbrDifEdit);
                        ajouterOeuvreManuel(inputLigneCMS, userDTO);
                    }
                } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.getCode())) {
                    String mtValue = obj.get(POINTS_MONTANT);
                    Double mtEdit = mtValue != null && !mtValue.equals("") ? Double.valueOf(mtValue) : 0.0d;
                    inputLigneCMS.setMtEdit(mtEdit);
                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeCMS> oeuvresAuto = ligneProgrammeCMSDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCMS.getIde12());

                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            Double sumTotal = sumOfMt(oeuvresAuto);
                            if( !mtEdit.equals(sumTotal)) {
                                ajouterOeuvreManuel(inputLigneCMS, userDTO);
                            }
                        }
                    } else if(CORRIGE.equals(ajout)){
                        correctionOeuvreCorrige(numProg, inputLigneCMS, userDTO);
                    } else {
                        ajouterOeuvreManuel(inputLigneCMS, userDTO);
                        //ligneProgrammeCMSDao.updatePointsMtTemporaireByNumProgramme(numProg, inputLigneCMS.getIde12(), mtEdit);
                    }

                }

            }
        }
    }

    private void correctionOeuvreCorrige(String numProg, LigneProgrammeCMS inputLigneCMS, UserDTO userDTO) {
        Programme programme = programmeDao.findByNumProg(numProg);

        List<LigneProgrammeCMS> oeuvresAutoLinkCorrige = ligneProgrammeCMSDao.findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(numProg, inputLigneCMS.getIde12());
        LigneProgrammeCMS currentOeuvreCorrige = ligneProgrammeCMSDao.findOeuvreCorrigeByIde12(numProg, inputLigneCMS.getIde12());
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationEnum.CMS_FRA.getCode())){
            if(!oeuvresAutoLinkCorrige.isEmpty()) {
                Double sumTotal = sumOfMt(oeuvresAutoLinkCorrige);
                if(!inputLigneCMS.getMtEdit().equals(currentOeuvreCorrige.getMtEdit())) {
                    Journal journal = saveJournal(programme, inputLigneCMS, userDTO);
                    SituationApres situationApres = new SituationApres();
                    situationApres.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationApres().add(situationApres);

                    if(!inputLigneCMS.getMtEdit().equals(sumTotal)){
                        ligneProgrammeCMSDao.updatePointsMtTemporaireByNumProgramme(numProg, inputLigneCMS.getIde12(), inputLigneCMS.getMtEdit());
                    } else {
                        deleteOeuvreCorrigeLinkAuto(numProg, inputLigneCMS);
                    }


                    inputLigneCMS.setMtEdit(currentOeuvreCorrige.getMtEdit());
                    SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationAvant().add(situationAvant);

                    journalDao.saveAndFlush(journal);

                }
            }
        } else if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationEnum.CMS_ANT.getCode())){
            if(!oeuvresAutoLinkCorrige.isEmpty()) {
                Long sumTotal = sumOfNbrDif(oeuvresAutoLinkCorrige);
                if(!inputLigneCMS.getNbrDifEdit().equals(currentOeuvreCorrige.getNbrDifEdit())) {
                    Journal journal = saveJournal(programme, inputLigneCMS, userDTO);
                    SituationApres situationApres = new SituationApres();
                    situationApres.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationApres().add(situationApres);


                    if (!inputLigneCMS.getNbrDifEdit().equals(sumTotal)) {
                        ligneProgrammeCMSDao.updatePointsTemporaireByNumProgramme(numProg, inputLigneCMS.getIde12(), inputLigneCMS.getNbrDifEdit());
                    } else {
                        deleteOeuvreCorrigeLinkAuto(numProg, inputLigneCMS);
                    }

                    inputLigneCMS.setNbrDifEdit(currentOeuvreCorrige.getNbrDifEdit());

                    SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(getPointsOrMtAsString(programme, inputLigneCMS));
                    journal.getListSituationAvant().add(situationAvant);

                    journalDao.saveAndFlush(journal);

                }
            }
        }
    }

    private void deleteOeuvreCorrigeLinkAuto(String numProg, LigneProgrammeCMS inputLigneCMS) {
        LigneProgrammeCMS oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreCorrigeByIde12(numProg, inputLigneCMS.getIde12());
        deleteOeuvreManuel(oeuvreManuelFound);
    }

    private Journal saveJournal(Programme programme, LigneProgrammeCMS ligneProgrammeCMS, UserDTO userDTO){
        Journal journal = createJournal(ligneProgrammeCMS, programme);
        journal.setUtilisateur(userDTO.getUserId());
        journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
        return journal;
    }

    private double sumOfMt(List<LigneProgrammeCMS> oeuvresAuto) {
        return oeuvresAuto.stream().mapToDouble(LigneProgrammeCMS::getMt).sum();
    }

    private long sumOfNbrDif(List<LigneProgrammeCMS> oeuvresAuto) {
        return oeuvresAuto.stream().mapToLong(LigneProgrammeCMS::getNbrDif).sum();
    }


    private LigneProgrammeCMS createLigneProgrammeCMSFromInput(String numProg, Map<String, String> input) {
        LigneProgrammeCMS ligneProgrammeCMS = new LigneProgrammeCMS();


        String ide12Value = input.get(IDE_12);
        Long ide12 = StringUtils.isNotEmpty(ide12Value) ? Long.parseLong(ide12Value) : 0L;

        ligneProgrammeCMS.setTitreOeuvre(input.get("titreOeuvre"));
        ligneProgrammeCMS.setRoleParticipant1(input.get("roleParticipant1"));
        ligneProgrammeCMS.setNomParticipant1(input.get("nomParticipant1"));
        ligneProgrammeCMS.setNumProg(numProg);
        ligneProgrammeCMS.setIde12(ide12);
        ligneProgrammeCMS.setNumProg(numProg);




        return ligneProgrammeCMS;
    }


    private boolean isBackToEtatAuto(Programme programme, LigneProgrammeCMS ligneProgrammeCMS, List<LigneProgrammeCMS> oeuvresAutoLink){
        boolean result = false;
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationEnum.CMS_FRA.getCode())){
            if(ligneProgrammeCMS.getMt()!=null){
                if(ligneProgrammeCMS.getMt().equals(sumOfMt(oeuvresAutoLink))){
                    result = true;
                }
            } else {
                if(ligneProgrammeCMS.getMtEdit() != null && ligneProgrammeCMS.getMtEdit().equals(sumOfMt(oeuvresAutoLink))){
                    result = true;
                }
            }
        } else {
            if(ligneProgrammeCMS.getNbrDif()!=null){
                if(ligneProgrammeCMS.getNbrDif().equals(sumOfNbrDif(oeuvresAutoLink))){
                    result = true;
                }
            } else {
                if(ligneProgrammeCMS.getNbrDifEdit() != null && ligneProgrammeCMS.getNbrDifEdit().equals(sumOfNbrDif(oeuvresAutoLink))){
                    result = true;
                }
            }
        }
        return result;
    }

}
