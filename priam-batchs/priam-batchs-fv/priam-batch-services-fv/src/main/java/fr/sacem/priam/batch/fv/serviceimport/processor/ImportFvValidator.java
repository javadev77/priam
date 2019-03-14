package fr.sacem.priam.batch.fv.serviceimport.processor;

import static fr.sacem.priam.batch.common.fv.util.CategorieFondsEnum.CAT_04;
import static fr.sacem.priam.batch.common.fv.util.CategorieFondsEnum.getValue;
import fr.sacem.priam.batch.common.util.valdiator.importPenef.CommonValidator;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.model.dao.jpa.SareftrCategAdSacemDao;
import fr.sacem.priam.model.dao.jpa.SareftrFamiltyputilDao;
import fr.sacem.priam.model.dao.jpa.SareftrGreOeuvDao;
import fr.sacem.priam.model.dao.jpa.SareftrSteDao;
import fr.sacem.priam.model.dao.jpa.SareftrTypDrtSacemDao;
import fr.sacem.priam.model.dao.jpa.SareftrTypProtecDao;
import fr.sacem.priam.model.dao.jpa.SareftrTyputilDao;
import fr.sacem.priam.model.domain.TypeDroit;
import fr.sacem.priam.model.domain.saref.SareftrCategAdSacem;
import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftrGreOeuv;
import fr.sacem.priam.model.domain.saref.SareftrSte;
import fr.sacem.priam.model.domain.saref.SareftrTypDrtSacem;
import fr.sacem.priam.model.domain.saref.SareftrTypProtec;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
@Component
public class ImportFvValidator extends CommonValidator implements Validator  {
    public static final int IDE12_LENGTH_MAX = 12;
    public static final int IDE12_LENGTH_MIN = 6;


    @Autowired
    SareftrSteDao sareftrSteDao;

    @Autowired
    SareftrTypProtecDao sareftrTypProtecDao;

    @Autowired
    SareftrCategAdSacemDao sareftrCategAdSacemDao;

    @Autowired
    SareftrFamiltyputilDao sareftrFamiltyputilDao;

    @Autowired
    SareftrTyputilDao sareftrTyputilDao;

    @Autowired
    SareftrTypDrtSacemDao sareftrTypDrtSacemDao;

    @Autowired
    SareftrGreOeuvDao sareftrGreOeuvDao;


    @Override
    public boolean supports(final Class<?> aClass) {
        return ExportCsvDto.class.equals(aClass);
    }

