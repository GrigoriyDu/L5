package org.example;

/**
 * Пример использования класса {@link Injector} для внедрения зависимостей.
 */
public class Main {
    public static void main(String[] args) {
        Injector injector = new Injector();
        Seed Seed = injector.inject(new Seed(), "config.properties");
        Seed.foo();
    }
}
