package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.LigneProgramme;
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
    private LigneProgrammeDao ligneProgrammeDao;

    @Autowired
    private ProgrammeDao programmeDao;
    
    @Autowired
    private FichierDao fichierDao;

    private static final Logger LOG = LoggerFactory.getLogger(LigneProgrammeService.class);

    @Transactional
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeDao.findIDE12sByProgramme(ide12, programme);
    }

    @Transactional
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Transactional
    public List<String> getUtilisateursByProgramme(String programme) {
        return ligneProgrammeDao.findUtilisateursByProgramme(programme);
    }

    @Transactional
    public Page<SelectionDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {

        Programme programme = programmeDao.findOne(criteria.getNumProg());

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

                if("nbrDif".equals(sortBy.getProperty()) || "durDif".equals(sortBy.getProperty())) {
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


        Page<SelectionDto> ligneProgrammeByCriteria = ligneProgrammeDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getUtilisateur(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(), queryPageable);

        return ligneProgrammeByCriteria;
    }

    @Transactional
    public void selectAll(String numProg) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, true);
    }

    @Transactional
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            ligneProgrammeDao.updateSelectionByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0]);
        }


    }

    @Transactional
    public void selectAllLigneProgrammeExcept(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            ligneProgrammeDao.updateSelectionByNumProgrammeExcept(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0]);
        }

    }

    @Transactional
    public void deselectAll(String numProg) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);
    }
    
    
    @Transactional
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
    
        String cdeUtil = selectedLigneProgramme.getLibAbrgUtil().split(" - ")[0];
        LigneProgramme oeuvreManuelFound = ligneProgrammeDao.findOeuvreManuelByIde12AndCdeUtil(numProg, ide12, cdeUtil);
        if(oeuvreManuelFound != null) {
    
            List<LigneProgramme> oeuvresAutoByIdOeuvreManuel = ligneProgrammeDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            oeuvresAutoByIdOeuvreManuel.forEach( oeuvreAuto -> {
                oeuvreAuto.setSelection(Boolean.FALSE);
                oeuvreAuto.setOeuvreManuel(null);
                
            });
            ligneProgrammeDao.save(oeuvresAutoByIdOeuvreManuel);
            //ligneProgrammeDao.deleteLigneProgrammeByIde12AndNumProg(numProg, ide12, cdeUtil);
            ligneProgrammeDao.delete(oeuvreManuelFound);
            ligneProgrammeDao.flush();
        }
        
    }
    
    
    @Transactional
    public void ajouterOeuvreManuel(LigneProgramme input) {
        Programme programme = programmeDao.findOne(input.getNumProg());
    
        LigneProgramme oeuvreManuelFound = ligneProgrammeDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
        if(oeuvreManuelFound != null) {
            oeuvreManuelFound.setCdeCisac(CDE_CISAC_058);
            oeuvreManuelFound.setCdeFamilTypUtil(programme.getFamille().getCode());
            oeuvreManuelFound.setCdeTypUtil(programme.getTypeUtilisation().getCode());
            oeuvreManuelFound.setAjout(MANUEL);
            oeuvreManuelFound.setOeuvreManuel(null);
            oeuvreManuelFound.setDurDif(input.getDurDif());
            oeuvreManuelFound.setNbrDif(input.getNbrDif());
            oeuvreManuelFound.setDateInsertion(new Date());
            oeuvreManuelFound.setUtilisateur("GUEST");
            
        } else {
            List<LigneProgramme> founds = ligneProgrammeDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
            if(founds != null && !founds.isEmpty()) {
                LigneProgramme oeuvreManuel = createOeuvreManuel(input, programme);
                founds.forEach( found -> {
                    found.setOeuvreManuel(oeuvreManuel);
                    found.setSelection(Boolean.FALSE);
                    ligneProgrammeDao.save(found);
                });
        
            } else {
                createOeuvreManuel(input, programme);
            }
        }
        
    }
    
    private LigneProgramme createOeuvreManuel(LigneProgramme input, Programme programme) {
        Fichier probe = new Fichier();
        probe.setAutomatique(false);
        Programme programme1 = new Programme();
        programme1.setNumProg(input.getNumProg());
        probe.setProgramme(programme1);
        
        Example<Fichier> of = Example.of(probe);
        Fichier f = fichierDao.findOne(of);
        
        if(f == null) {
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
        input.setSelection(Boolean.TRUE);
        input.setDateInsertion(new Date());
        
        return ligneProgrammeDao.saveAndFlush(input);
    }
}
