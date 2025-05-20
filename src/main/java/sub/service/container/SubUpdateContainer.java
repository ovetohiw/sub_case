package sub.service.container;

import java.util.UUID;

public class SubUpdateContainer {
    private Long subscriptionId;
    private Long userId;
    private UUID uuid;

    public SubUpdateContainer() {
    }

    public SubUpdateContainer(Long subscriptionId, Long userId, UUID uuid) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.uuid = uuid;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
