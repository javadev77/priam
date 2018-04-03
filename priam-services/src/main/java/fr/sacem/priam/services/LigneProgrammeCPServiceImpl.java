package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cp.LigneProgrammeCPService;

import fr.sacem.priam.model.journal.JournalBuilder;
import org.apache.commons.lang3.StringUtils;

import fr.sacem.priam.services.journal.annotation.TypeLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by jbelwidane on 25/07/2017.
 */
@Service("ligneProgrammeCPService")
public class LigneProgrammeCPServiceImpl implements LigneProgrammeService, LigneProgrammeCPService {

    public static final String IDE_12 = "ide12";
    public static final String CDE_UTIL = "libAbrgUtil";
    public static final String CDE_CISAC_058 = "058";
    public static final String MANUEL = "MANUEL";
    public static final String AUTOMATIQUE = "AUTOMATIQUE";
    public static final String CORRIGE = "CORRIGE";
    private static final String SOMME = "SOMME";
    public static final int SELECTION = 1;
    public static final String DUR_DIF = "durDif";
    public static final String AJOUT = "ajout";
    public static final String NBR_DIF = "nbrDif";


    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    JournalDao journalDao;

    @Autowired
    FichierService fichierService;
    
    private static final Logger LOG = LoggerFactory.getLogger(LigneProgrammeCPServiceImpl.class);

