package sub.service.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sub.service.repository.SubscribeRepository;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SubscribeRepositoryImpl implements SubscribeRepository {

    private final Logger logger = LoggerFactory.getLogger(SubscribeRepositoryImpl.class);

    private final NamedParameterJdbcTemplate subscribeJdbcTemplate;

    public SubscribeRepositoryImpl(NamedParameterJdbcTemplate subscribeJdbcTemplate) {
        this.subscribeJdbcTemplate = subscribeJdbcTemplate;
    }

    @Override
    public String[] findByUserId(Long userId) {
        String sql = "select sub.name from sys_subscribers s\n" +
                "                    join sys_subscribe sub on s.subscribe_id = sub.id\n" +
                "                    where user_id = :user_id";

        logger.debug("sql: {}", sql);
        return subscribeJdbcTemplate.queryForList(sql,
                Map.of("user_id", userId), String.class).toArray(new String[0]);
    }

    @Override
    public String[] findTopic() {
        String sql = "select s.name\n" +
                "from sys_subscribe s\n" +
                "join (select subscribe_id\n" +
                "from sys_subscribers\n" +
                "group by subscribe_id\n" +
                "order by count(user_id) desc\n" +
                "limit 3) v1 on v1.subscribe_id = s.id";

        logger.debug("sql: {}", sql);
        return subscribeJdbcTemplate.queryForList(sql,
                Map.of(), String.class).toArray(new String[0]);
    }

    @Override
    public Map<Long, String> missingSubscriptions(Long userId) {
        String sql = "select id, name\n" +
                "from sys_subscribe\n" +
                "where id not in (select subscribe_id\n" +
                "                 from sys_subscribers\n" +
                "                 where user_id = :user_id)";

        logger.debug("sql: {}", sql);
        return subscribeJdbcTemplate.query(sql,
                Map.of("user_id", userId),
                (rs, rowNum) -> {
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    return new AbstractMap.SimpleEntry<>(id, name);
                }).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean checkSubscribe(Long userId, Long subscriptionId) {
        String sql = "select exists(select user_id \n" +
                "              from sys_subscribers \n" +
                "              where user_id = :userId and subscribe_id = :subscriptionId)";

        logger.debug("sql: {}", sql);
        return Boolean.TRUE.equals(
                subscribeJdbcTemplate.queryForObject(sql, Map.of("userId", userId, "subscriptionId", subscriptionId), Boolean.class)
        );
    }

    @Override
    public void save(Long userId, Long subscriptionId) {
        String sql = "insert into sys_subscribers (user_id, subscribe_id)" +
                "values (:userId, :subscriptionId)";

        logger.debug("sql: {}", sql);
        subscribeJdbcTemplate.update(sql, Map.of("userId", userId, "subscriptionId", subscriptionId));
    }

    @Override
    public void delete(Long userId, Long subscriptionId) {
        String sql = "delete from sys_subscribers where user_id = :userId and  subscribe_id = :subscriptionId";

        logger.debug("sql: {}", sql);
        subscribeJdbcTemplate.update(sql,
                Map.of("userId", userId, "subscriptionId", subscriptionId)
        );
    }
}
