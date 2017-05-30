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
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nom, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.statut IN (:status) " +
            "AND (:familleCode IS NULL OR fam.code = :familleCode) " +
            "AND (:typeUtilisationCode IS NULL OR typu.code = :typeUtilisationCode) " +
            /*"AND fam.dateDebut is not null " +
            "AND fam.dateFin is null " +
            "AND typu.dateDebut is not null " +
            "AND typu.dateFin is null " +*/
            "ORDER BY f.dateDebutChargt DESC")
    Page<FileDto> findAllFichiersByCriteria(@Param("familleCode") String familleCode, @Param("typeUtilisationCode") String typeUtilisationCode, @Param("status") List<Status> status, Pageable pageable);
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nom, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.statut IN (:status) " +
      /*      "AND f.famille.dateDebut is not null " +
            "AND (f.famille.dateFin is null OR f.famille.dateFin > CURRENT_DATE) " +
            "AND f.typeUtilisation.dateDebut is not null " +
            "AND (f.typeUtilisation.dateFin is null OR f.typeUtilisation.dateFin > CURRENT_DATE) " +*/
            "ORDER BY f.dateDebutChargt DESC")
    Page<FileDto> findAllFichiersByStatus(@Param("status") List<Status> status, Pageable pageable);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Fichier f SET f.statut= :statut WHERE f.id = :id")
    void updateFichierStatus(@Param("id") Long fileId, @Param("statut") Status status);
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.model.domain.dto.FileDto(f.id, f.nom, fam.code, typu.code, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
            "FROM Fichier AS f JOIN f.famille AS fam JOIN f.typeUtilisation AS typu " +
            "WHERE f.id = :id ")
   /*         "AND f.famille.dateDebut is not null " +
            "AND (f.famille.dateFin is null OR f.famille.dateFin > CURRENT_DATE) " +
            "AND f.typeUtilisation.dateDebut is not null " +
            "AND (f.typeUtilisation.dateFin is null OR f.typeUtilisation.dateFin > CURRENT_DATE) ")*/
    FileDto findById(@Param("id") Long fileId);
}

