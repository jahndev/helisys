package io.sisin.sisin.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.sisin.sisin.service.ProductoService;
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
 * Validate that the proNumeroParteAlterno value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ProductoProNumeroParteAlternoUnique.ProductoProNumeroParteAlternoUniqueValidator.class
)
public @interface ProductoProNumeroParteAlternoUnique {

    String message() default "{Exists.producto.proNumeroParteAlterno}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ProductoProNumeroParteAlternoUniqueValidator implements ConstraintValidator<ProductoProNumeroParteAlternoUnique, String> {

        private final ProductoService productoService;
        private final HttpServletRequest request;

        public ProductoProNumeroParteAlternoUniqueValidator(final ProductoService productoService,
                final HttpServletRequest request) {
            this.productoService = productoService;
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
            final String currentId = pathVariables.get("proId");
            if (currentId != null && value.equalsIgnoreCase(productoService.get(Integer.parseInt(currentId)).getProNumeroParteAlterno())) {
                // value hasn't changed
                return true;
            }
            return !productoService.proNumeroParteAlternoExists(value);
        }

    }

}
