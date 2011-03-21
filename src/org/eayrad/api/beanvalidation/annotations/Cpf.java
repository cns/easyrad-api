package org.eayrad.api.beanvalidation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import org.eayrad.api.beanvalidation.validators.CpfValidator;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=CpfValidator.class)
public @interface Cpf {
	String message() default "Cpf Inválido";
	boolean onlynumbers() default false;
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
}
