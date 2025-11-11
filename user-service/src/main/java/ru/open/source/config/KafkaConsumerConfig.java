package ru.open.source.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.open.source.event.UserCreatedEvent;
import ru.open.source.event.UserDeletedEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.host}")
    private String bootstrapServers;

    @Value("${kafka.group.users.created}")
    private String userCreatedGroup;

    @Value("${kafka.group.users.deleted}")
    private String userDeletedGroup;

    @Bean
    public Map<String, Object> commonConsumerConfigs(@Value("${kafka.group.users.created}") String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return props;
    }

    @Bean
    public ConsumerFactory<String, UserCreatedEvent> userCreatedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonConsumerConfigs(userCreatedGroup),
                new StringDeserializer(),
                new JsonDeserializer<>(UserCreatedEvent.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> userCreatedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent>();
        factory.setConsumerFactory(userCreatedConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, UserDeletedEvent> userDeletedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonConsumerConfigs(userDeletedGroup),
                new StringDeserializer(),
                new JsonDeserializer<>(UserDeletedEvent.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserDeletedEvent> userDeletedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UserDeletedEvent>();
        factory.setConsumerFactory(userDeletedConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }
}