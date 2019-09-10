package net.sunrise.repository.general;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.stock.Product;
import net.sunrise.framework.repository.AdvancedSearchRepository;

@Repository
public interface ProductRepositoryOrigin extends AdvancedSearchRepository<Product, Long> {
	Optional<Product> findOneById(Long id);

	Product findByName(String name);

	Product findByCode(String code);
}
