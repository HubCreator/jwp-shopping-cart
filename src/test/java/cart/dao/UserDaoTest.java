package cart.dao;

import cart.domain.user.User;
import cart.domain.user.UserEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@Import(UserDao.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @DisplayName("기존에 존재하는 사용자의 정보를 이메일을 통해 가져올 수 있다.")
    @Test
    void findUserByEmail() {
        // given
        final Optional<User> userOptional = userDao.findUserByEmail(new UserEmail("a@a.com"));

        // when
        final User user = userOptional.get();

        // then
        assertThat(user.getUserEmailValue()).isEqualTo("a@a.com");
    }

    @DisplayName("모든 사용자의 정보를 가져올 수 있다.")
    @Test
    void findAll() {
        assertDoesNotThrow(() -> userDao.findAll());
    }
}