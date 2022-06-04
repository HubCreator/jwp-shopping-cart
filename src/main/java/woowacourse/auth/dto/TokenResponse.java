package woowacourse.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TokenResponse {

    private final String accessToken;

    @JsonCreator
    public TokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}