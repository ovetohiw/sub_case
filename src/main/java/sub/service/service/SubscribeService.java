package sub.service.service;

import sub.service.container.ChangeContainer;

import java.util.Map;
import java.util.UUID;

public interface SubscribeService {

    ChangeContainer save(UUID uuid, Long userId, Long subscriptionId);

    String[] findByUserId(Long userId);

    ChangeContainer delete(UUID uuid, Long userId, Long subscriptionId);

    String[] findTopic();

    Map<Long, String> missingSubscriptions(Long userId);
}
