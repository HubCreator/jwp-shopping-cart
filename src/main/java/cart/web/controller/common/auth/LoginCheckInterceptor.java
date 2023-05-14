package cart.web.controller.common.auth;

import cart.exception.UnAuthorizedException;
import cart.web.controller.cart.dto.AuthCredentials;
import cart.web.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public LoginCheckInterceptor(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final AuthCredentials authCredentials = BasicAuthorizationExtractor.extract(request);
        request.setAttribute("authCredentials", authCredentials);
        if (authService.isValidUser(authCredentials)) {
            return true;
        }

        throw new UnAuthorizedException();
    }
}
