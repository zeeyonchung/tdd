package chap07.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 대역을 이용하지 않은 테스트
 * 호출하는 외부 시스템(DB, 외부서버 등)과, 그 연동 부분이 모두 정상적으로 동작하고 있어야 한다.
 */
public class AutoDebitRegisterTest {

    private AutoDebitRegister register;

    @BeforeEach
    void setUp() {
        CardNumberValidator validator = new CardNumberValidator();
        AutoDebitInfoRepository repository = new JpaAutoDebitInfoRepository();
        register = new AutoDebitRegister(validator, repository);
    }

    @Test
    void validCard() {
        //업체에서 받은 테스트용 유효한 카드번호 사용
        AutoDebitReq req = new AutoDebitReq("user1", "12341234");
        RegisterResult result = this.register.register(req);
        Assertions.assertEquals(CardValidity.VALID, result.getValidity());
    }

    @Test
    void theftCard() {
        //업체에서 받은 도난 테스트용 카드번호 사용
        AutoDebitReq req = new AutoDebitReq("user1", "56785678");
        RegisterResult result = this.register.register(req);
        Assertions.assertEquals(CardValidity.THEFT, result.getValidity());
    }
}
