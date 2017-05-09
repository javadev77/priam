package fr.sacem.priam.ui.dao;

import fr.sacem.priam.ui.domain.Fichier;
import fr.sacem.priam.ui.rest.dto.FileData;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Lazy
public interface FichierDao extends JpaRepository<Fichier, Long> {
    
    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT new fr.sacem.priam.ui.rest.dto.FileData(f.id, f.nom, f.famille, f.typeUtilisation, f.dateDebutChargt, f.dateFinChargt, f.nbLignes, f.statut) " +
           "FROM Fichier AS f " +
           "ORDER BY f.dateDebutChargt DESC")
    Page<FileData> findAllFichiersByName(Pageable pageable);
}
