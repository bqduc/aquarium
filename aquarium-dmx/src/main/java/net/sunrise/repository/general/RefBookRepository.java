package net.sunrise.repository.general;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.general.RefBook;
import net.sunrise.framework.repository.IBaseRepository;

@Repository
public interface RefBookRepository extends IBaseRepository<RefBook, Long>{
	Optional<RefBook> findOneById(Long id);
	RefBook findByName(String name);
}
