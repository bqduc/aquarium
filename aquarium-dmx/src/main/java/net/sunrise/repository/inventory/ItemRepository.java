package net.sunrise.repository.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.config.Item;
import net.sunrise.framework.repository.BaseRepository;

@Repository
public interface ItemRepository extends BaseRepository<Item, Long>{
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	public Page<Item> search(@Param("keyword") String keyword, Pageable pageable);

	public Item findByCode(String code);
}
