package fr.sacem.priam.model.dao.jpa;

import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 09/05/2017.
 */


public class FichierDaoTest extends AbstractDaoTest {
    
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
    private SareftrTyputilDao sareftrTyputilDao;

    @Autowired
    private ProgrammeDao programmeDao;
    
    @Test
    @Transactional
    public void should_find_all_fichiers_affectes() {
        List<Status> status = Arrays.asList(Status.values());
        List<FileDto> all = fichierDao.findFichiersAffectes(Lists.newArrayList("COPIEPRIV"),
                Lists.newArrayList("COPRIVSON"), status, null);
    
        assertThat(all)
            .isNotNull()
            .isNotEmpty();
            
    }
    
    @Test
    @Transactional
    public void should_find_all_fichiers_by_status() {
        List<Status> status = Arrays.asList(Status.values());
        Page<FileDto> allFichiersByStatus = fichierDao.findAllFichiersByStatus(status, PAGEABLE);
    
        assertThat(allFichiersByStatus).isNotNull().isNotEmpty();
        assertThat(allFichiersByStatus.getContent()).isNotEmpty().hasSize(3);
    }
    
    @Test
    @Transactional
    public void should_find_all_fichiers_with_null_criteria() {
        List<Status> status = Arrays.asList(Status.values());
        List<String> typeUtilCode = Arrays.asList("COPRIVSON", "CPRIVSONPH", "CPRIVAUDV", "CPRIVSONRD", "CPRIVAUDPL");
        Page<FileDto> allFichiersByStatus = fichierDao.findAllFichiersByCriteria(Lists.newArrayList("COPIEPRIV"),
                typeUtilCode, status, PAGEABLE);
        
        assertThat(allFichiersByStatus).isNotNull().isNotEmpty();
        assertThat(allFichiersByStatus.getTotalElements()).isEqualTo(17L);
    
        
    }
    
    @Test
    @Transactional
    public void should_find_all_fichiers_by_famille_COPIEPRIVEE() {
        List<Status> status = Arrays.asList(Status.values());
        List<String> copiepriv = Lists.newArrayList("COPIEPRIV");

        List<String> typeUtilCode = Arrays.asList("COPRIVSON", "CPRIVSONPH", "CPRIVAUDV", "CPRIVSONRD", "CPRIVAUDPL");
        Page<FileDto> allFichiersByStatus = fichierDao.findAllFichiersByCriteria(copiepriv, typeUtilCode, status, PAGEABLE);
        
        assertThat(allFichiersByStatus).isNotNull().isNotEmpty();
        

        allFichiersByStatus.getContent().forEach( fileDto -> {
            assertThat(fileDto.getFamille()).isEqualTo("COPIEPRIV");
            assertThat(fileDto.getTypeUtilisation()).isIn(typeUtilCode);
        });
    
    }

    @Test
    @Transactional
    public void clearSelectedFichiersTest(){
        fichierDao.clearSelectedFichiers("170001",Status.AFFECTE);
        List<Fichier> all = fichierDao.findFichiersByIdProgramme("170001",Status.AFFECTE);

        assertThat(all)
                .hasSize(0);
    }
    @Test
    @Transactional
    public void updateStatusFichiersAffectesTest(){
        List<Long> idFichiers= new ArrayList<>();
        idFichiers.add(125l);
        idFichiers.add(126l);
        idFichiers.add(127l);
        idFichiers.add(128l);
        fichierDao.updateStatusFichiersAffectes("170001",Status.AFFECTE,idFichiers);
        List<Fichier> all = fichierDao.findFichiersByIdProgramme("170001",Status.AFFECTE);

        assertThat(all)
                .isNotNull();
    }
    @Test
    @Transactional
    public void findFichiersByIdProgrammeTest(){
        List<Fichier> all = fichierDao.findFichiersByIdProgramme("170001",Status.AFFECTE);
        assertThat(all)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4);
    }
}
