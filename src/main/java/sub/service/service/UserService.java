package sub.service.service;

import sub.service.container.ChangeContainer;
import sub.service.dto.User;

import java.util.UUID;

public interface UserService {

    User findById(Long id);

    ChangeContainer save(UUID uuid, User user);

    ChangeContainer update(UUID uuid, User user);

    ChangeContainer delete(UUID uuid, Long id);
}
