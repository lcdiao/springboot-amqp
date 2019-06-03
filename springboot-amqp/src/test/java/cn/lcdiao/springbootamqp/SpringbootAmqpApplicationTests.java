package cn.lcdiao.springbootamqp;

import cn.lcdiao.springbootamqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootAmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void createExchange(){
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        System.out.println("创建完成");
    }

    @Test
    public void createQueue(){
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true));//durable:true  持久化
        System.out.println("创建完成");
    }

    @Test
    public void createBinding(){
        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue",Binding.DestinationType.QUEUE,
                "amqpadmin.exchange","amqp.haha",null));
        System.out.println("创建完成");
    }


    /**
     * 单播
     */
    @Test
    public void send() {
        //message需要自己构造一个；定义消息体内容和消息头
        //rabbitTemplate.send(exchange,routeKey,message);

        //只需要传入要发送的对象，自动序列化发送给rabbitmq  ，object默认被当成消息体
        //rabbitTemplate.convertAndSend(exchange,routeKey,object);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","这是第一个消息");
        map.put("data", Arrays.asList("heloworld",123,true));
        //对象被默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct","diao.news",map);
        rabbitTemplate.convertAndSend("exchange.direct","diao.news",new Book("西游记","吴承恩"));
    }

    /**
     * 接收数据
     */
    @Test
    public void receive(){
        //接收数据并转换为对象    (每次只从队列取一个，并将其移出队列)
        Object o = rabbitTemplate.receiveAndConvert("diao.news");
        System.out.println(o.getClass());
        System.out.println(o);
        /*结果
        class java.util.HashMap
        {msg=这是第一个消息, data=[heloworld, 123, true]}
         */
    }

    /**
     * 广播
     */
    @Test
    public void sendMsg(){
        //rabbitTemplate.convertAndSend("exchange.fanout","",new Book("三国演义","罗贯中"));
        rabbitTemplate.convertAndSend("exchange.fanout","",new Book("红楼梦","曹雪芹"));
    }
}
