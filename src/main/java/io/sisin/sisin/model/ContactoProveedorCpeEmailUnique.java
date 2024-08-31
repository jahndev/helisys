package io.sisin.sisin.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.sisin.sisin.service.ContactoProveedorService;
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
 * Validate that the cpeEmail value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ContactoProveedorCpeEmailUnique.ContactoProveedorCpeEmailUniqueValidator.class
)
public @interface ContactoProveedorCpeEmailUnique {

    String message() default "{Exists.contactoProveedor.cpeEmail}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ContactoProveedorCpeEmailUniqueValidator implements ConstraintValidator<ContactoProveedorCpeEmailUnique, String> {

        private final ContactoProveedorService contactoProveedorService;
        private final HttpServletRequest request;

        public ContactoProveedorCpeEmailUniqueValidator(
                final ContactoProveedorService contactoProveedorService,
                final HttpServletRequest request) {
            this.contactoProveedorService = contactoProveedorService;
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
            final String currentId = pathVariables.get("cveId");
            if (currentId != null && value.equalsIgnoreCase(contactoProveedorService.get(Integer.parseInt(currentId)).getCpeEmail())) {
                // value hasn't changed
                return true;
            }
            return !contactoProveedorService.cpeEmailExists(value);
        }

    }

}
