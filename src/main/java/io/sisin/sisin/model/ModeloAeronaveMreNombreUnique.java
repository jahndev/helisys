package io.sisin.sisin.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.sisin.sisin.service.ModeloAeronaveService;
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
 * Validate that the mreNombre value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ModeloAeronaveMreNombreUnique.ModeloAeronaveMreNombreUniqueValidator.class
)
public @interface ModeloAeronaveMreNombreUnique {

    String message() default "{Exists.modeloAeronave.mreNombre}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ModeloAeronaveMreNombreUniqueValidator implements ConstraintValidator<ModeloAeronaveMreNombreUnique, String> {

        private final ModeloAeronaveService modeloAeronaveService;
        private final HttpServletRequest request;

        public ModeloAeronaveMreNombreUniqueValidator(
                final ModeloAeronaveService modeloAeronaveService,
                final HttpServletRequest request) {
            this.modeloAeronaveService = modeloAeronaveService;
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
            final String currentId = pathVariables.get("mre");
            if (currentId != null && value.equalsIgnoreCase(modeloAeronaveService.get(Integer.parseInt(currentId)).getMreNombre())) {
                // value hasn't changed
                return true;
            }
            return !modeloAeronaveService.mreNombreExists(value);
        }

    }

}
