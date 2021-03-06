package fr.sacem.priam.batch.common.dao;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fandis on 15/12/2017.
 */
@Repository
public class LigneProgrammeBatchDao {
    private static  final Logger LOGGER = LoggerFactory.getLogger(LigneProgrammeBatchDao.class);

    private JdbcTemplate jdbcTemplate;

    private String nomTableLigneProgramme;

    public LigneProgrammeBatchDao() {

    }

    @Transactional(value="transactionManager")
    public void deleteDedoublonnage(String numProg) {
        String selectSql = "SELECT l2.id as id " +
        "FROM PRIAM_LIGNE_PROGRAMME_CMS l2 " +
                "INNER JOIN PRIAM_FICHIER f ON l2.ID_FICHIER=f.ID " +
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "AND l2.id not IN (SELECT temp.id3 "+
                "from ( "+
                "select min(l.id) as id3 "+
                "from PRIAM_LIGNE_PROGRAMME_CMS l "+
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID "+
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "group by l.ide12, l.cdeFamilTypUtilOri) as  temp) ";



        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM " + this.nomTableLigneProgramme + " WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }



    public Double countSommePoints(String numProg) {
        String sql =  "SELECT sum(points) as points from ( SELECT " +
                "(CASE WHEN l.cdeTypUtil = 'SONOFRA' THEN sum(l.mt)  WHEN l.cdeTypUtil = 'SONOANT' THEN sum(l.nbrDif) ELSE 0 END) points, l.ide12 " +
                "FROM " +
                "PRIAM_LIGNE_PROGRAMME_CMS l inner join PRIAM_FICHIER as f " +
                "on l.ID_FICHIER=f.ID " +
                "WHERE " +
                "f.numProg = ? " +
                "GROUP BY l.ide12) result  ";
        Double points = jdbcTemplate.queryForObject(sql, (resultSet, i) ->
                resultSet.getDouble("points"), numProg);

        return points;
    }

    public Long countNbOeuvres(String numProg) {
        String sql =  "SELECT " +
                "count(l.id) as nbOeuvres " +
                "FROM " +
                "PRIAM_LIGNE_PROGRAMME_CMS l inner join PRIAM_FICHIER as f " +
                "on l.ID_FICHIER=f.ID " +
                "WHERE " +
                "f.numProg = ? ";
        Long nbOeuvres = jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("nbOeuvres"), numProg);

