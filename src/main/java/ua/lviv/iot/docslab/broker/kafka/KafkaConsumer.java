package ua.lviv.iot.docslab.broker.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "quickstart", groupId = "group-a")
    public void consume(String message) {
        log.info(message);
    }

}
