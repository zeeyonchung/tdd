package sample2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.EnumSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

/**
 * 하나의 테스트 메서드가 다른 파라미터들을 받아 여러번 실행한다.
 */
public class ParameterTest {

    /**
     * ValueSource
     * - short, byte, int, long, float, double, char, java.lang.String, java.lang.Class
     * - null은 안 된다.
     * - 메서드에 한 개의 파라미터만 전달할 수 있다.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -1, 13, Integer.MAX_VALUE})
    void isOdd_홀수라면_true(int number) {
        assertThat(Numbers.isOdd(number)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void isBlank_null또는공백이라면_true(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    /**
     * NullSource
     * EmptySource
     */
    @ParameterizedTest
    @NullSource
    void isBlank_null이라면_true(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    @ParameterizedTest
    @EmptySource
    void isBlank_공백이라면_true(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void isBlank_null또는공백이라면_true_2(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void isBlank_null또는공백이라면_true_3(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    /**
     * EnumSource
     * - 특정 값을 지정하지 않으면 enum 전체를 전달한다.
     * - regex를 지정할 수 있다.
     */
    @ParameterizedTest
    @EnumSource(Month.class) // 12개 Month를 파라미터로 전달
    void getValue_1과12사이(Month month) {
        int monthNumber = month.getValue();
        assertThat(monthNumber >= 1 && monthNumber <= 12).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"}) // 4개 Month만 파라미터로 전달
    void length_4월6월9월11월이라면_30일(Month month) {
        final boolean isALeapYear = false;
        assertThat(month.length(isALeapYear)).isEqualTo(30);
    }

    @ParameterizedTest
    @EnumSource(
            value = Month.class,
            names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"},
            mode = EnumSource.Mode.EXCLUDE) // 5개를 제외한 Month만 파라미터로 전달
    void length_4월6월9월11월2월이외라면_31일(Month month) {
        final boolean isALeapYear = false;
        assertThat(month.length(isALeapYear)).isEqualTo(31);
    }

    @ParameterizedTest
    @EnumSource(
            value = Month.class,
            names = ".+BER",
            mode = EnumSource.Mode.MATCH_ANY
    )
    void contains_BER로끝나는달은4개(Month month) {
        EnumSet<Month> months = EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        assertThat(months.contains(month));
    }

    /**
     * CsvSource
     * CsvFileSource
     * - 각각의 파라미터에 대해 기대 결과값이 다를 때 사용한다.
     * - 입력값과 기대값의 구분자, 파일 인코딩 등을 속성으로 지정할 수 있다.
     */
    @ParameterizedTest
    @CsvSource({"test,TEST", "EeEe,EEEE", "Java,JAVA"})
    void toUpperCase(String input, String expected) {
        String actualValue = input.toUpperCase();
        assertThat(actualValue).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/sample2/parameter_test.csv", numLinesToSkip = 1)
    void toUpperCase2(String input, String expected) {
        String actualValue = input.toUpperCase();
        assertThat(actualValue).isEqualTo(expected);
    }

    /**
     * MethodSource
     * - 객체 등 복잡한 파라미터를 전달할 때 사용한다.
     * - 파라미터 제공 메서드는 아무 컬렉션을 리턴할 수 있다.
     * - 메서드명이 같을 경우 명시를 생략할 수 있다.
     * - 다른 클래스에 있는 메서드를 명시할 수 있다.
     *      e.g. com.examlple.AnotherClass#methodName
     */
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void isBlank_null또는공백이라면_true_4(String input, boolean expected) {
        assertThat(Strings.isBlank(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of(" ", true),
                Arguments.of("not blank", false)
        );
    }

    @ParameterizedTest
    @MethodSource // 메서드명이 같을 경우 명시를 생략할 수 있다.
    void isBlank_null또는공백이라면_true_5(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    private static Stream<String> isBlank_null또는공백이라면_true_5() {
        return Stream.of(null, "", " ");
    }

    /**
     * ArgumentSource
     * - ArgumentsProvider를 구현한다.
     */
    @ParameterizedTest
    @ArgumentsSource(BlankStringsArgumentsProvider.class)
    void isBlank_null또는공백이라면_true_6(String input) {
        assertThat(Strings.isBlank(input)).isTrue();
    }

    /**
     * Custom annotation
     */
    static Stream<Arguments> arguments = Stream.of(
            Arguments.of(null, true),
            Arguments.of("", true),
            Arguments.of(" ", true),
            Arguments.of("not blank", false)
    );

    @ParameterizedTest
    @VariableSource("arguments")
    void isBlank_null또는공백이라면_true_7(String input, boolean expected) {
        assertThat(Strings.isBlank(input)).isEqualTo(expected);
    }

    /**
     * 암시적 변환
     * - String 값을 다음 enum 타입으로 암시적 변환한다.
     * - UUID
     * - Locale, LocalTime, LocalDateTime, Year, Month, etc.
     * - File, Path
     * - URL, URI
     * - Enum subclasses
     */
    @ParameterizedTest
    @CsvSource({"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    void length_4월6월9월11월이라면_30일_2(Month month) {
        final boolean isALeapYear = false;
        assertThat(month.length(isALeapYear)).isEqualTo(30);
    }

    /**
     * 명시적 변환
     * - ArgumentConverter를 구현한다.
     */
    @ParameterizedTest
    @CsvSource({"2018/12/25,2018", "2019/02/01,2019"})
    void getYear(@ConvertWith(SlashyDateConverter.class) LocalDate date, int expected) {
         assertThat(date.getYear()).isEqualTo(expected);
    }

    /**
     * Argument Accessor
     * - 모든 파라미터들을 ArgumentsAccessor의 인스턴스로 캡슐화하고 인덱스와 타입에 따라 검색한다.
     */
    @ParameterizedTest
    @CsvSource({"firstName, middleName, lastName, firstName middleName lastName"})
    void fullName(ArgumentsAccessor argumentsAccessor) {
        String firstName = argumentsAccessor.getString(0);
        String middleName = (String) argumentsAccessor.get(1);
        String lastName = argumentsAccessor.get(2, String.class);
        String expectedFullName = argumentsAccessor.getString(3);

        Person person = new Person(firstName, middleName, lastName);
        assertThat(person.fullName()).isEqualTo(expectedFullName);
    }

    /**
     * Argument Aggregator
     * - ArgumentsAggregator를 구현한다.
     */
    @ParameterizedTest
    @CsvSource({"firstName middleName lastName, firstName, middleName, lastName"})
    void fullName_2(String expectedFullName, @AggregateWith(PersonAggregator.class) Person person) {
        assertThat(person.fullName()).isEqualTo(expectedFullName);
    }
}
