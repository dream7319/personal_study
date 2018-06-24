package lierl.webflux.service.impl;

import com.google.common.collect.Maps;
import lierl.webflux.entity.User;
import lierl.webflux.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 19:52
 */
@Service
public class UserServiceImpl implements UserService{

    private Map<String,User> data = Maps.newConcurrentMap();

    {
        data.put("1", new User("1","zhangsan","zhangsan@qq.com"));
        data.put("2", new User("2","lisi","lisi@sina.com"));
        data.put("3", new User("3","wangwu","wangwu@163.com"));
    }

    @Override
    public Flux<User> list() {
        return Flux.fromIterable(data.values());
    }

    @Override
    public Flux<User> getById(Flux<String> ids) {
        return ids.flatMap(id->Mono.justOrEmpty(this.data.get(id)));
    }

    @Override
    public Mono<User> getById(String id) {
        return Mono.justOrEmpty(this.data.get(id));
    }

    @Override
    public Flux<User> createOrUpdate(Flux<User> users) {
        return users.doOnNext(user->this.data.put(user.getId(),user));
    }

    @Override
    public Mono<User> createOrUpdate(User user) {
        this.data.put(user.getId(),user);
        return Mono.just(user);
    }

    @Override
    public Mono<User> delete(String id) {
        return Mono.justOrEmpty(this.data.remove(id));
    }
}
