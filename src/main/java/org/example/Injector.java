package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Класс для внедрения зависимостей в объекты, поля которых помечены аннотацией {@link AutoInjectable}.
 */
public class Injector {

    /**
     * Загружает свойства из файла.
     *
     * @param filePath путь к файлу свойств
     * @return загруженные свойства
     */
    private Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Properties file not found: " + filePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file: " + filePath, e);
        }
        return properties;
    }

    /**
     * Создает экземпляр класса по его имени.
     *
     * @param className имя класса
     * @return экземпляр класса
     */
    private Object createInstance(String className) {
        try {
            return Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Error creating instance of class: " + className, e);
        }
    }

    /**
     * Внедряет зависимость в поле объекта.
     *
     * @param field    поле объекта
     * @param object   объект
     * @param properties свойства
     */
    private void injectField(Field field, Object object, Properties properties) {
        field.setAccessible(true);
        String interfaceName = field.getType().getName();
        String implementationClassName = properties.getProperty(interfaceName);
        if (implementationClassName != null) {
            Object implementationInstance = createInstance(implementationClassName);
            try {
                field.set(object, implementationInstance);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error injecting dependency into field: " + field.getName(), e);
            }
        }
    }

    /**
     * Внедряет зависимости в объект.
     *
     * @param object   объект, в который нужно внедрить зависимости
     * @param filePath путь к файлу свойств
     * @param <T>      тип объекта
     * @return объект с внедренными зависимостями
     */
    public <T> T inject(T object, String filePath) {
        Class<?> clazz = object.getClass();
        Properties properties = loadProperties(filePath);

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                injectField(field, object, properties);
            }
        }
        return object;
    }
}
