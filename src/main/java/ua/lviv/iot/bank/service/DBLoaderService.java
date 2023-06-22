package ua.lviv.iot.bank.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.bank.repo.FileRepository;
import ua.lviv.iot.bank.model.Card;
import ua.lviv.iot.bank.model.Client;
import ua.lviv.iot.bank.model.Transaction;


@Service
public class DBLoaderService {

	@Autowired
	FileRepository repository;

	ClientService clientService;
	CardService cardService;
	TransactionService transactionService;

	@Autowired
	public DBLoaderService(ClientService clientService, CardService cardService,
						   TransactionService transactionService) {
		this.clientService = clientService;
		this.cardService = cardService;
		this.transactionService = transactionService;
	}

	public void dumpCsvToDB(String filepath) {
		List<String[]> data = repository.readAll(filepath);
		data.forEach(entry -> {
			switch (entry[0]) {
				case "CLIENT":
					Client client = clientService.mapCsvToObject(entry);
					clientService.saveToDatabase(client);
					break;
				case "CARD":
					Card card = cardService.mapCsvToObject(entry);
					cardService.saveToDatabase(card);
					break;
				case "TRANSACTION":
					Transaction transaction = transactionService.mapCsvToObject(entry);
					transactionService.saveToDatabase(transaction);
					break;
			}
		});
	}
}