    @Transactional
    @Override
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeCPDao.findIDE12sByProgramme(ide12, programme);
    }

    @Transactional
    @Override
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeCPDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Transactional
    @Override
    public List<String> getUtilisateursByProgramme(String programme) {
        return ligneProgrammeCPDao.findUtilisateursByProgramme(programme);
    }

    @Transactional
    @Override
    public Page<SelectionDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {
        Programme programme = programmeDao.findOne(criteria.getNumProg());

        Sort customSort = createCustomSort(pageable, programme);

        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), customSort);
        return ligneProgrammeCPDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getUtilisateur(),
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

        if("sum(nbrDifEdit)".equals(sortBy.getProperty()) ||
                "nbrDifEdit".equals(sortBy.getProperty()) ||
                "sum(durDif)".equals(sortBy.getProperty()) ||
                "sum(durDifEdit)".equals(sortBy.getProperty()) ||
                "durDif".equals(sortBy.getProperty())) {
            if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                sort = JpaSort.unsafe(sortBy.getDirection(), "sum(nbrDifEdit)");
            } else if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                sort = JpaSort.unsafe(sortBy.getDirection(), "sum(durDifEdit)");
            }
        } else if("libAbrgUtil".equals(sortBy.getProperty())) {
            sort = JpaSort.unsafe(sortBy.getDirection(), "cdeUtil");
        }

        return sort;

    }

    @Transactional
    @Override
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {

                Long ide12 = Long.parseLong(obj.get(IDE_12));
                String libUtil = obj.get(CDE_UTIL);
                String cdeUtil = libUtil !=null ? libUtil.split(" - ")[0] : "";

                ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, ide12, cdeUtil, 1);
            }
        }
    }

    @Transactional
    @Override
    public void modifierDurOrNbrDifTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes, boolean isSelected, UserDTO userDTO) {
        Programme prog = programmeDao.findByNumProg(numProg);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                String ajout = obj.get("ajout");

                LigneProgrammeCP inputLigneCP = createLigneProgrammeCPFromInput(numProg, obj);
                inputLigneCP.setSelectionEnCours(isSelected);

                if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
                    String nbrDifValue = obj.get(NBR_DIF);
                    Long nbrDifEdit = nbrDifValue != null && !nbrDifValue.equals("") ? Long.valueOf(nbrDifValue) : 0L;


                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeCP> oeuvresAuto = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());

                        if(!oeuvresAuto.isEmpty()) {
                            Long sumTotal = sumOfNbrDif(oeuvresAuto);
                            if( !nbrDifEdit.equals(sumTotal)) {

                                inputLigneCP.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());
                                inputLigneCP.setNbrDifEdit(nbrDifEdit);

                                ajouterOeuvreManuel(inputLigneCP, userDTO);
                            }

                        }
                    } else {
                        ligneProgrammeCPDao.updateNbrDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), nbrDifEdit);
                    }
                }
                else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
                    String durDifStringValue = obj.get(DUR_DIF);
                    Long durDifEdit = durDifStringValue != null && !durDifStringValue.equals("") ? Long.valueOf(durDifStringValue) : 0L;

                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeCP> oeuvresAuto = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());

                        if(!oeuvresAuto.isEmpty()) {
                            Long sumTotal = sumOfDurDif(oeuvresAuto);
                            if( !durDifEdit.equals(sumTotal)) {

                                inputLigneCP.setDurDifEdit(durDifEdit);
                                inputLigneCP.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());

                                ajouterOeuvreManuel(inputLigneCP, userDTO);
                            }

                        }
                    } else {
                        ligneProgrammeCPDao.updateDurDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), durDifEdit);
                    }

                }

            }
        }

    }

    private long sumOfDurDif(List<LigneProgrammeCP> oeuvresAuto) {
        return oeuvresAuto.stream().mapToLong(lcp -> lcp.getDurDif()).sum();
    }

    private long sumOfNbrDif(List<LigneProgrammeCP> oeuvresAuto) {
        return oeuvresAuto.stream().mapToLong(LigneProgrammeCP::getNbrDif).sum();
    }

    private LigneProgrammeCP createLigneProgrammeCPFromInput(String numProg, Map<String, String> input) {
        LigneProgrammeCP ligneProgrammeCP = new LigneProgrammeCP();


        String ide12Value = input.get(IDE_12);
        Long ide12 = StringUtils.isNotEmpty(ide12Value) ? Long.parseLong(ide12Value) : 0L;
        String libUtil = input.get(CDE_UTIL);
        String cdeUtil = StringUtils.isNotEmpty(libUtil)  ? libUtil.split(" - ")[0] : "";

        ligneProgrammeCP.setTitreOeuvre(input.get("titreOeuvre"));
        ligneProgrammeCP.setRoleParticipant1(input.get("roleParticipant1"));
        ligneProgrammeCP.setNomParticipant1(input.get("nomParticipant1"));
        ligneProgrammeCP.setNumProg(numProg);
        ligneProgrammeCP.setIde12(ide12);
        ligneProgrammeCP.setCdeUtil(cdeUtil);
        ligneProgrammeCP.setLibelleUtilisateur(libUtil);
        ligneProgrammeCP.setNumProg(numProg);




        return ligneProgrammeCP;
    }


    @Transactional
    @Override
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
    
        String cdeUtil = selectedLigneProgramme.getCdeUtil();
        LigneProgrammeCP oeuvreManuelFound = new LigneProgrammeCP();
        if(selectedLigneProgramme.getAjout().equals(MANUEL)){
            oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(numProg, ide12, cdeUtil);
        } else if(selectedLigneProgramme.getAjout().equals(CORRIGE)){
            oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(numProg, ide12, cdeUtil);
        }
        doDeleteOeuvreManuel(oeuvreManuelFound);
    }

    private void doDeleteOeuvreManuel(LigneProgrammeCP oeuvreManuelFound) {
        if(oeuvreManuelFound != null) {
    
            List<LigneProgrammeCP> oeuvresAutoByIdOeuvreManuel = ligneProgrammeCPDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            oeuvresAutoByIdOeuvreManuel.forEach( oeuvreAuto -> {

                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
                oeuvreAuto.setDurDifEdit(oeuvreAuto.getDurDif());
                oeuvreAuto.setNbrDifEdit(oeuvreAuto.getNbrDif());

                ligneProgrammeCPDao.saveAndFlush(oeuvreAuto);
            });
            ligneProgrammeCPDao.delete(oeuvreManuelFound);
            ligneProgrammeCPDao.flush();
        }
    }
    
    
    @Transactional
    @Override
    public void ajouterOeuvreManuel(LigneProgrammeCP input, UserDTO userDTO) {
        Programme programme = programmeDao.findOne(input.getNumProg());

        Journal journal = createJournal(input, programme);
        journal.setUtilisateur(userDTO.getUserId());
        SituationAvant situationAvant = new SituationAvant();

        List<LigneProgrammeCP> founds = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
        if(founds != null && !founds.isEmpty()) {
            input.setAjout(CORRIGE);

            LigneProgrammeCP oeuvreManuel = createOeuvreManuel(input, programme);
            founds.forEach( found -> {
                found.setOeuvreManuel(oeuvreManuel);
                //found.setSelection(FALSE);
                ligneProgrammeCPDao.saveAndFlush(found);
            });

            SareftrTyputil typeUtilisation = programme.getTypeUtilisation();
            if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfNbrDif(founds)));
            } else if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfDurDif(founds)));
            }

            journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());


        } else {
            LigneProgrammeCP oeuvreCorrigeFound = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
            if(oeuvreCorrigeFound != null) {
                oeuvreCorrigeFound.setAjout(CORRIGE);
                situationAvant.setSituation(getNbrDifOrDurDifAsString(programme, oeuvreCorrigeFound));
                updateOeuvreManuelOuCorrige(input, programme, oeuvreCorrigeFound);

                journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
            } else {
                LigneProgrammeCP oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
                if(oeuvreManuelFound != null) {
                    oeuvreManuelFound.setAjout(MANUEL);
                    situationAvant.setSituation(getNbrDifOrDurDifAsString(programme, oeuvreManuelFound));
                    updateOeuvreManuelOuCorrige(input, programme, oeuvreManuelFound);


                    journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
                } else {
                    input.setAjout(MANUEL);
                    input.setDurDifEdit(input.getDurDif());
                    input.setNbrDifEdit(input.getNbrDif());

                    createOeuvreManuel(input, programme);

                    situationAvant.setSituation("0");
                    journal.setEvenement(TypeLog.AJOUT_OEUVRE.getEvenement());

                }
            }
        }

        journal.getListSituationAvant().add(situationAvant);

        if(LOG.isInfoEnabled()) {
            LOG.info(journal.toString());
        }
        journalDao.saveAndFlush(journal);
        
    }

    private Journal createJournal(LigneProgrammeCP input, Programme prog) {
        JournalBuilder jb = new JournalBuilder(input.getNumProg(), input.getIde12(), input.getUtilisateur());

        SituationApres situationApres = new SituationApres();
        situationApres.setSituation(getNbrDifOrDurDifAsString(prog, input));

        jb.addSituationApres(situationApres);

        return jb.build();
    }

    private String getNbrDifOrDurDifAsString(Programme prog, LigneProgrammeCP input) {
        SareftrTyputil typeUtilisation = prog.getTypeUtilisation();
        if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(typeUtilisation.getCode())) {
            /*return String.valueOf(input.getNbrDif());*/
            return String.valueOf(input.getNbrDif()!= null ? input.getNbrDif() : input.getNbrDifEdit());
        } else if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(typeUtilisation.getCode())) {
            /*return  String.valueOf(input.getDurDif());*/
            return  String.valueOf(input.getDurDif()!= null ? input.getDurDif() : input.getDurDifEdit());
        }
        return "";
    }

    private void updateOeuvreManuelOuCorrige(LigneProgrammeCP input, Programme programme, LigneProgrammeCP oeuvreToMdify) {

        oeuvreToMdify.setCdeCisac(CDE_CISAC_058);
        oeuvreToMdify.setCdeFamilTypUtil(programme.getFamille().getCode());
        oeuvreToMdify.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        oeuvreToMdify.setOeuvreManuel(null);
        oeuvreToMdify.setDurDif(input.getDurDif());
        oeuvreToMdify.setDurDifEdit(input.getDurDifEdit());
        oeuvreToMdify.setNbrDif(input.getNbrDif());
        oeuvreToMdify.setNbrDifEdit(input.getNbrDifEdit());
        oeuvreToMdify.setDateInsertion(new Date());
        oeuvreToMdify.setUtilisateur(input.getUtilisateur());
        oeuvreToMdify.setCdeTypIde12(input.getCdeTypIde12());

        ligneProgrammeCPDao.saveAndFlush(oeuvreToMdify);
    }

    private LigneProgrammeCP createOeuvreManuel(LigneProgrammeCP input, Programme programme) {

        input.setFichier(fichierService.getOrCreateFichierLink(input.getNumProg()));
        input.setCdeCisac(CDE_CISAC_058);
        input.setCdeFamilTypUtil(programme.getFamille().getCode());
        input.setCdeTypUtil(programme.getTypeUtilisation().getCode());

        input.setDateInsertion(new Date());

        //input.setSelectionEnCours(TRUE);
        //input.setSelection(TRUE);
        
        return ligneProgrammeCPDao.saveAndFlush(input);
    }
    
    @Transactional @Override
    public void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected) {
        for (Map<String, String>  obj:  unselected) {
            if(obj != null && !obj.isEmpty()) {
                ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg,
                        Long.parseLong(obj.get(IDE_12)),
                        obj.get(CDE_UTIL).split(" - ")[0],
                        0);
            }
        }
    }
    

    @Transactional
    @Override
    public void enregistrerEdition(String numProg) {

        Programme prog = programmeDao.findByNumProg(numProg);

        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, FALSE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
            ligneProgrammeCPDao.updateDurDif(numProg);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
            ligneProgrammeCPDao.updateNbrDif(numProg);
        }

        ligneProgrammeCPDao.flush();
    }
    
    @Transactional
    @Override
    public void annulerEdition(String numProg, String utilisateur) {
        Programme prog = programmeDao.findByNumProg(numProg);

        List<LigneProgrammeCP> oeuvresManuelsEnCoursEdition = ligneProgrammeCPDao.findOeuvresManuelsEnCoursEdition(numProg);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, TRUE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
            ligneProgrammeCPDao.updateDurDifTemporaire(numProg, FALSE);
            ligneProgrammeCPDao.updateDurDifTemporaire(numProg, TRUE);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
            ligneProgrammeCPDao.updateNbrDifTemporaire(numProg, FALSE);
            ligneProgrammeCPDao.updateNbrDifTemporaire(numProg, TRUE);
        }

        ligneProgrammeCPDao.flush();
    }


    @Transactional
    @Override
    public void annulerSelection(String numProg, String utilisateur) {
        List<LigneProgrammeCP> allOeuvresManuelsByNumProg = ligneProgrammeCPDao.findAllOeuvresManuelsByNumProg(numProg);

        allOeuvresManuelsByNumProg.forEach(this::doDeleteOeuvreManuel);


        //allOeuvresManuelsByNumProg.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel, utilisateur));

        ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        
        ligneProgrammeCPDao.flush();
    }

    @Override
    public Map<String, Long> getDurDifProgramme(String numProg, String statut){

        Map<String, Long> result = new HashMap<>();
        result.put(AUTOMATIQUE, 0L);
        result.put(CORRIGE, 0L);
        result.put(MANUEL, 0L);
        result.put(SOMME, 0L);

        Programme programme = programmeDao.findOne(numProg);

        Integer selection = SELECTION;

        List<Object> prog = ligneProgrammeCPDao.compterOuvres(numProg, selection);

        for (Object indicateur : prog) {
            Object[] indObjects = (Object[]) indicateur;
            result.put((String) indObjects[1], ((BigInteger) indObjects[0]).longValue());
        }

        if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(programme.getTypeUtilisation().getCode())) {
            result.put(SOMME, ligneProgrammeCPDao.calculerQuantiteOeuvres(numProg, selection));
        } else if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getTypeUtilisation().getCode())) {
            result.put(SOMME, ligneProgrammeCPDao.calculerDureeOeuvres(numProg, selection));
        }

        return result;
    }
}
