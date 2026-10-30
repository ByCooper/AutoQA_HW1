import inno.tech.BasicJava;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectWithAssertTest {


    @BeforeEach
    void getGo() {
        System.out.println("=============================Test method start");
    }

    @AfterEach
    void getClose() {
        System.out.println("Test method end" + '\n' + "================================");
    }

    @AfterAll
    static void gameOver() {
        System.out.println("Test run is over");
    }

    @Test
    @Tag("one")
    void testIsEven() {
        Random random = new Random();
        int testDigit = random.nextInt(1, 100);

        Assertions.assertTrue(BasicJava.isEven(testDigit));
    }


    @Tag("one")
    @ParameterizedTest
    @CsvSource({"1, false", "2, true", "7, false", "14, true"})
    void testIsEven(int value, boolean expected) {

        assertThat(BasicJava.isEven(value))
                .as("Число должно быть четным")
                .isEqualTo(expected);
    }

    @Tag("one")
    @RepeatedTest(value = 20, name = "Повтор {currentRepetition} из {totalRepetitions}")
    void testCheckAccess() {
        Random random = new Random();
        int testDigit = random.nextInt(0, 99);
        assertThat(BasicJava.checkAccess(testDigit))
                .as("Возраст должен быть старше 18")
                .isEqualTo("Allowed");
    }

    @Tag("one")
    @ParameterizedTest
    @CsvSource({"15, E" , "27, D", "54, C", "77, B", "99, A"})
    void testGetGrade(int value, String grade) {

        assertThat(BasicJava.getGrade(value))
                .as("Проверяем соответствие грейда")
                        .isEqualTo(grade);
        System.out.println(BasicJava.getGrade(value));
    }


    @Tag("one")
    @RepeatedTest(value = 16,name = "Повтор {currentRepetition} из {totalRepetitions}")
    void testIsPositive() {
        Random random = new Random();
        int testDigit = random.nextInt(-10, 5);
        System.out.println(testDigit);
        assertThat(BasicJava.isPositive(testDigit))
                .as("Проверяем, что число положительное")
                .isTrue();
    }


    @Tag("one")
    @Test
    void testBlastOff() {
        int testDigit = 5;
        String expected = "5, 4, 3, 2, 1 Поехали!";

        assertThat(BasicJava.blastOff(testDigit))
                .as("Проверяем отображение всех чисел входящих в заданный промежуток")
                .isEqualTo(expected);
    }


    @Tag("one")
    @Test
    void testSumToN() {
        int test = 5;
        int expected = 15;
        int actual = BasicJava.sumToN(test);

        Assertions.assertEquals(expected, actual);
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

        boolean[] actual = {actualOne, actualTwo, actualThree};
        boolean[] expected = {true, true, false};

        assertThat(actual)
                .as("Проверить соответствие массивов")
                .isEqualTo(expected);
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
        int startTest = 1;
        int endTest = 3;

        String expected = "2";
        String actual = BasicJava.getEvenInRange(startTest, endTest);
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }


    @Tag("one")
    @Test
    void testFindMax() {
        int[] arr = {23, 34, 4, 60, 159};
        int expected = 159;

        assertThat(BasicJava.findMax(arr))
                .as("Найти самое большое число в массиве")
                .isEqualTo(expected);
    }


    @Tag("one")
    @Test
    void testReverse() {
        String[] arrTest = {"Работа", "Дела", "Отдых", "Деньги", "Путешествия"};
        String[] expected = {"Путешествия", "Деньги", "Отдых", "Дела", "Работа"};

        assertThat(BasicJava.reverse(arrTest))
                .as("Проверяем реверс значений массива")
                .isEqualTo(expected);
    }


    @Tag("one")
    @Test
    void testCalcAverage() {
        List<Integer> listTest = List.of(1, 4, 7, 43, 59);
        double expected = 22.8;

        assertThat(BasicJava.calcAverage(listTest))
                .as("Найти среднее арифмитическое значение чисел в массиве")
                .isEqualTo(expected);
    }


    @Tag("one")
    @Test
    void testRemoveSpecificName() {
        List<String> listTest = List.of("Chill", "Journey", "Party", "Work");
        String removeNameTest = "Work";
        List<String> expected = List.of("Chill", "Journey", "Party");

        assertThat(BasicJava.removeSpecificName(listTest, removeNameTest))
                .as("Проверка исключения заданной строки из коллекции строк")
                .isEqualTo(expected);
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
