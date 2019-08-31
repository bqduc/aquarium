/**
 * 
 */
package net.sunrise.cdix.controller;

/**
 * @author bqduc
 *
 */
public enum CdixPageFlows {
	PERSISTENCE_RESOURCE_BROWSE("persistenceResourceBrowse"),
	UPLOAD_ONE_RESOURCE("uploadOneResource"),
	UPLOAD_MULTIPLE_RESOURCES("uploadMultipleResources");

	private String pageFlow;

	private CdixPageFlows(String pageFlow) {
		this.setPageFlow(pageFlow);
	}

	public String getPageFlow() {
		return pageFlow;
	}

	public void setPageFlow(String pageFlow) {
		this.pageFlow = pageFlow;
	}
}
