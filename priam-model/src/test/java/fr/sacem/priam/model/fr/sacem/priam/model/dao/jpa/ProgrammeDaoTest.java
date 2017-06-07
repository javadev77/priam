package fr.sacem.priam.model.fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.JpaConfigurationTest;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class ProgrammeDaoTest {
    
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
            return null;
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
    private ProgrammeDao programmeDao;
    
    @Autowired
    private FichierDao fichierDao;
    
    
    /*@Test
    public void should_find_programmes_rion_619() {
        
        Page<ProgrammeDto> all = programmeDao.findAllProgrammeByCriteria(PAGEABLE);
    
        assertThat(all).isNotNull();
        assertThat(all.getContent()).isNotNull().isNotEmpty().hasSize(1);
    
        all.getContent().forEach( programmeDto -> {
            assertThat(programmeDto.getNumProg()).startsWith("PR17");
            assertThat(programmeDto.getRionTheorique()).isEqualTo(619);
        });
    }
    */
    @Test
    public void count_nb_fichiers_par_programme() {
    
        Page<Object[]> all = programmeDao.findAllProgrammeByCriteria(PAGEABLE);
        
        assertThat(all).isNotNull();
    
    }

}
