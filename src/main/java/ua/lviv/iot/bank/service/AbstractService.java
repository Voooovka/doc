package ua.lviv.iot.bank.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<T> {

	private final JpaRepository<T, Long> repository;

	public AbstractService(JpaRepository<T, Long> repository) {
		this.repository = repository;
	}

	public T getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public T saveToDatabase(T newObject) {
		return repository.save(newObject);
	}

	abstract public T mapCsvToObject(String[] objectCsv);
}
