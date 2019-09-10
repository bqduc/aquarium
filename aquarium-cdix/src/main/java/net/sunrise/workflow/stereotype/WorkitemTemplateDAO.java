package net.sunrise.workflow.stereotype;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface WorkitemTemplateDAO extends CrudRepository<WorkItemTemplate, Long> {

}
