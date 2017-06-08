package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.ProgrammeView;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
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
public interface ProgrammeDao extends JpaRepository<ProgrammeView, String> {
    
    /*@Transactional(readOnly = true)
    @Query(value = "SELECT pr, " +
            "(SELECT count(f.id) FROM Fichier as f where f.programme.numProg = pr.numProg) as fichiers " +
            "FROM Programme AS pr " +
            "GROUP BY pr.numProg ",
            countQuery = "SELECT count(pr.numProg) FROM Programme AS pr")
    Page<Object[]> findAllProgrammeByCriteria(Pageable pageable);*/
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT new fr.sacem.priam.model.domain.dto.ProgrammeDto(pr.numProg, pr.nom, pr.famille, pr.typeUtilisation, " +
                                                    "pr.rionTheorique, pr.dateCreation, pr.typeRepart, pr.statut, pr.rionPaiement, pr.fichiers) " +
            "FROM ProgrammeView AS pr ")
    Page<ProgrammeDto> findAllProgrammeByCriteria(Pageable pageable);
    
}
