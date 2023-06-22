package ua.lviv.iot.bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.iot.bank.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
