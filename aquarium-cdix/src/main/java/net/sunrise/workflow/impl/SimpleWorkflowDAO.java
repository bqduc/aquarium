package net.sunrise.workflow.impl;

import org.springframework.transaction.annotation.Transactional;

import net.sunrise.framework.repository.BaseRepository;

@Transactional
public interface SimpleWorkflowDAO extends BaseRepository<SimpleWorkflow, Long>{
}
