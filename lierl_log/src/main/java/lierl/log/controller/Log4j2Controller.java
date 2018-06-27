package lierl.log.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 15:46
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class Log4j2Controller {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping("")
    public String hello(){
        logger.info("info execute index method");
        logger.warn("warn execute index method");
        logger.error("error execute index method");
        log.info("info execute index method");
        log.warn("warn execute index method");
        log.error("error execute index method");
        return "success";
    }
}
