package sub.service.container;

import java.util.UUID;

public class ChangeContainer {

    private UUID uuid;
    private String message;

    public ChangeContainer() {
    }

    public ChangeContainer(UUID uuid, String message) {
        this.uuid = uuid;
        this.message = message;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
