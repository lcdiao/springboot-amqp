package cn.lcdiao.springbootamqp.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/6/3 15:53
 */
@Configuration
public class MyAMQPConfig {

    @Bean
    public MessageConverter messageConverter(){
        //将数据自动的转为json发送出去
        return new Jackson2JsonMessageConverter();
    }
}
