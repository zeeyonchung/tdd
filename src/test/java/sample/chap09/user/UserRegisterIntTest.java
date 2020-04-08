package sample.chap09.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
class UserRegisterIntTest {

    @Autowired
    private UserRegister userRegister;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void 동일Id가_이미존재하면_익셉션() {
        userRepository.save(new User("id1", "pw", "email@email.com"));
        assertThrows(DuplIdException.class, () -> userRegister.register("id1", "pw123", "email@email.com"));
    }

    @Test
    @Transactional
    void 동일Id가_존재하지않으면_저장() {
        userRegister.register("id", "pwert", "email@email.com");

        User user = userRepository.findById("id");
        assertEquals("email@email.com", user.getEmail());
    }
}