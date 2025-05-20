package sub.service.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sub.service.dto.User;
import sub.service.mapper.UserRowMapper;
import sub.service.repository.UserRepository;

import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final NamedParameterJdbcTemplate userJdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate userJdbcTemplate) {
        this.userJdbcTemplate = userJdbcTemplate;
    }

    @Override
    public User findById(Long id) {
        try {
            String sql = "select id, name, email from sys_user where id = :id";

            logger.debug("sql: {}", sql);
            return userJdbcTemplate.queryForObject(sql,
                    Map.of("id", id),
                    new UserRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    @Override
    public void save(User user) {
        String sql = "insert into sys_user (name, email) values (:name, :email)";

        logger.debug("sql: {}", sql);
        userJdbcTemplate.update(sql,
                Map.of("name", user.getName(), "email", user.getEmail()));
    }

    @Override
    public void update(User user) {
        String sql = "update sys_user set name = :name,  email = :email where id = :id";

        logger.debug("sql: {}", sql);
        userJdbcTemplate.update(sql,
                Map.of("name", user.getName(), "email", user.getEmail(), "id", user.getId()));
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        String sql = "delete from sys_user where id = :id";

        logger.debug("sql: {}", sql);
        userJdbcTemplate.update(sql,
                Map.of("id", id)
        );

        sql = "delete from sys_subscribers where user_id = :user_id";

        logger.debug("sql: {}", sql);
        userJdbcTemplate.update(sql,
                Map.of("user_id", id)
        );
    }

    @Override
    public boolean checkUserByEmail(String email) {
        String sql = "select exists(select email from sys_user where email in (:email))";

        logger.debug("sql: {}", sql);
        return Boolean.TRUE.equals(userJdbcTemplate.queryForObject(sql,
                Map.of("email", email),
                Boolean.class
        ));
    }
}
