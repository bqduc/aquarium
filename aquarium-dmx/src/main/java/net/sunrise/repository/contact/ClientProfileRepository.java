package net.sunrise.repository.contact;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.admin.UserAccount;
import net.sunrise.domain.entity.contact.ClientProfile;
import net.sunrise.framework.repository.SearchableRepository;

@Repository
public interface ClientProfileRepository extends SearchableRepository/*JBaseRepository*/<ClientProfile, Long> {
	ClientProfile findByCode(String code);
	ClientProfile findByUser(UserAccount user);

	/*@Query("SELECT entity FROM #{#entityName} entity "
			+ "WHERE ("
			+ " LOWER(entity.code) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.fullName) like LOWER(CONCAT('%',:keyword,'%')) or "
			+ " LOWER(entity.phones) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<ClientProfile> search(@Param("keyword") String keyword, Pageable pageable);*/
}