package fr.sacem.priam.batch.fv.octav.req.processor;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.domain.OctavCtnu;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
@Component
public class OctavCtnuReqProcessor implements ItemProcessor<LigneProgrammeFV, OctavCtnu> {

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Override
    public OctavCtnu process(LigneProgrammeFV oeuvre) throws Exception {

        /*OctavDTO octavDTO = new OctavDTO();
        octavDTO.setIde12(oeuvre.getIde12());
        String dateJour = new SimpleDateFormat("yyyyMMdd").format(new Date());
        octavDTO.setDatConsult(dateJour);
        octavDTO.setDatSitu(dateJour);

        ligneProgrammeFVDao.majOeuvreWithInfoOctav(octavDTO);
        oeuvre.setCdeTer("250");*/
        OctavCtnu octavCtnu = new OctavCtnu();
        octavCtnu.setCdeCisac(oeuvre.getCdeCisac());
        octavCtnu.setCdeTer("250");
        octavCtnu.setCdeFamilTypUtilOri(oeuvre.getCdeFamilTypUtilOri());
        octavCtnu.setCdeTypUtilOri(oeuvre.getCdeTypUtilOri());
        octavCtnu.setCdeUtil(oeuvre.getCdeUtil());
        octavCtnu.setIde12(oeuvre.getIde12());
        octavCtnu.setCdeTypIde12(oeuvre.getCdeTypIde12());
        String dateJour = new SimpleDateFormat("yyyyMMdd").format(new Date());
        octavCtnu.setDatConsult(dateJour);
        octavCtnu.setDatSitu(dateJour);
        octavCtnu.setRionStatut(oeuvre.getRionStatut());
        octavCtnu.setRionCalc(oeuvre.getRionCalc());
        octavCtnu.setCdeLng(oeuvre.getCdeLng());
        octavCtnu.setIndDoubSsTit(oeuvre.getIndDoubSsTit());

        return octavCtnu;

    }
}
