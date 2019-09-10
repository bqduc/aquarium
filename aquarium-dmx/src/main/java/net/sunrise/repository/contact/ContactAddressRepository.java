package net.sunrise.repository.contact;

import java.util.List;
import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.crx.contact.Contact;
import net.sunrise.domain.entity.crx.contact.ContactAddress;
import net.sunrise.framework.repository.RepositoryBase;

@Repository
public interface ContactAddressRepository extends RepositoryBase<ContactAddress, Long> {
	List<ContactAddress> findByContact(Contact contact);
}
