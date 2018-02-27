package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cp.LigneProgrammeCPService;
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


    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired
    private ProgrammeDao programmeDao;
    
    @Autowired
    private FichierDao fichierDao;
    
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

        Pageable queryPageable = createCustomPageable(pageable, programme);


        Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeCPDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getUtilisateur(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(), queryPageable);

        return ligneProgrammeByCriteria;
    }

    protected Pageable createCustomPageable(Pageable pageable, Programme programme) {
        return new Pageable() {

                @Override
                public int getPageNumber() {
                    return pageable.getPageNumber();
                }

                @Override
                public int getPageSize() {
                    return pageable.getPageSize();
                }

                @Override
                public int getOffset() {
                    return pageable.getOffset();
                }

                @Override
                public Sort getSort() {

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

                @Override
                public Pageable next() {
                    return pageable.next();
                }

                @Override
                public Pageable previousOrFirst() {
                    return pageable.previousOrFirst();
                }

                @Override
                public Pageable first() {
                    return pageable.first();
                }

                @Override
                public boolean hasPrevious() {
                    return pageable.hasPrevious();
                }
            };
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
    public void modifierDurOrNbrDifTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        Programme prog = programmeDao.findByNumProg(numProg);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                String ajout = obj.get("ajout");

                LigneProgrammeCP inputLigneCP = createLigneProgrammeCPFromInput(numProg, obj);

                if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
                    String nbrDifValue = obj.get("nbrDif");
                    Long nbrDifEdit = nbrDifValue != null && !nbrDifValue.equals("") ? Long.valueOf(nbrDifValue) : 0L;


                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeCP> oeuvresAuto = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());
                        Long sumTotal = oeuvresAuto.stream().mapToLong(lcp -> lcp.getNbrDif()).sum();
                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            if( !nbrDifEdit.equals(sumTotal)) {

                                inputLigneCP.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());
                                inputLigneCP.setNbrDifEdit(nbrDifEdit);

                                ajouterOeuvreManuel(inputLigneCP);
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
                        Long sumTotal = oeuvresAuto.stream().mapToLong(lcp -> lcp.getDurDif()).sum();
                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            if( !durDifEdit.equals(sumTotal)) {

                                inputLigneCP.setDurDifEdit(durDifEdit);
                                inputLigneCP.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());

                                ajouterOeuvreManuel(inputLigneCP);
                            }

                        }
                    } else {
                        ligneProgrammeCPDao.updateDurDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), durDifEdit);
                    }

                }

            }
        }

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

                oeuvreAuto.setSelection(FALSE);
                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
                oeuvreAuto.setDurDifEdit(oeuvreAuto.getDurDif());
                oeuvreAuto.setNbrDifEdit(oeuvreAuto.getNbrDif());

                ligneProgrammeCPDao.save(oeuvreAuto);
            });
            ligneProgrammeCPDao.delete(oeuvreManuelFound);
            ligneProgrammeCPDao.flush();
        }
    }
    
    
    @Transactional
    @Override
    public void ajouterOeuvreManuel(LigneProgrammeCP input) {
        Programme programme = programmeDao.findOne(input.getNumProg());

        List<LigneProgrammeCP> founds = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
        if(founds != null && !founds.isEmpty()) {
            input.setAjout(CORRIGE);
            LigneProgrammeCP oeuvreManuel = createOeuvreManuel(input, programme);
            founds.forEach( found -> {
                found.setOeuvreManuel(oeuvreManuel);
                found.setSelection(FALSE);
                ligneProgrammeCPDao.saveAndFlush(found);
            });

        } else {
            LigneProgrammeCP oeuvreCorrigeFound = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
            if(oeuvreCorrigeFound != null) {
                oeuvreCorrigeFound.setAjout(CORRIGE);
                updateOeuvreManuelOuCorrige(input, programme, oeuvreCorrigeFound);
            } else {
                LigneProgrammeCP oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
                if(oeuvreManuelFound != null) {
                    oeuvreManuelFound.setAjout(MANUEL);
                    updateOeuvreManuelOuCorrige(input, programme, oeuvreManuelFound);
                } else {
                    input.setAjout(MANUEL);
                    input.setDurDifEdit(input.getDurDif());
                    createOeuvreManuel(input, programme);
                }
            }
        }
        
    }

    private void updateOeuvreManuelOuCorrige(LigneProgrammeCP input, Programme programme, LigneProgrammeCP oeuvreToMdify) {

        oeuvreToMdify.setCdeCisac(CDE_CISAC_058);
        oeuvreToMdify.setCdeFamilTypUtil(programme.getFamille().getCode());
        oeuvreToMdify.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        oeuvreToMdify.setOeuvreManuel(null);
        oeuvreToMdify.setDurDif(input.getDurDif());
        oeuvreToMdify.setDurDifEdit(input.getDurDifEdit());
        oeuvreToMdify.setNbrDif(input.getNbrDif());
        oeuvreToMdify.setDateInsertion(new Date());
        oeuvreToMdify.setUtilisateur(input.getUtilisateur());
        oeuvreToMdify.setCdeTypIde12(input.getCdeTypIde12());

        ligneProgrammeCPDao.saveAndFlush(oeuvreToMdify);
    }

    private LigneProgrammeCP createOeuvreManuel(LigneProgrammeCP input, Programme programme) {
        Fichier probe = new Fichier();
        probe.setAutomatique(false);
        Programme programme1 = new Programme();
        programme1.setNumProg(input.getNumProg());
        probe.setProgramme(programme1);
        
        Example<Fichier> of = Example.of(probe);
        Fichier f = fichierDao.findOne(of);

        if (f == null) {
            f = new Fichier();

            f.setProgramme(programme);
            f.setAutomatique(false);

            fichierDao.saveAndFlush(f);
        }
        
        input.setFichier(f);
        input.setCdeCisac(CDE_CISAC_058);
        input.setCdeFamilTypUtil(programme.getFamille().getCode());
        input.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        /*input.setAjout(MANUEL);*/
        input.setSelection(TRUE);
        input.setDateInsertion(new Date());
        input.setSelectionEnCours(TRUE);
        input.setSelection(FALSE);
        
        return ligneProgrammeCPDao.saveAndFlush(input);
    }
    
    @Transactional
    @Override
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
    public void annulerEdition(String numProg) {
        /*List<LigneProgrammeCP> oeuvresManuelsEnCoursEdition = ligneProgrammeCPDao.findOeuvresManuelsEnCoursEdition(numProg, TRUE);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel));*/

        Programme prog = programmeDao.findByNumProg(numProg);

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
    public void annulerSelection(String numProg) {
        List<LigneProgrammeCP> allOeuvresManuelsByNumProg = ligneProgrammeCPDao.findAllOeuvresManuelsByNumProg(numProg);
        allOeuvresManuelsByNumProg.forEach(this::doDeleteOeuvreManuel);

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
