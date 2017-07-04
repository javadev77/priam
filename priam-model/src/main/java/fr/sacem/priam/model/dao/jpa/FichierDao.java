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


/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Lazy
public interface FichierDao extends JpaRepository<Fichier, Long> {
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.statut IN (:status) " +
            "AND (:familleCode IS NULL OR fam.code = :familleCode) " +
            "AND (:typeUtilisationCode IS NULL OR typu.code = :typeUtilisationCode)")
    Page<FileDto> findAllFichiersByCriteria(@Param("familleCode") String familleCode, @Param("typeUtilisationCode") String typeUtilisationCode, @Param("status") List<Status> status, Pageable pageable);
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.statut IN (:status) ")
      
    Page<FileDto> findAllFichiersByStatus(@Param("status") List<Status> status, Pageable pageable);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.statut= :statut WHERE f.id = :id")
    void updateFichierStatus(@Param("id") Long fileId, @Param("statut") Status status);
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.id = :id ")
    FileDto findById(@Param("id") Long fileId);
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nomFichier, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
        "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
        "WHERE f.statut IN (:status) " +
        "AND (:familleCode IS NULL OR fam.code = :familleCode) " +
        "AND (:numProg IS NULL OR f.programme.numProg = :numProg) " +
        "AND (:typeUtilisationCode IS NULL OR typu.code = :typeUtilisationCode) " +
        "ORDER BY f.dateFinChargt DESC ")
    List<FileDto> findFichiersAffectes(@Param("familleCode") String familleCode, @Param("typeUtilisationCode") String typeUtilisationCode,
                                            @Param("status") List<Status> status, @Param("numProg") String numProg);
    @Transactional(readOnly =true)
    @Query("SELECT f " +
            "FROM Fichier AS f " +
            "WHERE f.programme.numProg = :numProg " +
            "AND f.statut=:status")
    List<Fichier> findFichiersByIdProgramme(@Param("numProg") String numProg,@Param("status") Status status);


    @Transactional(readOnly =true)
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.programme.numProg = NULL, f.statut =:status WHERE f.programme.numProg = :numProg")
    void clearSelectedFichiers(@Param("numProg") String numProg,@Param("status") Status status);

    @Transactional(readOnly =true)
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.programme.numProg = :numProg, f.statut =:status  WHERE f.id IN (:idFichiers) ")
    void updateStatusFichiersAffectes(@Param("numProg") String numProg,@Param("status") Status status,@Param("idFichiers") List<Long> idFichiers);
}

