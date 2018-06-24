package lierl.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/13 18:45
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "config/spring-dubbo-provider.xml");
        context.start();
        System.out.println("服务启动");
        System.in.read();
    }
}
