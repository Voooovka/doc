package ua.lviv.iot.docslab.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteToKafkaStrategy implements WriteStrategy{
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String TOPIC_NAME;

    @Override
    public void write(String line) {
        kafkaTemplate.send(TOPIC_NAME, line);
    }
}
