package chap09.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UserApi {

    private UserRegister userRegister;

    public UserApi(UserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> register(@RequestBody UserRegisterReq req) {
        try {
            userRegister.register(req.getId(), req.getPw(), req.getEmail());
            return ResponseEntity.created(URI.create("/users/" + req.getId())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ErrorResponse.error(e));
        }
    }
}
