package cart.web.controller.common.auth.dto;

public class AuthCredentials {

    private final String email;
    private final String password;

    public AuthCredentials(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
