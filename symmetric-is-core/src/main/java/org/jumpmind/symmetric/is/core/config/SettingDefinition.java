package org.jumpmind.symmetric.is.core.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingDefinition {

    public enum Type {
        STRING, INTEGER, BOOLEAN, CHOICE, PASSWORD, XML, DB_CONNECTION
    };

    int order() default 0;

    Type type();

    boolean required() default false;

    String[] choices() default {};

    String defaultValue() default "";

    String label() default "";

    boolean visible() default true;

    /**
     * When set, this setting must be provided by the user/caller of the object
     * that defined the setting. For example, a file connection needs to be
     * provided the name of the file or an SMTP connection needs to be provided
     * the subject and to list for an email.
     */
    boolean provided() default false;

}