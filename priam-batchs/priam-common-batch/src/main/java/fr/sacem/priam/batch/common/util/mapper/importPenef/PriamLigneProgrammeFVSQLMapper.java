package fr.sacem.priam.batch.common.util.mapper.importPenef;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by embouazzar on 28/11/2018.
 */
public class PriamLigneProgrammeFVSQLMapper implements RowMapper<LigneProgrammeFV> {

    public LigneProgrammeFV mapRow(ResultSet rs, int rowNum) throws SQLException {

        LigneProgrammeFV ligneProgrammeFV = new LigneProgrammeFV();

        ligneProgrammeFV.setId(rs.getLong("id"));
        ligneProgrammeFV.setIdFichier(rs.getLong("ID_FICHIER"));
        ligneProgrammeFV.setCdeCisac(rs.getString("cdeCisac"));
        ligneProgrammeFV.setCdeFamilTypUtil(rs.getString("cdeFamilTypUtil"));
        ligneProgrammeFV.setNumProg(rs.getLong("numProg"));
        ligneProgrammeFV.setCdeUtil(rs.getString("cdeUtil"));
        ligneProgrammeFV.setCdeTypUtil(rs.getString("cdeTypUtil"));
        ligneProgrammeFV.setCdeGreDif(rs.getString("cdeGreDif"));
        ligneProgrammeFV.setCdeModDif(rs.getString("cdeModDif"));
        ligneProgrammeFV.setCdeTypIde12(rs.getString("cdeTypIde12"));
        ligneProgrammeFV.setIde12(rs.getString("ide12"));
        ligneProgrammeFV.setDurDif(rs.getString("durDif"));
        ligneProgrammeFV.setNbrDif(rs.getString("nbrDif"));
        ligneProgrammeFV.setMt(rs.getDouble("mt"));
        ligneProgrammeFV.setCtna(rs.getString("ctna"));
        ligneProgrammeFV.setParamCoefHor(rs.getString("paramCoefHor"));
        ligneProgrammeFV.setDurDifCtna(rs.getString("durDifCtna"));
        ligneProgrammeFV.setCdeLng(rs.getString("cdeLng"));
        ligneProgrammeFV.setIndDoubSsTit(rs.getString("indDoubSsTit"));
        ligneProgrammeFV.setTax(rs.getString("tax"));
        ligneProgrammeFV.setTypMt(rs.getString("typMt"));
        ligneProgrammeFV.setCdeGreIde12Cmplx(rs.getString("cdeGreIde12Cmplx"));
        ligneProgrammeFV.setCdeGreIde12(rs.getString("cdeGreIde12"));
        ligneProgrammeFV.setTitreOriCmplx(rs.getString("titreOriCmplx"));
        ligneProgrammeFV.setTitreAltPppalCmplx(rs.getString("titreAltPppalCmplx"));
        ligneProgrammeFV.setTitreOriOeuvPereCmplx(rs.getString("titreOriOeuvPereCmplx"));
        ligneProgrammeFV.setTitreAltOeuvPereCmplx(rs.getString("titreAltOeuvPereCmplx"));
        ligneProgrammeFV.setTitreOeuvre(rs.getString("titreOeuvre"));
        ligneProgrammeFV.setCdePaysOriIso4NCmplx(rs.getString("cdePaysOriIso4NCmplx"));
        ligneProgrammeFV.setRealisateurCmplx(rs.getString("realisateurCmplx"));
        ligneProgrammeFV.setRoleParticipant1(rs.getString("roleParticipant1"));
        ligneProgrammeFV.setNomParticipant1(rs.getString("nomParticipant1"));
        ligneProgrammeFV.setCdeTypUtilOri(rs.getString("cdeTypUtilOri"));
        ligneProgrammeFV.setCdeFamilTypUtilOri(rs.getString("cdeFamilTypUtilOri"));
        ligneProgrammeFV.setUtilisateur(rs.getString("utilisateur"));
        ligneProgrammeFV.setLibelleUtilisateur(rs.getString("libelleUtilisateur"));
        ligneProgrammeFV.setDate_insertion(rs.getString("date_insertion"));
        ligneProgrammeFV.setAjout(rs.getString("ajout"));
        ligneProgrammeFV.setSelection(rs.getString("selection"));
        ligneProgrammeFV.setRionEffet(rs.getString("rionEffet"));

        return ligneProgrammeFV;
    }

}
