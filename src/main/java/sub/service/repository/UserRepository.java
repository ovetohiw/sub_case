package sub.service.repository;

import sub.service.dto.User;

public interface UserRepository {

    User findById(Long id);

    void save(User user);

    void update(User user);

    void delete(Long id);

    boolean checkUserByEmail(String email);
}
