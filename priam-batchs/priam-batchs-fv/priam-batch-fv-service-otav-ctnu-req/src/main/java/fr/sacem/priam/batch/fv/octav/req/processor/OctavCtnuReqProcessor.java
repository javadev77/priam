package fr.sacem.priam.batch.fv.octav.req.processor;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class OctavCtnuReqProcessor implements ItemProcessor<LigneProgrammeFV, LigneProgrammeFV> {

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Override
    public LigneProgrammeFV process(LigneProgrammeFV oeuvre) throws Exception {

        OctavDTO octavDTO = new OctavDTO();
        octavDTO.setIde12(oeuvre.getIde12());
        String dateJour = new SimpleDateFormat("yyyyMMdd").format(new Date());
        octavDTO.setDatConsult(dateJour);
        octavDTO.setDatSitu(dateJour);

        ligneProgrammeFVDao.majOeuvreWithInfoOctav(octavDTO);

        return oeuvre;

    }
}
