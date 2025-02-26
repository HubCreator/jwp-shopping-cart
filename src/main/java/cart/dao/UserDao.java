package cart.dao;

import cart.domain.user.User;
import cart.domain.user.UserEmail;
import cart.domain.user.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<User> userRowMapper = (result, count) ->
            new User(result.getLong("id"),
                    result.getString("email"),
                    result.getString("password")
            );

    @Override
    public List<User> findAll() {
        final String query = "SELECT u.id, u.email, u.password FROM _user u";
        return jdbcTemplate.query(query, userRowMapper);
    }

    @Override
    public Optional<User> findUserByEmail(final UserEmail email) {
        final String query = "SELECT u.id, u.email, u.password FROM _user u WHERE u.email = ?";
        try {
            final User user = jdbcTemplate.queryForObject(query, userRowMapper, email.getUserEmail());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(final String email, final String password) {
        final String query = "SELECT u.id, u.email, u.password FROM _user u WHERE u.email = ? and u.password = ?";
        try {
            final User user = jdbcTemplate.queryForObject(query, userRowMapper, email, password);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
