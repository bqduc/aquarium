package net.sunrise.workflow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleWorkflowRepository {

    @Autowired
    private SimpleWorkflowDAO simpleWorkflowDAO;
    @Autowired
    private SimpleWorkitemDAO simpleWorkitemDAO;

    public SimpleWorkflow findWorkflowByWorkflowId(Long workflowId) {
        SimpleWorkflow workflow = simpleWorkflowDAO.getOne(workflowId);
        List<SimpleWorkItemEntity> workitemEntityList = simpleWorkitemDAO.findByWorkflowId(workflowId);
        workflow.setWorkItemEntityList(workitemEntityList);
        return workflow;
    }

    public SimpleWorkflow updateWorkflow(SimpleWorkflow newWorkflow) {
        final SimpleWorkflow savedWorkflow = simpleWorkflowDAO.save(newWorkflow);
        newWorkflow.getWorkItemList().forEach(simpleWorkitem -> {
            SimpleWorkItemEntity workitemEntity = new SimpleWorkItemEntity(simpleWorkitem);
            //if workitemEntity's workflow id is null that means this is new workflow never saved in database before
            if (workitemEntity.getWorkflowId() == null) {
                workitemEntity.setWorkflowId(savedWorkflow.getId());
            }
            simpleWorkitemDAO.save(workitemEntity);
        });
        return savedWorkflow;
    }
}