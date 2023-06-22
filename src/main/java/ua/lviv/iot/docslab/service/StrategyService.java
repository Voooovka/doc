package ua.lviv.iot.docslab.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.lviv.iot.docslab.service.strategy.WriteStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class StrategyService {

    private final WriteStrategy writeStrategy;
    @Value("${write.type}")
    private String WRITE_TYPE;

    private static final String POLICE_INCIDENTS_CSV_URI = "https://opendata.utah.gov/api/views/459a-j6x3/rows.csv";

    @SneakyThrows
    public String readAndWritePoliceIncidents() {
        URL url = new URL(POLICE_INCIDENTS_CSV_URI);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            for (int i = 0; i < 100; i++) {
                String line = reader.readLine();
                if (line == null) {
                    break; // Stop reading if we reach the end of the file before 100 lines
                }
                writeStrategy.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("Wrote to %s successfully", WRITE_TYPE);
    }

}
