package fr.sacem.priam.batch.fv.util.validator;

import fr.sacem.priam.batch.fv.dao.SareftrFamilTypUtilDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.domain.SareftrTyputil;
import fr.sacem.priam.batch.common.util.valdiator.importPenef.CommonValidator;
import fr.sacem.priam.batch.fv.util.CategorieFondsEnum;
import fr.sacem.priam.common.TypeUtilisationEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by embouazzar on 23/11/2018.
 */
@Component
public class LigneProgrammeFVSpringValidator extends CommonValidator implements Validator {

    public static final int IDE12_LENGTH_MAX = 12;
    public static final int IDE12_LENGTH_MIN = 6;
    public static final String CDETYPIDE12_COPT = "COPT";
    public static final String CDETYPIDE12_COCV = "COCV";
    public static final CategorieFondsEnum CAT_01 = CategorieFondsEnum.CAT_01;
    public static final CategorieFondsEnum CAT_02 = CategorieFondsEnum.CAT_02;
    public static final CategorieFondsEnum CAT_03 = CategorieFondsEnum.CAT_03;
    public static final CategorieFondsEnum CAT_04 = CategorieFondsEnum.CAT_04;
    protected static final String[] VAL1_CDEGREIDE12 = {"ME", "MS", "MO"};
    protected static final String[] VAL2_CDEGREIDE12 = {"LP"};
    protected static final String[] VAL3_CDEGREIDE12 = {"TOUS"};
    protected static final String[] VAL4_CDEGREIDE12 = {"IJ"};


    @Autowired
    public SareftrFamilTypUtilDao sareftrFamilTypUtilDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return LigneProgrammeFV.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {


        /*debut : commun à tous les fonds*/
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
            String cdeTypUtil = ((LigneProgrammeFV) o).getCdeTypUtil();
            String cdeFamilTypUtil = ((LigneProgrammeFV) o).getCdeFamilTypUtil();
            if (cdeFamilTypUtil == null || cdeFamilTypUtil.isEmpty()) {
                errors.rejectValue("cdeFamilTypUtil", "error.cdeFamilTypUtil");
            } else if (cdeTypUtil != null && !cdeTypUtil.isEmpty() &&
                    !cdeFamilTypUtil.equals(TypeUtilisationEnum.getValue(cdeTypUtil).getCodeFamille())) {
                errors.rejectValue("cdeTypUtil", "format.error.cdeTypUtil");
            }
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
            String ide12 = ((LigneProgrammeFV) o).getIde12();
            if (ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
                errors.rejectValue("ide12", "format.error.ide12");
            }

            SareftrTyputil sareftrTyputil = new SareftrTyputil();
            sareftrTyputil.setCodeFamille(((LigneProgrammeFV)o).getCdeFamilTypUtilOri());
            sareftrTyputil.setCode(((LigneProgrammeFV)o).getCdeTypUtilOri());
            Map<String, List<String>> sareftrFamilTypUtilMap = sareftrFamilTypUtilDao.findAll();
        /*fin : commun à tous les fonds*/


        /*debut : règles de validation CAT_04 (fonds 1|2|5|7|13) */
            if(checkCategorieFondsByCdeTypUtil(CAT_04, cdeTypUtil)){
                checkTitreOeuv(errors);
                /*1 si non renseigné*/
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
            }
        /*fin : règles de validation CAT_04 (fonds 1|2|5|7|13) */


        /*debut : règles de validation CAT_01 & CAT_03 (fonds 6|9 & 3|4|10|11) */
            if(checkCategorieFondsByCdeTypUtil(CAT_01, cdeTypUtil) || checkCategorieFondsByCdeTypUtil(CAT_03, cdeTypUtil)){
                String cdeTypIde12 = ((LigneProgrammeFV)o).getCdeTypIde12();
                if(CDETYPIDE12_COPT.equals(cdeTypIde12)){
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOriCmplx", "error.titreOriCmplx");
                } else if(CDETYPIDE12_COCV.equals(cdeTypIde12)){
                    checkTitreOeuv(errors);
                }
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mt", "error.mt");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeLng", "error.cdeLng");
                String durDif = ((LigneProgrammeFV)o).getDurDif();
                if(durDif == null || durDif.isEmpty()){
                    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tax", "error.tax");
                }
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typMt", "error.typMt");
                String typMt = ((LigneProgrammeFV)o).getTypMt();
                if(typMt != null && !typMt.isEmpty() && !"MB".equals(typMt)){
                    errors.rejectValue("typMt", "format.error.typMt");
                }
                checkCdeTypUtilOri(sareftrTyputil, sareftrFamilTypUtilMap, errors);
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeGreIde12", "error.cdeGreIde12");

                /*1 si non renseigné*/
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
            }
        /*fin : règles de validation CAT_01 & CAT_03 (fonds 6|9 & 3|4|10|11) */


