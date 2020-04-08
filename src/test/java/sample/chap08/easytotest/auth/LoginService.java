package sample.chap08.easytotest.auth;

/**
 * 테스트하기 쉬운 코드
 * - 외부 라이브러리를 직접 사용하지 않고 감싸서 사용한 코드
 *
 * 키를 확인하여 로그인한다.
 */
public class LoginService {
    private CustomerRepository customerRepo;
    private AuthService authService;

    public LoginService(CustomerRepository customerRepo, AuthService authService) {
        this.customerRepo = customerRepo;
        this.authService = authService;
    }

    public LoginResult login(String id, String pw) {
        // 외부 라이브러리를 직접 사용하지 않고 외부 라이브러리와 연동하는 AuthService를 사용한다.
        int resp = authService.authenticate(id, pw);

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
