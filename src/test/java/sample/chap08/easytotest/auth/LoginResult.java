package sample.chap08.easytotest.auth;

public class LoginResult {
    public static LoginResult badAuthKey() {
        return null;
    }

    public static LoginResult authenticated(Customer c) {
        return null;
    }

    public static LoginResult fail(int resp) {
        return null;
    }
}
