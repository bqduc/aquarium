/**
 * 
 */
package net.sunrise.engine.dropbox;

import lombok.Builder;

/**
 * @author bqduc_2
 *
 */
@Builder
public class DropboxUser {
	private String clientIdentifier;
	private String clientLocale;
	private String userName;
	private String workingDirectory;

  /**
   * If this user has allowed us (the Web File Browser app) to "link" to his Dropbox
   * account, then we save the Dropbox <em>access token</em> here.  This access token
   * will let us use the Dropbox API with his account.
   */
  public String accessToken;

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getClientIdentifier() {
		return clientIdentifier;
	}

	public void setClientIdentifier(String clientIdentifier) {
		this.clientIdentifier = clientIdentifier;
	}

	public String getClientLocale() {
		return clientLocale;
	}

	public void setClientLocale(String clientLocale) {
		this.clientLocale = clientLocale;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
}