    @Override
    public void validate(final Object o, final Errors errors) {
        ExportCsvDto exportCsvDto = (ExportCsvDto) o;

        //Regle de validation des données pour les Fonds 01, 02, 05, 07

        //Programme
        rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
        rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");
        rejectIfEmptyOrWhitespace(errors, "numProg", "error.numProg");
        //Oeuvre
        rejectIfEmptyOrWhitespace(errors, "rionEffet", "error.rionEffet");

        String cdeTypUtil = exportCsvDto.getCdeTypUtil();
        if(cdeTypUtil != null && !cdeTypUtil.isEmpty() && CAT_04.equals(getValue(cdeTypUtil))) {
            //Oeuvre
            rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");

            String ide12 = exportCsvDto.getIde12();
            if (ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
                errors.rejectValue("ide12", "format.error.ide12");
            }
            rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");

            //Oeuvre Ctnu
            rejectIfEmptyOrWhitespace(errors, "ide12RepCoad", "error.ide12RepCoad");
            rejectIfEmptyOrWhitespace(errors, "cdeTypIde12RepCoad", "error.cdeTypIde12RepCoad");

            //Ayant Droit
            rejectIfEmptyOrWhitespace(errors, "cleAd", "error.cleAd");
            rejectIfEmptyOrWhitespace(errors, "cdeTypProtect", "error.cdeTypProtect");


        }

        rejectIfEmptyOrWhitespace(errors, "datsitu", "error.datsitu");
        rejectIfEmptyOrWhitespace(errors, "datconslt", "error.datconslt");

        //Ayant Droit
        rejectIfEmptyOrWhitespace(errors, "coad", "error.coad");
        rejectIfEmptyOrWhitespace(errors, "numPers", "error.numPers");
        rejectIfEmptyOrWhitespace(errors, "numCatal", "error.numCatal");
        rejectIfEmptyOrWhitespace(errors, "idSteAd", "error.idSteAd");
        rejectIfEmptyOrWhitespace(errors, "rolAd", "error.rolAd");
        rejectIfEmptyOrWhitespace(errors, "typeDroit", "error.typeDroit");
        rejectIfEmptyOrWhitespace(errors, "coadOriEdtr", "error.coadOriEdtr");
        rejectIfEmptyOrWhitespace(errors, "idSteOriEdtr", "error.idSteOriEdtr");
        rejectIfEmptyOrWhitespace(errors, "points", "error.points");

        List<SareftrSte> all = sareftrSteDao.findAll();
        Optional<SareftrSte> first = all.stream().filter(s -> s.getIdSte().equals(Long.valueOf(exportCsvDto.getIdSteAd()))).findFirst();
        if(!first.isPresent()) {
            errors.rejectValue("idSteAd","saref.error.idSteAd");
        }


        //Verifier le idSteOriEdtr en DE,DR et PH uniquement
        TypeDroit typeDroit = TypeDroit.getEnumValue(exportCsvDto.getTypeDroit());
        if(typeDroit != null) {
            switch (typeDroit) {
                case DE:
                case DR:
                case PH:
                    String idSteOriEdtr = exportCsvDto.getIdSteOriEdtr();
                    Optional<SareftrSte> isExist = all.stream().filter(s -> s.getIdSte().equals(Long.valueOf(idSteOriEdtr))).findFirst();
                    if(!isExist.isPresent()) {
                        errors.rejectValue("idSteOriEdtr","saref.error.idSteOriEdtr");
                    }

                    String cdeTypProtect = exportCsvDto.getCdeTypProtect();
                    List<SareftrTypProtec> sareftrTypProtecs = sareftrTypProtecDao.findAll();
                    Optional<SareftrTypProtec> found = sareftrTypProtecs.stream().filter(s -> s.getCdeTypProtec().equals(cdeTypProtect)).findFirst();
                    if(!found.isPresent()) {
                        errors.rejectValue("cdeTypProtect","saref.error.cdeTypProtect");
                    }

                    break;
            }
        }

        List<SareftrCategAdSacem> sareftrCategAdSacems = sareftrCategAdSacemDao.findAll();
        String rolAd = exportCsvDto.getRolAd();
        Optional<SareftrCategAdSacem> result = sareftrCategAdSacems.stream().filter(s -> s.getCdeCategAdSacem().equals(rolAd)).findFirst();

        if(!result.isPresent()) {
            errors.rejectValue("rolAd","saref.error.rolAd");
        }

        List<SareftrFamiltyputil> familtyputils = sareftrFamiltyputilDao.findAll();
        Optional<SareftrFamiltyputil> rslt = familtyputils.stream().filter(s -> s.getCode().equals(exportCsvDto.getCdeFamilTypUtil())).findFirst();

        if(!rslt.isPresent()) {
            errors.rejectValue("cdeFamilTypUtil","saref.error.cdeFamilTypUtil");
        }

        List<SareftrTyputil> typutils = sareftrTyputilDao.findAll();
        Optional<SareftrTyputil> rsltTypUtil = typutils.stream().filter(s -> s.getCode().equals(exportCsvDto.getCdeTypUtil())).findFirst();

        if(!rsltTypUtil.isPresent()) {
            errors.rejectValue("cdeTypUtil","saref.error.cdeTypUtil");
        }

        List<SareftrTypDrtSacem> typDrtSacems = sareftrTypDrtSacemDao.findAll();
        boolean present = typDrtSacems.stream().anyMatch(s -> s.getCdeTypDrtSacem().equals(exportCsvDto.getTypeDroit()));

        if(!present) {
            errors.rejectValue("typeDroit","saref.error.typeDroit");
        }

        List<SareftrGreOeuv> greOeuvs = sareftrGreOeuvDao.findAll();
        present = greOeuvs.stream().anyMatch(s -> s.getCdeGreOeuv().equals(exportCsvDto.getGenreOeuvre()));

        if(!present) {
            errors.rejectValue("genreOeuvre","saref.error.genreOeuvre");
        }
    }
}
