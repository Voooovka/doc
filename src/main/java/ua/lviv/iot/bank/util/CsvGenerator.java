package ua.lviv.iot.bank.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;

import com.opencsv.CSVWriter;

import ua.lviv.iot.bank.model.Card;
import ua.lviv.iot.bank.model.Client;
import ua.lviv.iot.bank.model.Transaction;

public class CsvGenerator {

	public static void main(String[] args) throws IOException {
		List<String[]> data = new LinkedList<>();

		generateClientData(data);
		generateCardData(data);
		generateTransactionData(data);

		try (CSVWriter writer = new CSVWriter(new FileWriter("data.csv"))) {
			writer.writeAll(data);
		}
	}

	private static void generateClientData(List<String[]> data) {
		EasyRandom generator = new EasyRandom();
		List<Client> clients = generator.objects(Client.class, 200).collect(Collectors.toList());

		for (Client client : clients) {
			data.add(client.toCsvFormat());
		}
	}

	private static void generateCardData(List<String[]> data) {
		EasyRandom generator = new EasyRandom();
		List<Card> cards = generator.objects(Card.class, 200).collect(Collectors.toList());

		for (Card card : cards) {
			long clientId = Math.abs(card.getOwner().getId() % 200);
			if (clientId == 0) clientId++;

			card.getOwner().setId(clientId);
			data.add(card.toCsvFormat());
		}
	}

	private static void generateTransactionData(List<String[]> data) {
		EasyRandom generator = new EasyRandom();
		List<Transaction> transactions = generator.objects(Transaction.class, 200).collect(Collectors.toList());

		for (Transaction transaction : transactions) {
			long recipientId = Math.abs(transaction.getRecipient().getId() % 200);
			long senderId = Math.abs(transaction.getSender().getId() % 200);
			if (recipientId == 0) recipientId++;
			if (senderId == 0) senderId++;

			transaction.getRecipient().setId(recipientId);
			transaction.getSender().setId(senderId);

			data.add(transaction.toCsvFormat());
		}
	}


}
