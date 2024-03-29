package util.functional.validate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface DefaultCondition {
    Condition[] conditions();
}
