package sub.service.container;

import sub.service.dto.User;

import java.util.UUID;

public class UpdateContainer {

    private User user;
    private UUID uuid;

    public UpdateContainer() {
    }

    public UpdateContainer(User user, UUID uuid) {
        this.user = user;
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
