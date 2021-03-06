package com.gl.SpringKafkaConsumer.config;

import util.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {


    public ConsumerFactory<String,String> consumerFactory(){
        Map<String,Object> config=new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);

        return  new DefaultKafkaConsumerFactory(config);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory  factory=new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
 @Bean
   public  ConsumerFactory<String, User> userConsumerFactory(){
         JsonDeserializer<User> deserializer = new JsonDeserializer<>(User.class);
         deserializer.setRemoveTypeHeaders(false);
         deserializer.addTrustedPackages("*");
         deserializer.setUseTypeMapperForKey(true);

        Map<String,Object> config=new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);

     return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);

       // return new DefaultKafkaConsumerFactory(config,new StringDeserializer(), new JsonDeserializer<>(User.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,User> userKafkaListner(){
        ConcurrentKafkaListenerContainerFactory userFactory=new ConcurrentKafkaListenerContainerFactory();
        userFactory.setConsumerFactory(userConsumerFactory());
        return  userFactory;
    }


}
