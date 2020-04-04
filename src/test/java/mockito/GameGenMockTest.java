package mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.List;

public class GameGenMockTest {

    @Test
    void mockTest() {
        // 모의 객체 생성
        GameNumGen genMock = Mockito.mock(GameNumGen.class);

        // 모의 객체에 스텁 구성
        BDDMockito.given(genMock.generate(GameLevel.EASY)).willReturn("123");

        // 스텁 설정에 매칭되는 메서드 실행
        String num = genMock.generate(GameLevel.EASY);
        Assertions.assertEquals("123", num);

        // Mockito는 일치하는 스텁 설정이 없을 경우 리턴 타입의 기본 값을 리턴한다.
        String num2 = genMock.generate(GameLevel.NORMAL);
        Assertions.assertEquals(null, num2);
    }

    @Test
    void mockThrowTest() {
        GameNumGen genMock = Mockito.mock(GameNumGen.class);
        BDDMockito.given(genMock.generate(null)).willThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> genMock.generate(null));
    }

    @Test
    void voidMethodWillThrowTest() {
        List<String> mockList = Mockito.mock(List.class);

        // 리턴타입이 void인 메서드에 대해 익셉션을 발생하려면 willThrow() 메서드로 시작한다.
        BDDMockito.willThrow(UnsupportedOperationException.class)
                .given(mockList)
                .clear();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> mockList.clear());
    }

    @Test
    void anyMatchTest() {
        GameNumGen genMock = Mockito.mock(GameNumGen.class);

        // 임의의 인자값에 일치하도록 설정. ArgumentMatchers.any()
        BDDMockito.given(genMock.generate(BDDMockito.any())).willReturn("123");

        String num = genMock.generate(GameLevel.EASY);
        Assertions.assertEquals("123", num);

        String num2 = genMock.generate(GameLevel.NORMAL);
        Assertions.assertEquals("123", num2);
    }

    @Test
    void mixAnyAndEq() {
        List<String> mockList = Mockito.mock(List.class);

        // 임의의 값과 일치하는 인자와 정확하게 일치하는 인자를 함께 사용하고 싶으면 `ArgumentMatchers.eq()`을 사용한다.
        BDDMockito.given(mockList.set(ArgumentMatchers.anyInt(), ArgumentMatchers.eq("123"))).willReturn("456");

        String old = mockList.set(5, "123");
        Assertions.assertEquals("456", old);
    }

    @Test
    void init() {
        GameNumGen genMock = Mockito.mock(GameNumGen.class);
        Game game = new Game(genMock);
        game.init(GameLevel.EASY);

        // 실제로 모의 객체가 불렸는지 검증한다.
        BDDMockito.then(genMock).should(Mockito.only()).generate(GameLevel.EASY);
    }

    @Test
    void argumentTest() {
        List<String> mockList = Mockito.mock(List.class);

        mockList.add("123");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockList).should().add(captor.capture());

        String value = captor.getValue();
        Assertions.assertEquals("123", value);
    }
}
