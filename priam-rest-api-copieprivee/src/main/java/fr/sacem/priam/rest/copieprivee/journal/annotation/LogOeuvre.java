package fr.sacem.priam.rest.copieprivee.journal.annotation;

import fr.sacem.priam.common.TypeLog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by benmerzoukah on 09/03/2018.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogOeuvre {

    TypeLog event();
}
