package chap07.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

/**
 * Mickito를 이용한 모의 객체 생성과 사용
 * UserRegister를 테스트한다. UserRegister가 사용하는 WeakPasswordCheck, UserRepository, EmailNotifier는 대역 객체로 대체.
 */
public class UserRegisterMockTest {

    private UserRegister userRegister;
    private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
    private MemoryUserRepository fakeReposiotry = new MemoryUserRepository();
    private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockPasswordChecker, fakeReposiotry, mockEmailNotifier);
    }

    @Test
    @DisplayName("약한 암호면 가입 실패")
    void weakPassword() {
        BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);

        Assertions.assertThrows(WeakPasswordException.class, () -> userRegister.register("id", "pw", "email"));
    }

    @Test
    @DisplayName("회원가입시 암호 검사 수행함")
    void checkPassword() {
        userRegister.register("id", "pw", "email");

        // 대역 객체가 기대하는대로 상호작용했는지 확인한다.
        BDDMockito.then(mockPasswordChecker).should().checkPasswordWeak(BDDMockito.anyString());
    }

    @Test
    @DisplayName("가입하면 메일을 전송함")
    void register_Then_SendMail() {
        userRegister.register("id", "pw", "email");

        // 모의 객체 스파이로의 사용.
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockEmailNotifier).should().sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        Assertions.assertEquals("email", realEmail);
    }
}
