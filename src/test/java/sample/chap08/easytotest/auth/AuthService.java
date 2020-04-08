package sample.chap08.easytotest.auth;

public class AuthService {
    private String authKey = "someKey";

    public int authenticate(String id, String pw) {
        boolean authorized = AuthUtil.authorize(authKey);

        if (authorized) {
            return AuthUtil.authenticate(id, pw);
        }

        return -1;
    }
}
