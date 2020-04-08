package sample.chap05;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

@DisplayName("기본 사용법")
public class JunitTest {

    public JunitTest() {
        System.out.println("new Test");
    }

    @BeforeAll
    static void setUpAll() {
        System.out.println("setUpAll");
    }

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @Test
    @DisplayName("주요 단언 메서드")
    void test() {
        System.out.println("test1");

        Assertions.assertEquals(1, 1);
        Assertions.assertNotEquals(1, 2);
        Assertions.assertSame("a", "a");
        Assertions.assertNotSame(LocalDate.now(), LocalDate.now());
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.assertNull(null);
        Assertions.assertNotNull("");
        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> {
            throw new NullPointerException("wow");
        });
        Assertions.assertEquals("wow", thrown.getMessage());
        Assertions.assertDoesNotThrow(() -> {});

        // 모든 검증을 실행하고 그 중 실패한 내용 확인
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, 2),
                () -> Assertions.assertEquals(1, 1),
                () -> Assertions.assertEquals(1, 3)
        );
    }

    @Test
    void test2() {
        System.out.println("test2");
    }

    @Disabled
    @Test
    void test3() {
        System.out.println("test3");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("tearDownAll");
    }
}

/*
setUpAll
new Test
setUp
test1
tearDown
chap05.JunitTest > test() FAILED
org.opentest4j.MultipleFailuresError at JunitTest.java:40
new Test
setUp
test2
tearDown
new Test
void chap05.JunitTest.test3() is @Disabled
tearDownAll
*/

/**
 * 테스트 라이프사이클
 *
 * @BeforeAll
 * 테스트객체생성 @BeforeEach @Test @AfterEach
 * 테스트객체생성 @BeforeEach @Test @AfterEach
 * 테스트객체생성 @BeforeEach @Test @AfterEach
 * @AfterAll
 **/