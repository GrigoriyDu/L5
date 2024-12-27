package org.example;

import java.lang.annotation.*;

/**
 * Аннотация для пометки полей, которые должны быть инъецированы.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoInjectable {
}