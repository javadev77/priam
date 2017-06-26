package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.ProgrammeView;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.TypeRepart;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@Lazy
public interface ProgrammeViewDao extends JpaRepository<ProgrammeView, String> {
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT new fr.sacem.priam.model.domain.dto.ProgrammeDto(pr.numProg, pr.nom, pr.famille, pr.typeUtilisation, " +
                                                    "pr.rionTheorique, pr.dateCreation, pr.typeRepart, pr.statut, pr.rionPaiement, pr.fichiers) " +
                    "FROM ProgrammeView AS pr " +
                    "WHERE (pr.numProg like %:numProg% OR :numProg IS NULL) " +
                    "AND (pr.nom like %:nom% OR :nom IS NULL) " +
                    "AND (pr.dateCreation >= :dateCreationDebut OR :dateCreationDebut IS NULL ) " +
                    "AND (pr.dateCreation <= :dateCreationFin OR :dateCreationFin IS NULL ) " +
                    "AND (pr.famille = :famille OR :famille IS NULL) " +
                    "AND (pr.typeUtilisation = :typeUtilisation OR :typeUtilisation IS NULL) " +
                    "AND (pr.typeRepart = :typeRepart OR :typeRepart IS NULL) " +
                    "AND (pr.rionTheorique = :rionTheorique OR :rionTheorique IS NULL) " +
                    "AND (pr.rionPaiement = :rionPaiement OR :rionPaiement IS NULL) " +
                    "AND (pr.statut IN (:statut) )")
    Page<ProgrammeDto> findAllProgrammeByCriteria(@Param("numProg") String numProg, @Param("nom") String nom,
                                                  @Param("statut") List<StatutProgramme> statut,  @Param("dateCreationDebut") Date dateCreationDebut,
                                                  @Param("dateCreationFin") Date dateCreationFin, @Param("famille") String famille,
                                                  @Param("typeUtilisation") String typeUtilisation, @Param("rionTheorique") Integer rionTheorique,
                                                  @Param("rionPaiement") Integer rionPaiement, @Param("typeRepart") TypeRepart typeRepart, Pageable pageable);
    
    
    @Transactional(readOnly = true)
    @Query(value = "SELECT new fr.sacem.priam.model.domain.dto.ProgrammeDto(pr.numProg, pr.nom, pr.famille, pr.typeUtilisation, " +
        "pr.rionTheorique, pr.dateCreation, pr.typeRepart, pr.statut, pr.rionPaiement, pr.fichiers) " +
        "FROM ProgrammeView AS pr " +
        "WHERE (pr.numProg = :numProg) ")
    ProgrammeDto findByNumProg(@Param("numProg") String numProg);

}
