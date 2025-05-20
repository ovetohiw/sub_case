package sub.service.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sub.service.container.ChangeContainer;
import sub.service.repository.SubscribeRepository;
import sub.service.service.SubscribeService;
import sub.service.service.UserService;

import java.util.Map;
import java.util.UUID;

@Service
public class SubscribeServiceImpl implements SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final UserService userService;

    public SubscribeServiceImpl(SubscribeRepository subscribeRepository, UserService userService) {
        this.subscribeRepository = subscribeRepository;
        this.userService = userService;
    }

    @Override
    public String[] findByUserId(Long userId) {
        return subscribeRepository.findByUserId(userId);
    }

    @Override
    public String[] findTopic() {
        return subscribeRepository.findTopic();
    }

    @Override
    public Map<Long, String> missingSubscriptions(Long userId) {
        return subscribeRepository.missingSubscriptions(userId);
    }

    @Override
    public ChangeContainer save(UUID uuid, Long userId, Long subscriptionId) {
        try {
            userService.findById(userId);
            if (checkSubscribe(userId, subscriptionId)) {
                throw new RuntimeException("Subscribe with id: "
                        + subscriptionId
                        + " for User with id: "
                        + userId
                        + " already exists"
                );
            }
            subscribeRepository.save(userId, subscriptionId);
        } catch (Exception e) {
            return new ChangeContainer(uuid, "Problem with saving: " + e.getMessage());
        }

        return new ChangeContainer(uuid, "Save successful");
    }

    @Override
    public ChangeContainer delete(UUID uuid, Long userId, Long subscriptionId) {
        try {
            subscribeRepository.delete(userId, subscriptionId);
        } catch (DataAccessException e) {
            return new ChangeContainer(uuid, "Problem with delete: " + e.getMessage());
        }

        return new ChangeContainer(uuid, "Delete successful");
    }

    private boolean checkSubscribe(Long userId, Long subscriptionId) {
        return subscribeRepository.checkSubscribe(userId, subscriptionId);
    }
}
