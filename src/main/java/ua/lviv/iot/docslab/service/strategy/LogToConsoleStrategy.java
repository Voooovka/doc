package ua.lviv.iot.docslab.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogToConsoleStrategy implements WriteStrategy{

    @Override
    public void write(String line) {
        log.info(line);
    }

}
