package fr.sacem.priam.model.dao.jpa;


import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
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
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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
    private SareftrRionDao sareftrRionDao;
    @Autowired
    private SareftrTyputilDao sareftrTyputilDao;
    @Autowired
    private SareftrFamiltyputilDao sareftrFamiltyputilDao;
    
    @Before
    public void setup() {
        /*Programme pr = new Programme();
        pr.setNumProg("PR179990");
        pr.setDateCreation(new Date());
        programmeDao.save(pr);*/
    }

    
    
    @Test
    @Transactional
    public void should_find_programmes_rion_619() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(),
                criteria.getDateCreationDebut(), criteria.getDateCreationFin(),
                criteria.getSareftrFamiltyputil(), criteria.getTypeUtilisation(),
                criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
    
        assertThat(all).isNotNull();
        assertThat(all.getContent()).isNotNull().isNotEmpty();
    
        all.getContent().forEach( programmeDto -> {
            assertThat(programmeDto.getNumProg()).startsWith("17");
            assertThat(programmeDto.getRionTheorique()).isEqualTo(619);
        });
    }
    
    @Test
    public void count_nb_fichiers_par_programme() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        //criteria.setNumProg("PR170001");
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getSareftrFamiltyputil(), criteria.getTypeUtilisation(), criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).extracting("fichiers").isEqualTo(Arrays.asList(0L,0L,0L, 0L, 0L, 4L));
    
    }
    
    @Test
    public void search_programme_by_numprog() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        criteria.setNumProg("170001");
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getSareftrFamiltyputil(), criteria.getTypeUtilisation(), criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).extracting("numProg").isEqualTo(Arrays.asList("170001"));
        
    }
    
    @Test
    public void search_programme_by_nom() {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        criteria.setNom("Programme 01");
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getSareftrFamiltyputil(), criteria.getTypeUtilisation(), criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
        assertThat(all).isNotNull();
        assertThat(all.getContent()).extracting("nom").contains("Programme 01");
        
    }

    @Test
    public void search_programme_by_date_creation_from() throws ParseException {
        ProgrammeCriteria criteria = new ProgrammeCriteria();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        
        Date currentDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd'T'00:00:00").format(new Date()));
        criteria.setDateCreationDebut(currentDate);
    
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getSareftrFamiltyputil(), criteria.getTypeUtilisation(), criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
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
    
        Date currentDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd'T'23:59:59").format(new Date()));
        criteria.setDateCreationFin(currentDate);
        
        
        criteria.setStatut(Arrays.asList(StatutProgramme.values()));
        
        Page<ProgrammeDto> all = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(), criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getSareftrFamiltyputil(), criteria.getTypeUtilisation(), criteria.getSareftrRionTheorique(), criteria.getSareftrRionPaiement(), criteria.getTypeRepart(), PAGEABLE);
        
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
        SareftrFamiltyputil sareftrFamiltyputil = new SareftrFamiltyputil();
        sareftrFamiltyputil.setCode("monCdeFam1");
        sareftrFamiltyputil.setDateDebut(date);
        sareftrFamiltyputilDao.save(sareftrFamiltyputil);
        SareftrTyputil sareftrTyputil = new SareftrTyputil();
        sareftrTyputil.setCode("cdeTyUt1");
        sareftrTyputil.setCodeFamille("monCdeFam1");
        sareftrTyputil.setDateDebut(date);
        sareftrTyputilDao.save(sareftrTyputil);
        SareftrRion sareftrRionPaiement = new SareftrRion();
        sareftrRionPaiement.setRion(998);
        sareftrRionDao.save(sareftrRionPaiement);
        SareftrRion sareftrRionTheorique = new SareftrRion();
        sareftrRionTheorique.setRion(999);
        sareftrRionDao.save(sareftrRionTheorique);
        Programme programme = new Programme();
        programme.setNumProg("myNum1");
        programme.setNom("mon programme 1");
        programme.setSareftrTyputil(sareftrTyputil);
        programme.setSareftrFamiltyputil(sareftrFamiltyputil);
        programme.setSareftrRionPaiement(sareftrRionPaiement);
        programme.setSareftrRionTheorique(sareftrRionTheorique);
        programmeDao.save(programme);
    }
    
    @Test
    public void find_by_numprog() {
        ProgrammeDto pr170001 = programmeViewDao.findByNumProg("170001");
        assertThat(pr170001).isNotNull();
        assertThat(pr170001.getNumProg()).isEqualTo("170001");
    }
}
