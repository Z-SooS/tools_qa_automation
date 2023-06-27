package zsoos.pets.tools_qa_automation.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyReaderTest {

    final String CONFIG_FILE_ENVIRONMENT_VARIABLE_NAME = "config_file";
    final String CONFIG_FILE_NAME = "property-reader.properties";
    final String CONFIG_FILE_IN_SUBDIR_NAME = "property-reader-sub/property-reader-sub.properties";

    @BeforeEach
    void setUp() {
        System.setProperty(CONFIG_FILE_ENVIRONMENT_VARIABLE_NAME, CONFIG_FILE_NAME);
    }

    @Test
    void getMustHaveProperty_fromFileWithoutQuotes_foundAsInFile() {
//        Should be same as in property-reader.properties
        final String expected = "value without quotes";
        final String propertyName = "value_without_quotes";

        final String actual = PropertyReader.getMustHaveProperty(propertyName);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getMustHaveProperty_fromFileWithQuotes_foundAsInFile() {
//        Should be same as in property-reader.properties
        final String expected = "\"value with quotes\"";
        final String propertyName = "value_with_quotes";

        final String actual = PropertyReader.getMustHaveProperty(propertyName);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getMustHaveProperty_fromSystemNotInFile_sameAsSetUp() {
        final String expected = "value only in system";
        final String propertyName = "value_only_in_system";
        System.setProperty(propertyName, expected);

        final String actual = PropertyReader.getMustHaveProperty(propertyName);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getMustHaveProperty_fromSystemAlsoInFile_prefersSystemValue() {
        final String expected = "different value";
        final String propertyName = "value_in_system";
        System.setProperty(propertyName, expected);

        final String actual = PropertyReader.getMustHaveProperty(propertyName);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getMustHaveProperty_propertyNotSet_throwsRuntimeException() {
        //        Important! Property is not set
        final String propertyName = "not_set_property";

        Assertions.assertThrows(RuntimeException.class, () -> PropertyReader.getMustHaveProperty(propertyName), "getMustHaveProperty must throw an exception if property is not set");
    }

    @Test
    void getPropertyOrDefault_fromFileWithoutQuotes_foundAsInFile() {
        //        Should be same as in property-reader.properties
        final String expected = "value without quotes";
        final String propertyName = "value_without_quotes";

        final String actual = PropertyReader.getPropertyOrDefault(propertyName, null);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getPropertyOrDefault_fromFileWithQuotes_foundAsInFile() {
        //        Should be same as in property-reader.properties
        final String expected = "\"value with quotes\"";
        final String propertyName = "value_with_quotes";

        final String actual = PropertyReader.getPropertyOrDefault(propertyName, null);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getPropertyOrDefault_fromSystemNotInFile_sameAsSetUp() {
        final String expected = "value only in system";
        final String propertyName = "value_only_in_system";

        System.setProperty(propertyName, expected);

        final String actual = PropertyReader.getPropertyOrDefault(propertyName, null);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getPropertyOrDefault_fromSystemAlsoInFile_prefersSystemValue() {
        final String expected = "different value";
        final String propertyName = "value_set_in_system";

        System.setProperty(propertyName, expected);

        final String actual = PropertyReader.getPropertyOrDefault(propertyName, null);

        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getMustHaveProperty_propertyNotSet_returnsDefault() {
        //        Important! Property is not set
        final String propertyName = "not_set_property";
        final String expected = "default value";

        String actual = PropertyReader.getPropertyOrDefault(propertyName, expected);
        Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
    }

    @Test
    void getMustHaveProperty_propertyNotSet_returnsNull() {
        //        Important! Property is not set
        final String propertyName = "not_set_property";

        String actual = PropertyReader.getPropertyOrDefault(propertyName, null);
        Assertions.assertNull(actual);
    }

    @Test
    void useFileFromSubDirectory() {
        // Important! File is set up
        // key=value
        PropertyReader.nullProperties();
        System.setProperty(CONFIG_FILE_ENVIRONMENT_VARIABLE_NAME, CONFIG_FILE_IN_SUBDIR_NAME);

        final String expected = "value";
        final String propertyName = "key";

        Assertions.assertDoesNotThrow(() -> {
            final String actual = PropertyReader.getMustHaveProperty(propertyName);

            Assertions.assertEquals(expected, actual, String.format("Expected:\n'%s' \nActual:\n'%s'\n", expected, actual));
        }, "File: /" + CONFIG_FILE_IN_SUBDIR_NAME + " should be present in resources directory");
    }
}