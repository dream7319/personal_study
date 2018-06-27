package lierl.webflux.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 17:11
 */
@Component
public class EchoHandler implements WebSocketHandler{

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(session.receive().map(msg->session.textMessage("ECHO->" + msg.getPayloadAsText())));
    }
}
