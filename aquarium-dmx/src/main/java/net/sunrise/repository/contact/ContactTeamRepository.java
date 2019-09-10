package net.sunrise.repository.contact;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.contact.ContactTeam;
import net.sunrise.domain.entity.crx.contact.Contact;
import net.sunrise.framework.repository.RepositoryBase;

@Repository
public interface ContactTeamRepository extends RepositoryBase<ContactTeam, Long> {
	List<ContactTeam> findByContact(Contact contact);
}
