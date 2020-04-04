package chap07.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRegisterTest {
    private UserRegister userRegister;

    // 실제 동작하는 구현이 아니라 약한 암호인지 여부를 알려주기만 하는 스텁 대역이다.
    private StubWeakPasswordChecker stubPasswordChecker = new StubWeakPasswordChecker();

    // 실제와 동일하게 동작하는 페이크 대역이다.
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();

    // 메일 발송 여부를 확인하기 위해 발송 이메일을 기록하는 스파이 대역이다.
    private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(stubPasswordChecker, fakeRepository, spyEmailNotifier);
    }

    @Test
    @DisplayName("약한 암호면 가입 실패")
    void weakPassword() {
        // 암호가 약하다고 응답하도록 설정
        stubPasswordChecker.setWeak(true);

        Assertions.assertThrows(WeakPasswordException.class, () -> userRegister.register("id", "pw", "email"));
    }

    @Test
    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    void dupIdExists() {
        fakeRepository.save(new User("id1", "pw1", "email1"));

        Assertions.assertThrows(DuplIdException.class, () -> userRegister.register("id1", "pw2", "email1"));
    }

    @Test
    @DisplayName("같은 ID가 존재하지 않으면 가입 성공")
    void noDupId_Then_RegisterSuccess() {
        userRegister.register("id", "pw", "email");

        User user = fakeRepository.findById("id");
        Assertions.assertEquals("id", user.getId());
        Assertions.assertEquals("email", user.getEmail());
    }

    @Test
    @DisplayName("가입되면 메일을 전송")
    void register_Then_SendMail() {
        userRegister.register("id", "pw", "email@email.com");

        Assertions.assertTrue(spyEmailNotifier.isCalled());
        Assertions.assertEquals("email@email.com", spyEmailNotifier.getEmail());
    }
}
