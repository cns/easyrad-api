package org.eayrad.api.beanvalidation.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.eayrad.api.beanvalidation.annotations.Cpf;

public class CpfValidator implements ConstraintValidator<Cpf, String>  {

	private Pattern pattern = Pattern.compile("[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}");
	private Pattern patternOnlyNumbers = Pattern.compile("[0-9]{8}");
	private Cpf constraintAnnotation;

	public void initialize(Cpf constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	/**
	 * TODO implementar verificacao de digito
	 * @param value
	 * @param context
	 * @return
	 */
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Matcher m = null;
		if (constraintAnnotation.onlynumbers())
			m = patternOnlyNumbers.matcher(value);
		else
			m = pattern.matcher(value);
		return m.matches();
	}

}
