package chap09.user;

import org.springframework.stereotype.Component;

@Component
public class VirtualEmailNotifier implements EmailNotifier {

    @Override
    public void sendRegisterEmail(String email) {
        System.out.println("이메일 발송: " + email);
    }
}
