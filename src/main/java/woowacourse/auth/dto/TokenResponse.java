package woowacourse.auth.dto;

public class TokenResponse {

    private String accessToken;

    public TokenResponse() {
    }

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "TokenResponse{" + "accessToken='" + accessToken + '\'' + '}';
    }
}