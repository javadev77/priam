package fr.sacem.priam.model.dao.jpa;


import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Lazy
@Transactional(value="transactionManager")
public interface FichierDao extends JpaRepository<Fichier, Long> {
    
    @Transactional(value="transactionManager",  readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut, f.statutEnrichissementFV) " +
            "FROM Fichier AS f LEFT JOIN f.famille AS fam LEFT JOIN f.typeUtilisation AS typu " +
            "WHERE f.statut IN (:status) " +
            "AND (fam.code IN (:familleCode) OR fam.code IS NULL) " +
            "AND (typu.code IN (:typeUtilisationCode) OR typu.code IS NULL) " +
            "AND f.automatique = true ")
    Page<FileDto> findAllFichiersByCriteria(@Param("familleCode") List<String> familleCode, @Param("typeUtilisationCode") List<String> typeUtilisationCode, @Param("status") List<Status> status, Pageable pageable);
    
    @Transactional(value="transactionManager",  readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.statut IN (:status) AND f.automatique = true ")

    Page<FileDto> findAllFichiersByStatus(@Param("status") List<Status> status, Pageable pageable);


    @Transactional(value="transactionManager")
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.statut= :statut WHERE f.id = :id")
    void updateFichierStatus(@Param("id") Long fileId, @Param("statut") Status status);
    
    @Transactional(value="transactionManager",  readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.id = :id ")
    FileDto findById(@Param("id") Long fileId);
    
    @Transactional(value="transactionManager",  readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut, f.statutEnrichissementFV) " +
        "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
        "WHERE f.statut IN (:status) " +
        "AND (fam.code IN (:familleCode)) " +
        "AND (f.programme.numProg IS NULL OR f.programme.numProg = :numProg) " +
        "AND (typu.code IN (:typeUtilisationCode)) " +
        "AND f.automatique = true " +
        "ORDER BY f.dateFinChargt DESC ")
    List<FileDto> findFichiersAffectes(@Param("familleCode") List<String> familleCode, @Param("typeUtilisationCode") List<String> typeUtilisationCode,
                                       @Param("status") List<Status> status, @Param("numProg") String numProg);

   @Transactional(value="transactionManager",  readOnly =true)
    @Query("SELECT f " +
            "FROM Fichier AS f " +
            "WHERE f.programme.numProg = :numProg " +
            "AND f.statut=:status")
    List<Fichier> findFichiersByIdProgramme(@Param("numProg") String numProg, @Param("status") Status status);

    @Transactional(value="transactionManager",  readOnly =true)
    @Query("SELECT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f " +
            "WHERE f.programme.numProg = :numProg " +
            "AND f.statut=:status")
    List<FileDto> findFichiersAffecteByIdProgramme(@Param("numProg") String numProg, @Param("status") Status status);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.statutEnrichissementFV = NULL WHERE f.id = :idFichier")
    void updateStatutEnrichissement(@Param("idFichier") Long idFichier);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.programme.numProg = NULL, f.statutEnrichissementFV = NULL, f.statut = 'CHARGEMENT_OK' WHERE f.programme.numProg = :numProg")
    void updateStatutEnrichissementFichiersAffectes(@Param("numProg") String numProg);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.programme.numProg = NULL, f.statut =:status WHERE f.programme.numProg = :numProg")
    void clearSelectedFichiers(@Param("numProg") String numProg,@Param("status") Status status);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.programme.numProg = :numProg, f.statut =:status  WHERE f.id IN (:idFichiers) ")
    void updateStatusFichiersAffectes(@Param("numProg") String numProg,@Param("status") Status status,@Param("idFichiers") List<Long> idFichiers);

    Fichier findByNomFichier(String s);

    @Transactional(value="transactionManager",  readOnly =true)
    @Query(nativeQuery = true, value="SELECT LOG from  PRIAM_FICHIER_LOG FL WHERE FL.ID_FICHIER = ?1 ORDER BY LOG")
    Set<String> getChargementLog(@Param("idFichier") Long idFichier);



    @Transactional(value="transactionManager",  readOnly =true)
    @Query("SELECT f " +
            "FROM Fichier AS f " +
            "WHERE f.programme.numProg = :numProg " +
            "AND f.automatique = false ")
    Fichier findFichierLink(@Param("numProg")String numProg);
}

