package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.TypeUtilisation;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Lazy
@Transactional(readOnly = true)
public interface TypeUtilisationDao extends JpaRepository<TypeUtilisation, String> {
    
    @Query("SELECT typu FROM TypeUtilisation typu " +
            "WHERE typu.dateDebut is not null " +
            "AND (typu.dateFin is null OR typu.dateFin >= CURRENT_DATE)" +
            "AND typu.codeFamille = :cdefam")
    List<TypeUtilisation> findByCodeFamille(@Param("cdefam") String codeFamille);
}
