package ua.lviv.iot.bank.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.iot.bank.model.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {
}
