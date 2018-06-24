package com.lierl;

import io.shardingjdbc.example.spring.namespace.mybatis.entity.Order;
import io.shardingjdbc.example.spring.namespace.mybatis.entity.OrderItem;
import io.shardingjdbc.example.spring.namespace.mybatis.repository.OrderItemRepository;
import io.shardingjdbc.example.spring.namespace.mybatis.repository.OrderRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/24 9:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/mybatisShardingDatabaseAndTableContext.xml"})
public class SpringShardingDatabaseAndTableTest {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

    @Before
    public void init(){
        orderRepository.createIfNotExistsTable();
        orderItemRepository.createIfNotExistsTable();
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
    }

    @Test
    public void demo() {

        System.out.println("1.Insert--------------");
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setUserId(51);
            order.setStatus("INSERT_TEST");
            orderRepository.insert(order);
            long orderId = order.getOrderId();

            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId(51);
            item.setStatus("INSERT_TEST");
            orderItemRepository.insert(item);
        }
//        System.out.println(orderItemRepository.selectAll());
//        System.out.println("2.Delete--------------");
//        for (Long each : orderIds) {
//            orderRepository.delete(each);
//            orderItemRepository.delete(each);
//        }

//        orderItemRepository.dropTable();
//        orderRepository.dropTable();
    }

    @After
    public void select(){

        List<OrderItem> orders = orderItemRepository.selectAll();
        for (OrderItem order : orders) {
            System.out.println(order.toString());
        }
    }
}
