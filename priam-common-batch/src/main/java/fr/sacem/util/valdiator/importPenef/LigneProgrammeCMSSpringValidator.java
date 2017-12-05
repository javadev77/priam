package fr.sacem.util.valdiator.importPenef;

import fr.sacem.domain.LigneProgramme;
import fr.sacem.priam.common.TypeUtilisationEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by belwidanej on 28/08/2017.
 */
@Component
public class LigneProgrammeCMSSpringValidator implements Validator {


	public static final int IDE12_LENGTH_MAX = 12;
	public static final int IDE12_LENGTH_MIN = 6;

	@Override
    public boolean supports(Class<?> clazz) {
	  return LigneProgramme.class.equals(clazz);
    }


    @Override
    public void validate(Object o, Errors errors) {

		String cdeTypUtil = ((LigneProgramme) o).getCdeTypUtil();
		String ide12 = ((LigneProgramme) o).getIde12();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvre", "error.titreOeuvre");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtilOri", "error.cdeTypUtilOri");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtilOri", "error.cdeFamilTypUtilOri");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeUtil", "error.cdeUtil");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");

		if(!cdeTypUtil.equals("") && TypeUtilisationEnum.CMS_ANT.getCode().equals(cdeTypUtil)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
		}
		if(!cdeTypUtil.equals("") && TypeUtilisationEnum.CMS_FRA.getCode().equals(cdeTypUtil)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mt", "error.mt");
		}

		if (ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
				errors.rejectValue("ide12", "format.error.ide12");
		}

		validateNumericFields(errors, (LigneProgramme) o);
    }

	protected void validateNumericFields(Errors errors, LigneProgramme o) {

		Double ide12 = validateNumericField(errors, o.getIde12(), "ide12");
		if(ide12 != null)
			o.setIde12(Long.toString(ide12.longValue()));
		else
			o.setIde12(null);

		Double durDif = validateNumericField(errors, o.getDurDif(), "durDif");
		if(durDif != null)
			o.setDurDif(Long.toString(durDif.longValue()));
		else
			o.setDurDif(null);

		Double nbrDif = validateNumericField(errors, o.getNbrDif(), "nbrDif");
		if(nbrDif != null)
			o.setNbrDif(Long.toString(nbrDif.longValue()));
		else
			o.setNbrDif(null);

		Double durDifCtna = validateNumericField(errors, o.getDurDifCtna(), "durDifCtna");
		if(durDifCtna != null)
			o.setDurDifCtna(Long.toString(durDifCtna.longValue()));
		else
			o.setDurDifCtna(null);

		Double tax = validateNumericField(errors, o.getTax(), "tax");
		if(tax != null)
			o.setTax(tax.toString());
		else
			o.setTax(null);

		if(o.getMt() != null)
			o.setMt(o.getMt());
		else
			o.setMt(null);
	}


	protected Double validateNumericField(Errors errors, String text, String field) {
		if (StringUtils.hasText(text)) {
			NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
			try {
				return nf.parse(text).doubleValue();
			} catch (ParseException e) {
				errors.rejectValue(field, "format.error."+field);
			}
		}

		return null;
	}
}
