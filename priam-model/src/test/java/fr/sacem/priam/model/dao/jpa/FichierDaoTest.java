package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.dao.JpaConfigurationTest;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfigurationTest.class})
public class FichierDaoTest {
    
    public static final Pageable PAGEABLE = new Pageable() {
        @Override
        public int getPageNumber() {
            return 0;
        }
        
        @Override
        public int getPageSize() {
            return 3;
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
    private FichierDao fichierDao;
    
    @Autowired
    private TypeUtilisationDao typeUtilisationDao;
    
    @Test
    public void should_find_all_fichiers() {
    
        List<Fichier> all = fichierDao.findAll();
    
        assertThat(all).isNotNull().isNotEmpty();
    }
    
    @Test
    public void should_find_all_fichiers_by_status() {
        List<Status> status = Arrays.asList(Status.values());
        Page<FileDto> allFichiersByStatus = fichierDao.findAllFichiersByStatus(status, PAGEABLE);
    
        assertThat(allFichiersByStatus).isNotNull().isNotEmpty();
        assertThat(allFichiersByStatus.getContent()).isNotEmpty().hasSize(3);
    }
    
    @Test
    public void should_find_all_fichiers_with_null_criteria() {
        List<Status> status = Arrays.asList(Status.values());
        Page<FileDto> allFichiersByStatus = fichierDao.findAllFichiersByCriteria(null, null, status, PAGEABLE);
        
        assertThat(allFichiersByStatus).isNotNull().isNotEmpty();
        assertThat(allFichiersByStatus.getTotalElements()).isEqualTo(fichierDao.findAll().size());
        assertThat(allFichiersByStatus.getContent()).isNotEmpty().hasSize(3);
    
        
    }
    
    @Test
    public void should_find_all_fichiers_by_famille_COPIEPRIVEE() {
        List<Status> status = Arrays.asList(Status.values());
        String copiepriv = "COPIEPRIV";
        Page<FileDto> allFichiersByStatus = fichierDao.findAllFichiersByCriteria(copiepriv, null, status, PAGEABLE);
        
        assertThat(allFichiersByStatus).isNotNull().isNotEmpty();
        
    
        List<String> typeUtilCode = Arrays.asList("COPRIVSON", "CPRIVSONPH", "CPRIVAUDV", "CPRIVSONRD", "CPRIVAUDPL");
        allFichiersByStatus.getContent().forEach( fileDto -> {
            assertThat(fileDto.getFamille()).isEqualTo(copiepriv);
            assertThat(fileDto.getTypeUtilisation()).isIn(typeUtilCode);
        });
    
    }

}
