package lierl.webflux.service;

import lierl.webflux.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 19:50
 */
public interface UserService {
    Flux<User> list();
    Flux<User> getById(Flux<String> ids);

    Mono<User> getById(String id);

    Flux<User> createOrUpdate(Flux<User> users);

    Mono<User> createOrUpdate(User user);

    Mono<User> delete(String id);
}
