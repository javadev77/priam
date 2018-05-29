package fr.sacem.priam.model.dao.jpa;

import fr.sacem.priam.model.domain.saref.SareftjLibfamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftjLibfamiltyputilPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SareftjLibfamiltyputilDao extends JpaRepository<SareftjLibfamiltyputil, SareftjLibfamiltyputilPK> {

    @Query("SELECT lib " +
            "FROM SareftjLibfamiltyputil AS lib " +
            "WHERE (lib.code =:code) " +
            "AND (lib.sareftrFamiltyputil.dateDebut is null OR " +
            "DATE_FORMAT(lib.sareftrFamiltyputil.dateDebut, '%Y-%d-%m') = '0000-00-00' OR " +
            "lib.sareftrFamiltyputil.dateDebut <= CURRENT_DATE) " +
            "AND (lib.sareftrFamiltyputil.dateFin is null OR " +
            "DATE_FORMAT(lib.sareftrFamiltyputil.dateFin, '%Y-%d-%m') = '0000-00-00' OR " +
            "lib.sareftrFamiltyputil.dateFin >= CURRENT_DATE) " +
            "AND  lib.lang = :lang")
    SareftjLibfamiltyputil findByCodeAndLang(@Param("code") String code, @Param("lang") String lang);
}
