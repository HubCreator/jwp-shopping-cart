package cart.domain.user;

import java.util.Objects;

public final class User {

    private Long id;
    private UserEmail userEmail;
    private UserPassword userPassword;

    private User() {
    }

    public User(final UserEmail userEmail) {
        this.userEmail = userEmail;
    }

    public User(final String emailAddress, final String password) {
        this(null, emailAddress, password);
    }

    public User(final Long id, final String emailAddress, final String password) {
        this.id = id;
        this.userEmail = new UserEmail(emailAddress);
        this.userPassword = new UserPassword(password);
    }

    public boolean hasSamePassword(final String password) {
        return Objects.equals(password, getUserPasswordValue());
    }

    public Long getId() {
        return id;
    }

    public UserEmail getUserEmail() {
        return userEmail;
    }

    public String getUserEmailValue() {
        return userEmail.getUserEmail();
    }

    public String getUserPasswordValue() {
        return userPassword.getUserPassword();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userEmail=" + userEmail +
                ", userPassword=" + userPassword +
                '}';
    }
}
