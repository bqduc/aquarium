/**
 * 
 */
package net.sunrise.framework.model.specifications;

/**
 * @author bqduc
 *
 */
public class DefaultSearchRequest extends SearchRequest{
	public final static String fieldCode = "code";

	private String code;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