        return nbOeuvres;
    }


    public Long countNbOeuvresCatalogue(String typeCMS) {
        String sql =  "SELECT " +
                "count(cat.id) as nbOeuvres " +
                "FROM PRIAM_CATALOGUE_OCTAV cat " +
                "WHERE cat.TYPE_CMS='" + typeCMS + "'";
        Long nbOeuvres = jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getLong("nbOeuvres"));

        return nbOeuvres;
    }


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String getNomTableLigneProgramme() {
        return nomTableLigneProgramme;
    }

    public void setNomTableLigneProgramme(String nomTableLigneProgramme) {
        this.nomTableLigneProgramme = nomTableLigneProgramme;
    }

    @Transactional(value="transactionManager")
    public void deleteDoublonsMemeMontant(String numProg) {

        String selectSql = "SELECT l2.id as id " +
                "FROM PRIAM_LIGNE_PROGRAMME_CMS l2 " +
                "INNER JOIN PRIAM_FICHIER f ON l2.ID_FICHIER=f.ID " +
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "AND l2.id not IN (SELECT temp.id3 "+
                "from ( "+
                "select min(l.id) as id3 "+
                "from PRIAM_LIGNE_PROGRAMME_CMS l "+
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID "+
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "group by l.ide12, l.mt) as  temp) ";



        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);

        LOGGER.info("Les lignes CMS à supprimer qui ont le meme montant : Ids=" + ids);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM " + this.nomTableLigneProgramme + " WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }

    @Transactional(value="transactionManager")
    public void deleteAfterCalculPointsAnt(String numProg){
        String selectSql = "SELECT ligne.id as id " +
                "FROM PRIAM_LIGNE_PROGRAMME_CMS ligne " +
                "INNER JOIN PRIAM_FICHIER fichier ON ligne.ID_FICHIER = fichier.ID " +
                "INNER JOIN PRIAM_PROGRAMME programme ON fichier.NUMPROG = programme.NUMPROG " +
                "WHERE programme.NUMPROG=? " +
                "AND programme.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' ";


        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM " + this.nomTableLigneProgramme + " WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }

    @Transactional(value="transactionManager")
    public void deleteDedoublonnageCP(String numProg) {
        String selectSql = "SELECT l2.id as id " +
                "FROM PRIAM_LIGNE_PROGRAMME_CP l2 " +
                "INNER JOIN PRIAM_FICHIER f ON l2.ID_FICHIER=f.ID " +
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "AND l2.id not IN (SELECT temp.id3 "+
                "from ( "+
                "select min(l.id) as id3 "+
                "from PRIAM_LIGNE_PROGRAMME_CP l "+
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID "+
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "group by l.ide12, l.cdeUtil) as  temp) ";



        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM PRIAM_LIGNE_PROGRAMME_CP WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }

    @Transactional(value="transactionManager")
    public void deleteDonneesLigneCP(Long idFichier) {
        deleteDonneesLigneProg(idFichier, "PRIAM_LIGNE_PROGRAMME_CP");
    }

    public void deleteDonneesLigneFV(Long idFichier) {

        jdbcTemplate.update("DELETE FROM PRIAM_AYANT_DROIT WHERE ID_FV IN (" +
            "SELECT id FROM PRIAM_LIGNE_PROGRAMME_FV WHERE ID_FICHIER=?) ", idFichier);

        String fromTableLignePrg = "PRIAM_LIGNE_PROGRAMME_FV";
        deleteDonneesLigneProg(idFichier, fromTableLignePrg);
    }

    private void deleteDonneesLigneProg(final Long idFichier, final String fromTableLignePrg) {
        String sql = "DELETE FROM " + fromTableLignePrg + " WHERE ID_FICHIER=?";
        jdbcTemplate.update(sql, idFichier);
    }

    //@Transactional(value="transactionManager")
    public void deleteDedoublonnageFVAD(final String numProg) {

        /*String selectSql = "SELECT l2.id as id " +
            "FROM PRIAM_LIGNE_PROGRAMME_FV l2 " +
            "INNER JOIN PRIAM_FICHIER f ON l2.ID_FICHIER=f.ID " +
            "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
            "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
            "AND l2.id not IN (SELECT temp.id3 "+
            "from ( "+
            "select distinct l.id as id3 "+
            "from PRIAM_LIGNE_PROGRAMME_FV l "+
            "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID "+
            "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG " +
            "INNER JOIN PRIAM_AYANT_DROIT ad on l.id = ad.ID_FV "+
            "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
            ") as  temp) ";*/

        String selectSql = "SELECT l2.id as id " +
                "            FROM PRIAM_LIGNE_PROGRAMME_FV l2 " +
                "            INNER JOIN PRIAM_FICHIER f ON l2.ID_FICHIER=f.ID " +
                "            INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG " +
                "            WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' AND l2.isOeuvreComplex = 0 " +
                "            AND l2.id not IN (SELECT temp.id3 " +
                "            from ( " +
                "                   SELECT AD.ID_FV AS id3 " +
                "                   FROM PRIAM_LIGNE_PROGRAMME_FV FV " +
                "                          INNER JOIN PRIAM_FICHIER PF ON FV.ID_FICHIER = PF.ID " +
                "                          INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=PF.NUMPROG " +
                "                          INNER JOIN PRIAM_AYANT_DROIT AD ON FV.id = AD.ID_FV " +
                "                   WHERE PF.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' " +
                "                   GROUP BY AD.COAD, AD.ROLAD " +
                "            ) as  temp) ";



        List<Long> ids = jdbcTemplate.query(selectSql,
            (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM PRIAM_LIGNE_PROGRAMME_FV WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }
    @Transactional(value="transactionManager")
    public void deleteDedoublonnageFVOeuvre(final String numProg) {

        String selectSql = "SELECT l2.id as id " +
                "FROM PRIAM_LIGNE_PROGRAMME_FV l2 " +
                "INNER JOIN PRIAM_FICHIER f ON l2.ID_FICHIER=f.ID " +
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "AND l2.id not IN (SELECT temp.id3 "+
                "from ( "+
                "select min(l.id) as id3 "+
                "from PRIAM_LIGNE_PROGRAMME_FV l "+
                "INNER JOIN PRIAM_FICHIER f ON l.ID_FICHIER=f.ID "+
                "INNER JOIN PRIAM_PROGRAMME p ON p.NUMPROG=f.NUMPROG "+
                "WHERE p.NUMPROG=? AND p.STATUT_ELIGIBILITE = 'EN_ATTENTE_ELIGIBILITE' "+
                "group by l.ide12) as  temp) ";



        List<Long> ids = jdbcTemplate.query(selectSql,
                (resultSet, i) -> resultSet.getLong("id"), numProg, numProg);

        if(ids != null && !ids.isEmpty()) {
            String sql =  "DELETE FROM PRIAM_LIGNE_PROGRAMME_FV WHERE id IN (" + Joiner.on(",").join(ids).toString() + ") ";
            jdbcTemplate.update(sql);
        }
    }
}

