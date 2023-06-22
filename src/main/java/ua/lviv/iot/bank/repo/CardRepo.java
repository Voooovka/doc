package ua.lviv.iot.bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.iot.bank.model.Card;

public interface CardRepo extends JpaRepository<Card, Long> {
}
