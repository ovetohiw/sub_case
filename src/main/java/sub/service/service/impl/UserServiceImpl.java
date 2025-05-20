package sub.service.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sub.service.container.ChangeContainer;
import sub.service.dto.User;
import sub.service.repository.UserRepository;
import sub.service.service.UserService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public ChangeContainer save(UUID uuid, User user) {
        try {
            if (checkUserByEmail(user.getEmail())) {
                throw new RuntimeException("Email is already in use");
            }

            userRepository.save(user);
        } catch (DataAccessException e) {
            return new ChangeContainer(uuid, "Problem with save: " + e.getMessage());
        }

        return new ChangeContainer(uuid, "Save successful");
    }

    @Override
    public ChangeContainer update(UUID uuid, User user) {
        try {
            userRepository.findById(user.getId());
            userRepository.update(user);
        } catch (DataAccessException e) {
            return new ChangeContainer(uuid, "Problem with update: " + e.getMessage());
        }

        return new ChangeContainer(uuid, "Update successful");
    }

    @Override
    public ChangeContainer delete(UUID uuid, Long id) {
        try {
            userRepository.delete(id);
        } catch (DataAccessException e) {
            return new ChangeContainer(uuid, "Problem with delete: " + e.getMessage());
        }

        return new ChangeContainer(uuid, "Delete successful");
    }

    private boolean checkUserByEmail(String email) {
        return userRepository.checkUserByEmail(email);
    }
}
