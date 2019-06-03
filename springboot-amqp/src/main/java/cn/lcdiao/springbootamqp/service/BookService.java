package cn.lcdiao.springbootamqp.service;


import cn.lcdiao.springbootamqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    //只要diao.news有消息进来就会被调用
    @RabbitListener(queues = "diao.news")
    public void receive(Book book){
        System.out.println("收到消息:" + book);
    }


    @RabbitListener(queues = "diao")
    public void receive02(Message message) {
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
