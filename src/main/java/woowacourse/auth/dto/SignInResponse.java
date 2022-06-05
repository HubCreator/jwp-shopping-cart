package woowacourse.auth.dto;

import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.domain.Customer;

public class SignInResponse {

    private final String username;
    private final String email;
    private final String token;

    public SignInResponse(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public static SignInResponse fromCustomer(Customer customer, JwtTokenProvider jwtTokenProvider) {
        return new SignInResponse(
                customer.getUsername(),
                customer.getEmail(),
                jwtTokenProvider.createToken(customer.getUsername())
        );
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}