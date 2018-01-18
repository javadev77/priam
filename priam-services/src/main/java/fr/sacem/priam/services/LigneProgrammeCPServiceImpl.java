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
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cp.LigneProgrammeCPService;
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
    public static final String MANUEL = "Manuel";
    public static final String AUTOMATIQUE = "Automatique";
    private static final String SOMME = "SOMME";
    public static final int SELECTION = 1;


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

                    if("sum(nbrDif)".equals(sortBy.getProperty()) ||
                           "nbrDif".equals(sortBy.getProperty()) ||
                           "sum(durDif)".equals(sortBy.getProperty()) ||
                           "durDif".equals(sortBy.getProperty())) {
                        if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                            sort = JpaSort.unsafe(sortBy.getDirection(), "sum(nbrDif)");
                        } else if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                            sort = JpaSort.unsafe(sortBy.getDirection(), "sum(durDif)");
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
                ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0], 1);
            }
        }


    }
    
    
    @Transactional
    @Override
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
    
        String cdeUtil = selectedLigneProgramme.getCdeUtil();
        LigneProgrammeCP oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(numProg, ide12, cdeUtil);
        doDeleteOeuvreManuel(oeuvreManuelFound);
    
    }

    private void doDeleteOeuvreManuel(LigneProgrammeCP oeuvreManuelFound) {
        if(oeuvreManuelFound != null) {
    
            List<LigneProgrammeCP> oeuvresAutoByIdOeuvreManuel = ligneProgrammeCPDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            oeuvresAutoByIdOeuvreManuel.forEach( oeuvreAuto -> {
                oeuvreAuto.setSelection(FALSE);
                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
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
    
        LigneProgrammeCP oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
        if(oeuvreManuelFound != null) {
            oeuvreManuelFound.setCdeCisac(CDE_CISAC_058);
            oeuvreManuelFound.setCdeFamilTypUtil(programme.getFamille().getCode());
            oeuvreManuelFound.setCdeTypUtil(programme.getTypeUtilisation().getCode());
            oeuvreManuelFound.setAjout(MANUEL);
            oeuvreManuelFound.setOeuvreManuel(null);
            oeuvreManuelFound.setDurDif(input.getDurDif());
            oeuvreManuelFound.setNbrDif(input.getNbrDif());
            oeuvreManuelFound.setDateInsertion(new Date());
            oeuvreManuelFound.setUtilisateur(input.getUtilisateur());
            oeuvreManuelFound.setCdeTypIde12(input.getCdeTypIde12());
        } else {
            List<LigneProgrammeCP> founds = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
            if(founds != null && !founds.isEmpty()) {
                LigneProgrammeCP oeuvreManuel = createOeuvreManuel(input, programme);
                founds.forEach( found -> {
                    found.setOeuvreManuel(oeuvreManuel);
                    found.setSelection(FALSE);
                    ligneProgrammeCPDao.save(found);
                });
        
            } else {
                createOeuvreManuel(input, programme);
            }
        }
        
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
        input.setAjout(MANUEL);
        input.setSelection(TRUE);
        input.setDateInsertion(new Date());
        input.setCdeTypIde12(input.getCdeTypIde12());
        input.setSelectionEnCours(TRUE);
        input.setSelection(FALSE);
        
        return ligneProgrammeCPDao.saveAndFlush(input);
    }
    
    @Transactional
    @Override
    public void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected) {
        for (Map<String, String>  obj:  unselected) {
            if(obj != null && !obj.isEmpty()) {
                ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0], 0);
            }
        }
    }
    

    @Transactional
    @Override
    public void enregistrerEdition(String numProg) {
        
        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, FALSE);
        
        ligneProgrammeCPDao.flush();
    }
    
    @Transactional
    @Override
    public void annulerEdition(String numProg) {
        List<LigneProgrammeCP> oeuvresManuelsEnCoursEdition = ligneProgrammeCPDao.findOeuvresManuelsEnCoursEdition(numProg, FALSE);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel));
        
        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, TRUE);
        
        ligneProgrammeCPDao.flush();
    }
    
    @Transactional
    @Override
    public void annulerSelection(String numProg) {
        List<LigneProgrammeCP> allOeuvresManuelsByNumProg = ligneProgrammeCPDao.findAllOeuvresManuelsByNumProg(numProg);
        allOeuvresManuelsByNumProg.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel));
        
        ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        
        ligneProgrammeCPDao.flush();
    }

    @Override
    public Map<String, Long> getDurDifProgramme(String numProg, String statut){

        Map<String, Long> result = new HashMap<>();
        result.put(AUTOMATIQUE, 0L);
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
