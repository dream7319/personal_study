package lierl.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 17:14
 */
@RestController
public class EchoController {

    @PostMapping(path = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,"!application/xml"},
            produces = MediaType.TEXT_PLAIN_VALUE, headers = "X-Custom=Foo", params = "a!=alpha")
    public String example(){
        System.out.println("----------echo----------");
        return "Hello World";
    }
}
