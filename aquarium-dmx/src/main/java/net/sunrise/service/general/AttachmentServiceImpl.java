package net.sunrise.service.general;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.sunrise.domain.entity.general.Attachment;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.repository.general.AttachmentRepository;
import net.sunrise.service.api.general.AttachmentService;

@Service
public class AttachmentServiceImpl extends GenericServiceImpl<Attachment, Long> implements AttachmentService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private AttachmentRepository repository;
	
	protected BaseRepository<Attachment, Long> getRepository() {
		return this.repository;
	}
}
