package io.sisin.sisin.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.sisin.sisin.service.GradoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the gdoNombre value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = GradoGdoNombreUnique.GradoGdoNombreUniqueValidator.class
)
public @interface GradoGdoNombreUnique {

    String message() default "{Exists.grado.gdoNombre}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class GradoGdoNombreUniqueValidator implements ConstraintValidator<GradoGdoNombreUnique, String> {

        private final GradoService gradoService;
        private final HttpServletRequest request;

        public GradoGdoNombreUniqueValidator(final GradoService gradoService,
                final HttpServletRequest request) {
            this.gradoService = gradoService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("gdoId");
            if (currentId != null && value.equalsIgnoreCase(gradoService.get(Integer.parseInt(currentId)).getGdoNombre())) {
                // value hasn't changed
                return true;
            }
            return !gradoService.gdoNombreExists(value);
        }

    }

}
