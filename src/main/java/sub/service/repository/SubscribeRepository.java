package sub.service.repository;

import java.util.Map;

public interface SubscribeRepository {

    void save(Long userId, Long subscriptionId);

    String[] findByUserId(Long userId);

    void delete(Long userId, Long subscriptionId);

    String[] findTopic();

    Map<Long, String> missingSubscriptions(Long userId);

    boolean checkSubscribe(Long userId, Long subscriptionId);
}
