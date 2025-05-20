package sub.service.container;

import java.util.UUID;

public class DeleteContainer {
    private Long userId;
    private UUID uuid;

    public DeleteContainer() {
    }

    public DeleteContainer(Long userId, UUID uuid) {
        this.userId = userId;
        this.uuid = uuid;
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
