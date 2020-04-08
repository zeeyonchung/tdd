package sample.chap07.card.stub;

import chap07.card.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.chap07.card.AutoDebitRegister;
import sample.chap07.card.AutoDebitReq;
import sample.chap07.card.CardValidity;
import sample.chap07.card.RegisterResult;

/**
 * 대역을 이용한 테스트
 */
public class AutoDebitRegisterStubTest {

    private AutoDebitRegister register;
    private StubCardNumberValidator stubValidator;
    private StubAutoDebitInfoRepository repository;

    @BeforeEach
    void setUp() {
        stubValidator = new StubCardNumberValidator();
        repository = new StubAutoDebitInfoRepository();
        register = new AutoDebitRegister(stubValidator, repository);
    }

    @Test
    void invalidCard() {
        stubValidator.setInvalidNo("111222333");

        AutoDebitReq req = new AutoDebitReq("user1", "111222333");
        RegisterResult result = this.register.register(req);

        Assertions.assertEquals(CardValidity.INVALID, result.getValidity());
    }

    @Test
    void theftCard() {
        stubValidator.setTheftNo("444555666");

        AutoDebitReq req = new AutoDebitReq("user1", "444555666");
        RegisterResult result = this.register.register(req);

        Assertions.assertEquals(CardValidity.THEFT, result.getValidity());
    }
}
