package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.cp.FichierCPDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeCPDao;
import fr.sacem.priam.model.domain.cp.FichierCP;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by jbelwidane on 25/07/2017.
 */
@Component
public class LigneProgrammeService {

    public static final String IDE_12 = "ide12";
    public static final String CDE_UTIL = "libAbrgUtil";
    public static final String CDE_CISAC_058 = "058";
    public static final String MANUEL = "Manuel";
    
    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired
    private ProgrammeCPDao programmeCPDao;
    
    @Autowired
    private FichierCPDao fichierCPDao;
    
    @Autowired
    private ProgrammeService programmeService;

    private static final Logger LOG = LoggerFactory.getLogger(LigneProgrammeService.class);

    @Transactional
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeCPDao.findIDE12sByProgramme(ide12, programme);
    }

    @Transactional
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeCPDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Transactional
    public List<String> getUtilisateursByProgramme(String programme) {
        return ligneProgrammeCPDao.findUtilisateursByProgramme(programme);
    }

    @Transactional
    public Page<SelectionDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {

        Programme programme = programmeCPDao.findOne(criteria.getNumProg());

        Pageable queryPageable = new Pageable() {

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


        Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeCPDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getUtilisateur(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(), queryPageable);

        return ligneProgrammeByCriteria;
    }

    @Transactional
    public void selectAll(String numProg) {
        ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, true);
        ligneProgrammeCPDao.updateSelection(numProg, true);
        
        ligneProgrammeCPDao.flush();
    }

    @Transactional
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        //ligneProgrammeDao.updateSelectionTemporaireByNumProgramme(numProg, false);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0], 1);
        }


    }
    @Transactional
    public void deselectAll(String numProg) {
        ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, false);
    }
    
    
    @Transactional
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
    
        String cdeUtil = selectedLigneProgramme.getCdeUtil();//selectedLigneProgramme.getLibAbrgUtil().split(" - ")[0];
        LigneProgrammeCP oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(numProg, ide12, cdeUtil);
        doDeleteOeuvreManuel(oeuvreManuelFound);
    
    }
    
    @Transactional
    private void doDeleteOeuvreManuel(LigneProgrammeCP oeuvreManuelFound) {
        if(oeuvreManuelFound != null) {
    
            List<LigneProgrammeCP> oeuvresAutoByIdOeuvreManuel = ligneProgrammeCPDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            oeuvresAutoByIdOeuvreManuel.forEach( oeuvreAuto -> {
                oeuvreAuto.setSelection(FALSE);
                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
                ligneProgrammeCPDao.save(oeuvreAuto);
            });
            //ligneProgrammeDao.save(oeuvresAutoByIdOeuvreManuel);
            //ligneProgrammeDao.deleteLigneProgrammeByIde12AndNumProg(numProg, ide12, cdeUtil);
            ligneProgrammeCPDao.delete(oeuvreManuelFound);
            ligneProgrammeCPDao.flush();
        }
    }
    
    
    @Transactional
    public void ajouterOeuvreManuel(LigneProgrammeCP input) {
        Programme programme = programmeCPDao.findOne(input.getNumProg());
    
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
            // oeuvreManuelFound.setSelectionEnCours(true);
        } else {
            List<LigneProgrammeCP> founds = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
            if(founds != null && !founds.isEmpty()) {
                LigneProgrammeCP oeuvreManuel = createOeuvreManuel(input, programme);
                founds.forEach( found -> {
                    found.setOeuvreManuel(oeuvreManuel);
                    //found.setSelectionEnCours(FALSE);
                    found.setSelection(FALSE);
                    ligneProgrammeCPDao.save(found);
                });
        
            } else {
                createOeuvreManuel(input, programme);
            }
        }
        
    }
    
    private LigneProgrammeCP createOeuvreManuel(LigneProgrammeCP input, Programme programme) {
        FichierCP probe = new FichierCP();
        probe.setAutomatique(false);
        Programme programme1 = new Programme();
        programme1.setNumProg(input.getNumProg());
        probe.setProgramme(programme1);
        
        Example<FichierCP> of = Example.of(probe);
        FichierCP f = fichierCPDao.findOne(of);
        
        if(f == null) {
		f = new FichierCP();
  
		f.setProgramme(programme);
		f.setAutomatique(false);
  
		fichierCPDao.saveAndFlush(f);
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
        //input.setLibelleUtilisateur(sareftjLibutilDao.find);
        
        return ligneProgrammeCPDao.saveAndFlush(input);
    }
    
    @Transactional
    public void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected) {
    
        for (Map<String, String>  obj:  unselected) {
            ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0], 0);
        }
    }
    
    @Transactional
    public Map<String, Long> calculerDureeAllSelection(String numProg, Set<Map<String, String>> lignesProg, boolean isSelectAll) {
        Programme programme = programmeCPDao.findOne(numProg);
        if(isSelectAll) {
           selectLigneProgramme(numProg, lignesProg);
        } else {
           deselectLigneProgramme(numProg, lignesProg);
        }
    
        Map<String, Long> durDifProgramme = programmeService.getDurDifProgramme(numProg, programme.getStatut().name());
       
       //Rollback les modif de avant calcul
        if(isSelectAll) {
            deselectLigneProgramme(numProg, lignesProg);
        } else {
            selectLigneProgramme(numProg, lignesProg);
        }
        
        return durDifProgramme;
    }
    
    @Transactional
    public void enregistrerEdition(String numProg) {
        
        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, FALSE);
        
        ligneProgrammeCPDao.flush();
    }
    
    @Transactional
    public void annulerEdition(String numProg) {
        List<LigneProgrammeCP> oeuvresManuelsEnCoursEdition = ligneProgrammeCPDao.findOeuvresManuelsEnCoursEdition(numProg, FALSE);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel));
        
        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, TRUE);
        
        ligneProgrammeCPDao.flush();
    }
    
    @Transactional
    public void annulerSelection(String numProg) {
        List<LigneProgrammeCP> allOeuvresManuelsByNumProg = ligneProgrammeCPDao.findAllOeuvresManuelsByNumProg(numProg);
        allOeuvresManuelsByNumProg.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel));
        
        ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        
        ligneProgrammeCPDao.flush();
    }
}
