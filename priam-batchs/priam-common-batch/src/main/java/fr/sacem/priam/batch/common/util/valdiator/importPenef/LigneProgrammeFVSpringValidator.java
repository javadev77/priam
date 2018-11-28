package fr.sacem.priam.batch.common.util.valdiator.importPenef;

import fr.sacem.priam.batch.common.dao.SareftrTyputilDao;
import fr.sacem.priam.batch.common.domain.LigneProgramme;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.domain.SareftrTyputil;
import fr.sacem.priam.common.TypeUtilisationEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Created by embouazzar on 23/11/2018.
 */
@Component
public class LigneProgrammeFVSpringValidator extends CommonValidator implements Validator {

    public static final int IDE12_LENGTH_MAX = 12;
    public static final int IDE12_LENGTH_MIN = 6;
    public static final String CDETYPIDE12_COPT = "COPT";
    public static final String CDETYPIDE12_COCV = "COCV";

    @Autowired
    public SareftrTyputilDao sareftrTyputilDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return LigneProgrammeFV.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String ide12 = ((LigneProgrammeFV) o).getIde12();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtilOri", "error.cdeFamilTypUtilOri");
        if (ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
            errors.rejectValue("ide12", "format.error.ide12");
        }

        //FONDS69
        String cdeTypUtil = ((LigneProgrammeFV)o).getCdeTypUtil();
        String cdeFamilTypUtil = ((LigneProgrammeFV)o).getCdeFamilTypUtil();
        String durDif = ((LigneProgrammeFV)o).getDurDif();
        String cdeTypIde12 = ((LigneProgrammeFV)o).getCdeTypIde12();
        //String cdeTypUtilOri = ((LigneProgrammeFV)o).getCdeTypUtilOri();
        SareftrTyputil sareftrTyputil = new SareftrTyputil();
        sareftrTyputil.setCodeFamille(((LigneProgrammeFV)o).getCdeFamilTypUtilOri());
        sareftrTyputil.setCode(((LigneProgrammeFV)o).getCdeTypUtilOri());

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
        if(cdeFamilTypUtil == null || cdeFamilTypUtil.isEmpty()) {
            errors.rejectValue("cdeFamilTypUtil", "error.cdeFamilTypUtil");
        }else if(cdeTypUtil != null && !cdeTypUtil.isEmpty() &&
                !cdeFamilTypUtil.equals(TypeUtilisationEnum.getValue(cdeTypUtil).getCodeFamille())) {
            errors.rejectValue("cdeFamilTypUtil", "format.error.cdeFamilTypUtil");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mt", "error.mt");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeLng", "error.cdeLng");
        if(durDif == null || durDif.isEmpty()){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tax", "error.tax");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typMt", "error.typMt");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeGreIde12", "error.cdeGreIde12");

        if(CDETYPIDE12_COPT.equals(cdeTypIde12)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOriCmplx", "error.titreOriCmplx");
        } else if(CDETYPIDE12_COCV.equals(cdeTypIde12)){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvre", "error.titreOeuvre");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtilOri", "error.cdeTypUtilOri");
        if(sareftrTyputil != null){
            List<SareftrTyputil> sareftrTyputils = sareftrTyputilDao.findByCdeFamilTypUtilOri(sareftrTyputil.getCodeFamille());
            if(!containsCdeTypUtilOri(sareftrTyputils, sareftrTyputil.getCode())){
                errors.rejectValue("cdeTypUtilOri", "format.error.cdeTypUtilOri");
            }
        }

        validateNumericFields(errors, (LigneProgrammeFV) o);
    }

    public boolean containsCdeTypUtilOri(final List<SareftrTyputil> list, final String cdeTypUtilOri){
        return list.stream().filter(o -> o.getCode().equals(cdeTypUtilOri)).findFirst().isPresent();
    }
}