        /*debut : règles de validation CAT_02 (fonds 12) */
            if(checkCategorieFondsByCdeTypUtil(CAT_02, cdeTypUtil)){
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleParticipant1", "error.roleParticipant1");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomParticipant1", "error.nomParticipant1");

                checkCdeTypUtilOri(sareftrTyputil, sareftrFamilTypUtilMap, errors);
            }
        /*fin : règles de validation CAT_02 (fonds 12) */

        /*debut : règles de validation CAT_03 (fonds 3|4|10|11)*/
            if(checkCategorieFondsByCdeTypUtil(CAT_03, cdeTypUtil)){
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtilOri", "error.cdeFamilTypUtilOri");
                if (!checkCdeFamilTypUtilOri(sareftrTyputil.getCodeFamille(), sareftrFamilTypUtilMap)) {
                    errors.rejectValue("cdeFamilTypUtilOri", "format.error.cdeFamilTypUtilOri");
                }
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rionEffet", "error.rionEffet");
            }
        /*fin : règles de validation CAT_03 (fonds 3|4|10|11) */

        /*règle de validation valeur cdeGreIde12*/
        String cdeGreIde12 = ((LigneProgrammeFV) o).getCdeGreIde12();
        if(("FD03".equals(cdeTypUtil) || "FD04".equals(cdeTypUtil) || "FD01".equals(cdeTypUtil) || "FD02".equals(cdeTypUtil))
                && !checkValeurCdeGreIde12ByCdeTypUtil(cdeGreIde12, VAL1_CDEGREIDE12)){
            errors.rejectValue("cdeGreIde12", "format.error.cdeGreIde12");
        } else if(("FD10".equals(cdeTypUtil) || "FD11".equals(cdeTypUtil)) && !checkValeurCdeGreIde12ByCdeTypUtil(cdeGreIde12, VAL2_CDEGREIDE12)){
            errors.rejectValue("cdeGreIde12", "format.error.cdeGreIde12");
        } else if(("FD05".equals(cdeTypUtil) || "FD06".equals(cdeTypUtil) || "FD07".equals(cdeTypUtil))
                && !checkValeurCdeGreIde12ByCdeTypUtil(cdeGreIde12, VAL4_CDEGREIDE12)){
            errors.rejectValue("cdeGreIde12", "format.error.cdeGreIde12");
        } else if("FD09".equals(cdeTypUtil) && !checkValeurCdeGreIde12ByCdeTypUtil(cdeTypUtil, VAL3_CDEGREIDE12)){
            errors.rejectValue("cdeGreIde12", "format.error.cdeGreIde12");
        }
        validateNumericFields(errors, (LigneProgrammeFV) o);
    }

    private void checkCdeTypUtilOri(SareftrTyputil sareftrTyputil, Map<String, List<String>> sareftrFamilTypUtilMap, Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtilOri", "error.cdeTypUtilOri");
        if(sareftrTyputil != null && !checkCdeTypUtilOri(sareftrTyputil, sareftrFamilTypUtilMap)){
            errors.rejectValue("cdeTypUtilOri", "format.error.cdeTypUtilOri");
        }
    }

    public boolean checkCdeFamilTypUtilOri(String cdeFamilTypUtilOri, Map<String, List<String>> sareftrFamilTypUtilMap){
        return sareftrFamilTypUtilMap.containsKey(cdeFamilTypUtilOri);
    }

    public boolean checkCdeTypUtilOri(SareftrTyputil sareftrTyputil, Map<String, List<String>> sareftrFamilTypUtilMap){
        if (StringUtils.isNotEmpty(sareftrTyputil.getCodeFamille())) {
            return sareftrFamilTypUtilMap.get(sareftrTyputil.getCodeFamille()).contains(sareftrTyputil.getCode());
        } else {
            return sareftrFamilTypUtilMap.values().stream().anyMatch(s -> s.contains(sareftrTyputil.getCode()));
        }
    }

    private void checkTitreOeuv(Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvre", "error.titreOeuvre");
    }

    private boolean checkCategorieFondsByCdeTypUtil(CategorieFondsEnum categorieFonds, String cdeTypUtil){
        return CategorieFondsEnum.getValue(cdeTypUtil).equals(categorieFonds);
    }

    private boolean checkValeurCdeGreIde12ByCdeTypUtil(String cdeTypUtil, String[] valeursCdegreide12){
        return Arrays.asList(valeursCdegreide12).contains(cdeTypUtil);
    }
}
