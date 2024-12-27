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
     * Возвращает значение поля field1.
     *
     * @return значение поля field1
     */
    public SomeInterface getField1() {
        return field1;
    }

    /**
     * Возвращает значение поля field2.
     *
     * @return значение поля field2
     */
    public OtherInterface getField2() {
        return field2;
    }

    /**
     * Метод для выполнения действий с инъецированными зависимостями.
     */
    public void foo() {
        field1.doSomething();
        field2.doSomeOther();
    }
}
