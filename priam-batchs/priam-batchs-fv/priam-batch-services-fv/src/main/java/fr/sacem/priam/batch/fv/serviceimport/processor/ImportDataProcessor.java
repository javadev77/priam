package fr.sacem.priam.batch.fv.serviceimport.processor;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;
import fr.sacem.priam.batch.common.dao.AyanrtDroitPersDao;
import fr.sacem.priam.batch.common.dao.AyantDroitDao;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ImportDataProcessor implements ItemProcessor<ExportCsvDto, ExportCsvDto> {
    private JobExecution jobExecution;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    AyanrtDroitPersDao ayanrtDroitPersDao;

    @Autowired
    AyantDroitDao ayantDroitDao;

    @Autowired
    FichierDao fichierDao;

    @Autowired
    ProgrammeDao programmeDao;

    private final ImmutableMap<String, String> typeFonds = ImmutableMap.<String, String>builder()
          .put("FD01","OEUVRE_AD")
          .put("FD02","OEUVRE_AD")
          .put("FD03","AYANT_DROIT")
          .put("FD04","AYANT_DROIT")
          .put("FD05","OEUVRE_AD")
          .put("FD06","OEUVRE")
          .put("FD07","OEUVRE_AD")
          .put("FD09","AYANT_DROIT")
          .put("FD10","AYANT_DROIT")
          .put("FD11","AYANT_DROIT")
          .put("FD12","OEUVRE")
          .put("FD13","AYANT_DROIT")
          .put("FD14","OEUVRE").build();




    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        jobExecution = stepExecution.getJobExecution();

    }

    @Override
    public ExportCsvDto process(final ExportCsvDto exportCsvDto) throws Exception {
        boolean numpersExist = ayanrtDroitPersDao.isNumpersExist(String.valueOf(exportCsvDto.getNumPers()));
        exportCsvDto.setNumpersExist(numpersExist);

      /*  boolean ayantDroitExist = ayantDroitDao.isAyantDroitExist(exportCsvDto.getCoad());
        exportCsvDto.setAyantDroitExist(ayantDroitExist);*/

        String numProg = jobExecution.getJobParameters().getString("numProg");
        Programme programme = programmeDao.findOne(numProg);
        String cdeTypUtil = programme.getTypeUtilisation().getCode();
        String typeFond = this.typeFonds.get(cdeTypUtil);
        Long idFichierLink = exportCsvDto.getIdFichier();
        if("AYANT_DROIT".equalsIgnoreCase(typeFond)) {
            exportCsvDto.setImportAD(true);

            //Create virtual link on LigneProgrammeFV
            LigneProgrammeFV virtualLink = new LigneProgrammeFV();
            virtualLink.setFichier(fichierDao.findOne(idFichierLink));
            LigneProgrammeFV result = ligneProgrammeFVDao.saveAndFlush(virtualLink);

            exportCsvDto.setIdOeuvreFv(result.getId());

            prepareDto(exportCsvDto, result);
            return exportCsvDto;
        }

        exportCsvDto.setImportAD(false);

        Long ide12RepCoad = exportCsvDto.getIde12RepCoad();
        LigneProgrammeFV ligneProgrammeFV = ligneProgrammeFVDao.findOeuvreByIde12(ide12RepCoad, idFichierLink);

        exportCsvDto.setOeuvreExist(ligneProgrammeFV != null);
        if(exportCsvDto.isOeuvreExist()) {
            if (ligneProgrammeFV != null) {
                exportCsvDto.setIdOeuvreFv(ligneProgrammeFV.getId());
            }
        } else {


            LigneProgrammeFV oeuvre = new LigneProgrammeFV();
            oeuvre.setFichier(fichierDao.findOne(exportCsvDto.getIdFichier()));
            prepareDto(exportCsvDto, oeuvre);

            LigneProgrammeFV result = ligneProgrammeFVDao.saveAndFlush(oeuvre);
            exportCsvDto.setIdOeuvreFv(result.getId());
        }



        return exportCsvDto;
    }

    private void prepareDto(ExportCsvDto exportCsvDto, LigneProgrammeFV oeuvre) throws ParseException {
        oeuvre.setCdeFamilTypUtil(exportCsvDto.getCdeFamilTypUtil());
        oeuvre.setCdeTypUtil(exportCsvDto.getCdeTypUtil());
        oeuvre.setNumProg(String.valueOf(exportCsvDto.getNumProg()));
        oeuvre.setCdeTypIde12(exportCsvDto.getCdeTypIde12());
        oeuvre.setIde12(Longs.tryParse(StringUtils.defaultIfEmpty(exportCsvDto.getIde12(), "")));
        oeuvre.setDurDif(Longs.tryParse(StringUtils.defaultIfEmpty(exportCsvDto.getDurDif(), "")));
        oeuvre.setNbrDif(Longs.tryParse(StringUtils.defaultIfEmpty(exportCsvDto.getNbrDif(), "")));
        oeuvre.setMt(exportCsvDto.getMt());
        oeuvre.setTax(Doubles.tryParse(StringUtils.defaultIfEmpty(exportCsvDto.getTax(), "")));
        oeuvre.setTitreOeuvre(exportCsvDto.getTitreOeuvre());

        String datconslt = exportCsvDto.getDatconslt();
        Date dateConslt = datconslt != null && !datconslt.isEmpty() ? new SimpleDateFormat("yyyyMMdd").parse(datconslt) : null;
        oeuvre.setDatconslt(dateConslt);

        String datsitu = exportCsvDto.getDatsitu();
        Date dateSitu= datsitu != null && !datsitu.isEmpty() ? new SimpleDateFormat("yyyyMMdd").parse(datsitu) : null;
        oeuvre.setDatsitu(dateSitu);
    }
}
