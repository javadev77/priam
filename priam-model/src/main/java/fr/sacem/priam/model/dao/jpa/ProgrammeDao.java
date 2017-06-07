package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.Programme;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@Lazy
public interface ProgrammeDao extends JpaRepository<Programme, String> {
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT pr, " +
            "(SELECT count(f.id) FROM Fichier as f where f.programme.numProg = pr.numProg) " +
            "FROM Programme AS pr " +
            "GROUP BY pr ",
            countQuery = "SELECT count(pr.numProg) FROM Programme AS pr")
    Page<Object[]> findAllProgrammeByCriteria(Pageable pageable);
    
}
