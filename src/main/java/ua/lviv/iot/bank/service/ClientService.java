package ua.lviv.iot.bank.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.bank.model.Client;
import ua.lviv.iot.bank.repo.ClientRepo;
import ua.lviv.iot.bank.service.AbstractService;

@Service
public class ClientService extends AbstractService<Client> {

    @Autowired
    public ClientService(ClientRepo repository) {
        super(repository);
    }

    @Override
    public Client mapCsvToObject(String[] objectCsv) {
        String username = objectCsv[1];
        String email = objectCsv[2];

        return new Client(username, email);
    }
}
