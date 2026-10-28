import inno.tech.BasicJava;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class FirstProjectTest {

    @BeforeEach
    void getGo() {
        System.out.println("=============================Test method start");
    }

    @AfterEach
    void getClose() {
        System.out.println("Test method end" + '\n' + "================================");
    }

    @Test
    @Tag("one")
    void testIsEven() {
        Random random = new Random();
        int testDigit = random.nextInt(1, 100);
        System.out.println(BasicJava.isEven(testDigit));
    }


    @Tag("one")
    @ParameterizedTest
    @CsvSource({"1, false", "2, true", "7, false", "14, true"})
    void testIsEven(int value, boolean expected) {

        if(BasicJava.isEven(value) == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    @Tag("one")
    @RepeatedTest(value = 20, name = "Повтор {currentRepetition} из {totalRepetitions}")
    void testCheckAccess() {
        Random random = new Random();
        int testDigit = random.nextInt(0, 99);
        System.out.println(BasicJava.checkAccess(testDigit));
    }

    @Tag("one")
    @ParameterizedTest
    @MethodSource("randomInt")
    void testGetGrade(int value) {
        System.out.println(BasicJava.getGrade(value));
    }


    @Tag("one")
    @Test
    void testIsPositive() {
        Random random = new Random();
        int testDigit = random.nextInt(-1, 5);
        if(BasicJava.isPositive(testDigit)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("one")
    @Test
    void testBlastOff() {
        int testDigit = 5;
        String expected = "5, 4, 3, 2, 1. Поехали!";
        String actual = BasicJava.blastOff(testDigit);
        if(actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("one")
    @Test
    void testSumToN() {
        int test = 5;
        int expected = 15;
        int actual = BasicJava.sumToN(test);
        if(actual == expected){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("one")
    @Test
    void testHasBug() {
        String[] one = {"feature", "Task", "bug"};
        String[] two = {"feature", "Task", "Bug"};
        String[] three = {"feature", "Task", "noBug"};

        boolean actualOne = BasicJava.hasBug(one);
        boolean actualTwo = BasicJava.hasBug(two);
        boolean actualThree = BasicJava.hasBug(three);

        boolean[] result = {actualOne, actualTwo, actualThree};

        for (boolean b : result) {
            if (b) {
                System.out.println("TEST PASSED");
            } else {
                System.out.println("TEST FAILED");
            }
        }
    }

    @Tag("one")
    @ParameterizedTest
    @MethodSource("forHasBug")
    void testHasBugTest(String[] value) {
        if(BasicJava.hasBug(value)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("one")
    @Test
    void testGetEvenInRange() {
        int startTest = 4;
        int endTest = 9;

        String expected = "4 6 8";
        String actual = BasicJava.getEvenInRange(startTest, endTest);

        Assertions.assertEquals(expected, actual);
    }


    @Tag("one")
    @Test
    void testFindMax() {
        int[] arr = {23, 34, 4, 60, 159};
        int expected = 159;
        if(BasicJava.findMax(arr) == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("one")
    @Test
    void testReverse() {
        String[] arrTest = {"Работа", "Дела", "Отдых", "Деньги", "Путешествия"};
        String[] expected = {"Путешествия", "Деньги", "Отдых", "Дела", "Работа"};

        if(Arrays.equals(BasicJava.revers(arrTest), expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("one")
    @Test
    void testCalcAverage() {
        List<Integer> listTest = List.of(1, 4, 7, 43, 59);
        double expected = 22.8;
        if(BasicJava.calcAverage(listTest) == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }


    @Tag("one")
    @Test
    void testRemoveSpecificName() {
        List<String> listTest = List.of("Chill", "Journey", "Party", "Work");
        String removeNameTest = "Work";
        List<String> expected = List.of("Chill", "Journey", "Party");

        if(BasicJava.removeSpecificName(listTest, removeNameTest).equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }

    static List<Integer> randomInt() {
        Random random = new Random();
        int value = random.nextInt(0, 99);
        return List.of(value);
    }

    static Stream<String[]> forHasBug() {
        String[] one = {"feature", "Task", "bug"};
        String[] two = {"feature", "Task", "Bug"};
        String[] three = {"feature", "Task", "noBug"};
        return (Stream<String[]>) List.of(one, two, three);
    }
}
