package ua.lviv.iot.docslab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.iot.docslab.service.StrategyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/strategy")
public class StrategyController {

    private final StrategyService strategyService;

    @PostMapping
    public ResponseEntity<String> writePoliceIncidents() {
        return ResponseEntity.ok(strategyService.readAndWritePoliceIncidents());
    }

}
