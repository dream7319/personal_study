package lierl.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/26 17:17
 */
@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome(){
        System.out.println("----------welcome----------");
        return "Hello World";
    }
}
