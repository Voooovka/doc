package ua.lviv.iot.bank.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.bank.model.Card;
import ua.lviv.iot.bank.model.Client;
import ua.lviv.iot.bank.model.Transaction;
import ua.lviv.iot.bank.repo.CardRepo;
import ua.lviv.iot.bank.repo.TransactionRepo;

@Service
public class TransactionService extends AbstractService<Transaction>{

    private final ClientService clientService;

    @Autowired
    public TransactionService(TransactionRepo repository, ClientService clientService) {
        super(repository);
        this.clientService = clientService;
    }

    @Override
    public Transaction mapCsvToObject(String[] objectCsv) {
        String paidAmount = objectCsv[1];
        String fee = objectCsv[2];
        LocalDate date = LocalDate.parse(objectCsv[3]);
        Client recipient = clientService.getById(Long.parseLong(objectCsv[4]));
        Client sender = clientService.getById(Long.parseLong(objectCsv[5]));

        return new Transaction(paidAmount, fee, date, recipient, sender);
    }
}
