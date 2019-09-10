package net.sunrise.workflow.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sunrise.framework.entity.BizObjectBase;
import net.sunrise.workflow.stereotype.WorkflowTemplate;

@Entity
@Table(name = "workflow")
public class SimpleWorkflow extends BizObjectBase {
	/*
	 * @Id
	 * 
	 * @Column(name="workflow_id")
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO) private Long workflowId;
	 */

    @Column(name="workflow_template_id")
    private Long workflowTemplateId;

    private STATUS status;

    @Column(name="create_datetime")
    private Date createDate;

    @Column(name="lastupdate_datetime")
    private Date lastUpdateDateTime;

    @Transient
    private List<SimpleWorkItem> workItemList;

    @Transient
    private List<SimpleWorkItemEntity> workItemEntityList;

    @Transient
    private WorkflowTemplate template;
    @Transient
    private SimpleWorkItem startWorkitem;

    public List<SimpleWorkItemEntity> getWorkItemEntityList() {
        return workItemEntityList;
    }

    public void setWorkItemEntityList(List<SimpleWorkItemEntity> workItemEntityList) {
        this.workItemEntityList = workItemEntityList;
    }

    public SimpleWorkItem getStartWorkitem() {
        return startWorkitem;
    }

    public void setStartWorkitem(SimpleWorkItem startWorkitem) {
        this.startWorkitem = startWorkitem;
    }

    public Long getWorkflowTemplateId() {
        return workflowTemplateId;
    }

    public void setWorkflowTemplateId(Long workflowTemplateId) {
        this.workflowTemplateId = workflowTemplateId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(Date lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public List<SimpleWorkItem> getWorkItemList() {
        return workItemList;
    }

    public void setWorkItemList(List<SimpleWorkItem> workItemList) {
        this.workItemList = workItemList;
    }

	/*
	 * public Long getWorkflowId() { return workflowId; }
	 * 
	 * public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
	 */

    public WorkflowTemplate getTemplate() {
        return template;
    }

    public void setTemplate(WorkflowTemplate template) {
        this.template = template;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public enum STATUS {
        PENDING, INPROGRESS, COMPLETED, ERROR, REJECTED;
    }
}
