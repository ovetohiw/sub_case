package sub.service.resource;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sub.service.container.ChangeContainer;
import sub.service.container.SubUpdateContainer;
import sub.service.service.SubscribeService;

import java.util.Map;

@RestController
public class SubscribeResource {

    private final SubscribeService subscribeService;

    public SubscribeResource(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @PostMapping("/sub/add/subscription")
    public ChangeContainer add(@RequestBody SubUpdateContainer container) {
        Assert.notNull(container.getUserId(), "User id must not be null");
        Assert.notNull(container.getSubscriptionId(), "Subscription id must not be null");
        return subscribeService.save(container.getUuid(), container.getUserId(), container.getSubscriptionId());
    }

    @GetMapping("/sub/find/{userId}")
    public String[] find(@PathVariable("userId") Long userId) {
        Assert.notNull(userId, "User id must not be null");
        return subscribeService.findByUserId(userId);
    }

    @GetMapping("/sub/find/{userId}/missing-subscriptions")
    public Map<Long, String> missingSubscriptions(@PathVariable("userId") Long userId) {
        Assert.notNull(userId, "User id must not be null");
        return subscribeService.missingSubscriptions(userId);
    }

    @DeleteMapping("/sub/delete/subscription")
    public ChangeContainer delete(@RequestBody SubUpdateContainer container) {
        Assert.notNull(container.getUserId(), "User id must not be null");
        Assert.notNull(container.getSubscriptionId(), "Subscription id must not be null");
        return subscribeService.delete(container.getUuid(), container.getUserId(), container.getSubscriptionId());
    }

    @GetMapping("/sub/findTopic")
    public String[] findTopic() {
        return subscribeService.findTopic();
    }
}
