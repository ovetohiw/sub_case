package sub.service.resource;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sub.service.container.ChangeContainer;
import sub.service.container.DeleteContainer;
import sub.service.container.UpdateContainer;
import sub.service.dto.User;
import sub.service.service.UserService;

@RestController
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public ChangeContainer save(@RequestBody UpdateContainer container) {
        Assert.notNull(container, "User must not be null");
        return userService.save(container.getUuid(), container.getUser());
    }

    @GetMapping("/user/find/{id}")
    public User findUser(@PathVariable("id") Long userId) {
        Assert.notNull(userId, "User id must not be null");
        return userService.findById(userId);
    }

    @PutMapping("/user/update")
    public ChangeContainer update(@RequestBody UpdateContainer update) {
        Assert.notNull(update, "Update must not be null");
        return userService.update(update.getUuid(), update.getUser());
    }

    @DeleteMapping("/user/delete")
    public ChangeContainer delete(@RequestBody DeleteContainer deleteContainer) {
        Assert.notNull(deleteContainer.getUserId(), "User id must not be null");
        return userService.delete(deleteContainer.getUuid(), deleteContainer.getUserId());
    }
}
