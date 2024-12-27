package org.example;

/**
 * Класс, использующий инъекции зависимостей.
 */
public class Seed {
    /**
     * Поле, которое будет инъецировано реализацией {@link SomeInterface}.
     */
    @AutoInjectable
    private SomeInterface field1;

    /**
     * Поле, которое будет инъецировано реализацией {@link OtherInterface}.
     */
    @AutoInjectable
    private OtherInterface field2;

    /**
     * Метод для выполнения действий с инъецированными зависимостями.
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}
