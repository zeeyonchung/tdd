package chap08.hardtotest.auth;

/**
 * 테스트하기 어려운 코드
 * - 정적 메서드 사용
 *
 * 키를 확인하여 로그인한다.
 */
public class LoginService {
    private String authKey = "someKey";
    private CustomerRepository customerRepo;

    public LoginService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public LoginResult login(String id, String pw) {
        int resp = 0;

        // 정적 메서드 사용
        boolean authorized = AuthUtil.authorize(authKey);

        if (authorized) {
            resp = AuthUtil.authenticate(id, pw);
        }
        else {
            resp = -1;
        }

        if (resp == -1) {
            return LoginResult.badAuthKey();
        }

        if (resp == 1) {
            Customer c = customerRepo.findOne(id);
            return LoginResult.authenticated(c);
        }

        return LoginResult.fail(resp);
    }
}
