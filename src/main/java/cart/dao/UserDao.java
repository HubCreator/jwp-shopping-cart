package cart.dao;

import cart.domain.user.User;
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
    public Optional<User> findUserByEmail(final String userEmail) {
        final String query = "SELECT u.id, u.email, u.password FROM _user u WHERE u.email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, userEmail));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
