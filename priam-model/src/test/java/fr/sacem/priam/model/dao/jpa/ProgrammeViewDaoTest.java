package fr.sacem.priam.model.dao.jpa;


import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class ProgrammeViewDaoTest {
    
    public static final Pageable PAGEABLE = new Pageable() {
        @Override
        public int getPageNumber() {
            return 0;
        }
        
        @Override
        public int getPageSize() {
            return 6;
        }
        
        @Override
        public int getOffset() {
            return 0;
        }
        
        @Override
        public Sort getSort() {
            return new Sort(Sort.Direction.ASC, "fichiers");
        }
        
        @Override
        public Pageable next() {
            return null;
        }
        
        @Override
        public Pageable previousOrFirst() {
            return null;
        }
        
        @Override
        public Pageable first() {
            return null;
        }
        
        @Override
        public boolean hasPrevious() {
            return false;
        }
    };
    
    @Autowired
    private ProgrammeViewDao programmeViewDao;
    @Autowired
    private ProgrammeDao programmeDao;
    @Autowired
    private RionDao rionDao;
    @Autowired
    private TypeUtilisationDao typeUtilisationDao;
    @Autowired
    private FamilleDao familleDao;
    
    @Before
    public void setup() {
        /*Programme pr = new Programme();
        pr.setNumProg("PR179990");
        pr.setDateCreation(new Date());
        programmeDao.save(pr);*/
    }

    
    
    @Test
    public void should_find_programmes_rion_619() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(),
                criteria.getDateCreationDebut(), criteria.getDateCreationFin(),
                criteria.getFamille(), criteria.getTypeUtilisation(),
                criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
    
        assertThat(all).isNotNull();
        assertThat(all.getContent()).isNotNull().isNotEmpty();
    
        all.getContent().forEach( programmeDto -> {
            assertThat(programmeDto.getNumProg()).startsWith("PR17");
            assertThat(programmeDto.getRionTheorique()).isEqualTo(619);
        });
    }
    
    @Test
    public void count_nb_fichiers_par_programme() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        //criteria.setNumProg("PR170001");
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getFamille(), criteria.getTypeUtilisation(), criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).extracting("fichiers").isEqualTo(Arrays.asList(0L,0L,0L, 0L, 0L, 3L));
    
    }
    
    @Test
    public void search_programme_by_numprog() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        criteria.setNumProg("PR170001");
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getFamille(), criteria.getTypeUtilisation(), criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).extracting("numProg").isEqualTo(Arrays.asList("PR170001"));
        
    }
    
    @Test
    public void search_programme_by_nom() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        criteria.setNom("Programme 01");
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getFamille(), criteria.getTypeUtilisation(), criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).extracting("nom").contains("Programme 01");
        
    }

    @Test
    public void search_programme_by_date_creation_from() throws ParseException {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        
        Date currentDate = sdf.parse("2017-06-23T00:00:00");
        criteria.setDateCreationDebut(currentDate);
    
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getFamille(), criteria.getTypeUtilisation(), criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).isNotEmpty();
        all.getContent().forEach(elem -> {
            assertThat(yyyyMMdd.format(elem.getDateCreation())).isEqualTo(yyyyMMdd.format(currentDate));
        });
        
    }
    
    @Test
    public void search_programme_by_date_creation_to() throws ParseException {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        
        Date currentDate = sdf.parse("2017-06-23T23:59:59");
        criteria.setDateCreationFin(currentDate);
        
        
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getFamille(), criteria.getTypeUtilisation(), criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).isNotEmpty();
        all.getContent().forEach(elem -> {
            assertThat(yyyyMMdd.format(elem.getDateCreation())).isEqualTo(yyyyMMdd.format(currentDate));
        });
        
    }
    
    @Test
    public void search_progremme_by_nom_without_criteria(){
        Programme programmeSearcher =new Programme();
        programmeSearcher.setNom("Programme 01");
        Example example = Example.of(programmeSearcher);
        List<Programme> resultat= programmeDao.findAll(example);
        assertThat(resultat).isNotNull();
        assertThat(resultat.size()).isEqualTo(2);

    }
    @Test
    public void add_programme(){
        java.util.Date date = new java.util.Date();
        Famille famille = new Famille();
        famille.setCode("monCdeFam1");
        famille.setDateDebut(date);
        familleDao.save(famille);
        TypeUtilisation typeUtilisation = new TypeUtilisation();
        typeUtilisation.setCode("cdeTyUt1");
        typeUtilisation.setCodeFamille("monCdeFam1");
        typeUtilisation.setDateDebut(date);
        typeUtilisationDao.save(typeUtilisation);
        Rion rionPaiement = new Rion();
        rionPaiement.setRion(998);
        rionDao.save(rionPaiement);
        Rion rionTheorique = new Rion();
        rionTheorique.setRion(999);
        rionDao.save(rionTheorique);
        Programme programme = new Programme();
        programme.setNumProg("myNum1");
        programme.setNom("mon programme 1");
        programme.setTypeUtilisation(typeUtilisation);
        programme.setFamille(famille);
        programme.setRionPaiement(rionPaiement);
        programme.setRionTheorique(rionTheorique);
        programmeDao.save(programme);
    }
    
    @Test
    public void find_by_numprog() {
        ProgrammeDto pr170001 = programmeViewDao.findByNumProg("PR170001");
        
        assertThat(pr170001).isNotNull();
        assertThat(pr170001.getNumProg()).isEqualTo("PR170001");
    }
}
