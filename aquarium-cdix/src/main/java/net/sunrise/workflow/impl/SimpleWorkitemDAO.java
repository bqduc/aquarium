package net.sunrise.workflow.impl;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SimpleWorkitemDAO extends CrudRepository<SimpleWorkItemEntity, Long>{
    public List<SimpleWorkItemEntity> findByWorkflowId(Long workflowId);
}
