package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest {

    private Injector injector;
    private File tempPropertiesFile;

    @BeforeEach
    void setUp() throws IOException {
        injector = new Injector();
        tempPropertiesFile = File.createTempFile("config", ".properties");
        try (FileWriter writer = new FileWriter(tempPropertiesFile)) {
            writer.write("org.example.SomeInterface=org.example.SomeImpl\n");
            writer.write("org.example.OtherInterface=org.example.OtherDo\n");
        }
    }

    @Test
    void inject() throws IOException {
        // Создаем объект Seed
        Seed seed = new Seed();

        // Внедряем зависимости
        seed = injector.inject(seed, tempPropertiesFile.getAbsolutePath());

        // Проверяем, что зависимости были внедрены
        assertNotNull(seed.getField1());
        assertNotNull(seed.getField2());

        // Проверяем, что методы были вызваны
        seed.foo();

        // Проверяем, что внедренные зависимости являются экземплярами правильных классов
        assertTrue(seed.getField1() instanceof SomeImpl);
        assertTrue(seed.getField2() instanceof OtherDo);
    }
}
