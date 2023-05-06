package cart.web.controller.auth;

import cart.domain.user.User;
import cart.web.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final UserService userService;

    private static final String AUTHORIZATION = "Authorization";

    public LoginCheckInterceptor(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final User user = BasicAuthorizationExtractor.extract(request);
        return userService.isExistUser(user);
    }
}