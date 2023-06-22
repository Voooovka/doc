package ua.lviv.iot.bank.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.lviv.iot.bank.model.Card;
import ua.lviv.iot.bank.model.Client;
import ua.lviv.iot.bank.repo.CardRepo;


@Service
public class CardService  extends AbstractService<Card>{

    private final ClientService clientService;

    @Autowired
    public CardService(CardRepo repository, ClientService clientService) {
        super(repository);
        this.clientService = clientService;
    }

    @Override
    public Card mapCsvToObject(String[] objectCsv) {
        String number = objectCsv[1];
        LocalDate expiredDate = LocalDate.parse(objectCsv[2]);
        String cvv = objectCsv[3];
        long balance = Long.parseLong(objectCsv[4]);
        Client owner = clientService.getById(Long.parseLong(objectCsv[5]));

        return new Card(number, expiredDate, cvv, balance, owner);
    }
}
