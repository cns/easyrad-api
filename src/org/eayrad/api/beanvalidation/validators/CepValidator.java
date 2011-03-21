package org.eayrad.api.beanvalidation.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.eayrad.api.beanvalidation.annotations.Cep;

public class CepValidator implements ConstraintValidator<Cep, String> {

	private Pattern pattern = Pattern.compile("[0-9]{5}-[0-9]{3}");
	private Pattern patternOnlyNumbers = Pattern.compile("[0-9]{8}");
	private Cep constraintAnnotation;

	public void initialize(Cep constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		Matcher m = null;
		if (constraintAnnotation.onlynumbers())
			m = patternOnlyNumbers.matcher(value);
		else
			m = pattern.matcher(value);
		return m.matches();
	}

}
