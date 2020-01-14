package com.gl.SpringKafkaConsumer.listner;

import util.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerListner {
    @KafkaListener(topics = "kafka-example")
    public void consume(String msg){
        System.out.println("Consumed Message: "+msg);
    }

    @KafkaListener(topics = "kafka-example-userDetails",groupId ="group_json",containerFactory = "userKafkaListner")
    public void consumeUserDetails(User user){
        System.out.println("Consumed Json Message: "+user.toString());
    }
}
