package sample.chap07.card.fake;

import sample.chap07.card.AutoDebitInfo;
import sample.chap07.card.AutoDebitRegister;
import sample.chap07.card.AutoDebitReq;
import sample.chap07.card.RegisterResult;
import sample.chap07.card.stub.StubCardNumberValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AutoDebitRegisterFakeTest {

    private AutoDebitRegister register;
    private StubCardNumberValidator validator;
    private MemoryAutoDebitInfoRepository fakeRepository;

    @BeforeEach
    void setUp() {
        validator = new StubCardNumberValidator();
        fakeRepository = new MemoryAutoDebitInfoRepository();
        register = new AutoDebitRegister(validator, fakeRepository);
    }

    @Test
    void alreadyRegistered_Then_InfoUpdated() {
        fakeRepository.save(
                new AutoDebitInfo("user1", "12345", LocalDateTime.now())
        );

        AutoDebitReq req = new AutoDebitReq("user1", "67890");
        RegisterResult result = this.register.register(req);

        AutoDebitInfo saved = fakeRepository.findOne("user1");
        Assertions.assertEquals("67890", saved.getCardNumber());
    }

    @Test
    void notYetRegistered_Then_newInfoRegistered() {
        AutoDebitReq req = new AutoDebitReq("user1", "12345");
        RegisterResult result = this.register.register(req);

        AutoDebitInfo saved = fakeRepository.findOne("user1");
        Assertions.assertEquals("12345", saved.getCardNumber());
    }
}
