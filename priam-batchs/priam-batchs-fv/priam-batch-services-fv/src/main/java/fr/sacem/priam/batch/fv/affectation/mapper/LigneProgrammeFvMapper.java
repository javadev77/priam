package fr.sacem.priam.batch.fv.affectation.mapper;

import fr.sacem.priam.batch.common.domain.LigneProgramme;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created with IntelliJ IDEA.
 * @author Habib BENMERZOUKA
 * @version $Id$
 * @since 1.0
 */
public class LigneProgrammeFvMapper implements RowMapper<LigneProgrammeFV> {
    private static  final Logger LOGGER = LoggerFactory.getLogger(LigneProgrammeFvMapper.class);

    @Override
    public LigneProgrammeFV mapRow(final ResultSet rs, final int i) throws SQLException {
        LigneProgrammeFV ligneProgramme = new LigneProgrammeFV();

        ligneProgramme.setId(rs.getLong("id"));
        ligneProgramme.setIdFichier(rs.getLong("ID_FICHIER"));
        ligneProgramme.setCdeCisac(rs.getString("cdeCisac"));
        ligneProgramme.setCdeFamilTypUtil(rs.getString("cdeFamilTypUtil"));
        ligneProgramme.setNumProg(rs.getLong("numProg"));
        ligneProgramme.setCdeUtil(rs.getString("cdeUtil"));
        ligneProgramme.setCdeTypUtil(rs.getString("cdeTypUtil"));
        ligneProgramme.setCdeGreDif(rs.getString("cdeGreDif"));
        ligneProgramme.setCdeModDif(rs.getString("cdeModDif"));
        ligneProgramme.setCdeTypIde12(rs.getString("cdeTypIde12"));
        ligneProgramme.setIde12(rs.getString("ide12"));
        ligneProgramme.setDurDif(rs.getString("durDif"));
        ligneProgramme.setNbrDif(rs.getString("nbrDif"));
        ligneProgramme.setMt(rs.getDouble("mt"));
        ligneProgramme.setCtna(rs.getString("ctna"));
        ligneProgramme.setParamCoefHor(rs.getString("paramCoefHor"));
        ligneProgramme.setDurDifCtna(rs.getString("durDifCtna"));
        ligneProgramme.setCdeLng(rs.getString("cdeLng"));
        ligneProgramme.setIndDoubSsTit(rs.getString("indDoubSsTit"));
        ligneProgramme.setTax(rs.getString("tax"));
        ligneProgramme.setTypMt(rs.getString("typMt"));
        ligneProgramme.setCdeGreIde12Cmplx(rs.getString("cdeGreIde12Cmplx"));
        ligneProgramme.setCdeGreIde12(rs.getString("cdeGreIde12"));
        ligneProgramme.setTitreOriCmplx(rs.getString("titreOriCmplx"));
        ligneProgramme.setTitreAltPppalCmplx(rs.getString("titreAltPppalCmplx"));
        ligneProgramme.setTitreOriOeuvPereCmplx(rs.getString("titreOriOeuvPereCmplx"));
        ligneProgramme.setTitreAltOeuvPereCmplx(rs.getString("titreAltOeuvPereCmplx"));
        ligneProgramme.setTitreOeuvre(rs.getString("titreOeuvre"));
        ligneProgramme.setCdePaysOriIso4NCmplx(rs.getString("cdePaysOriIso4NCmplx"));
        ligneProgramme.setRealisateurCmplx(rs.getString("realisateurCmplx"));
        ligneProgramme.setRoleParticipant1(rs.getString("roleParticipant1"));
        ligneProgramme.setNomParticipant1(rs.getString("nomParticipant1"));
        ligneProgramme.setCdeTypUtilOri(rs.getString("cdeTypUtilOri"));
        ligneProgramme.setCdeFamilTypUtilOri(rs.getString("cdeFamilTypUtilOri"));
        ligneProgramme.setUtilisateur(rs.getString("utilisateur"));
        ligneProgramme.setDate_insertion(rs.getString("date_insertion"));
        ligneProgramme.setAjout(rs.getString("ajout"));
        ligneProgramme.setSelection(rs.getString("selection"));
        ligneProgramme.setRionEffet(rs.getString("rionEffet"));

        LOGGER.info("LigneProgramme id=" + ligneProgramme.getId() +  " ide12= " + ligneProgramme.getIde12()
            + ", mt=" + ligneProgramme.getMt());


        return ligneProgramme;
    }
}
