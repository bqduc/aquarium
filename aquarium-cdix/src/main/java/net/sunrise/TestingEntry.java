/**
 * 
 */
package net.sunrise;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;

/**
 * @author bqduc_2
 *
 */
public class TestingEntry {
	private static final String ACCESS_TOKENS[] = new String[] {"clyIdqA2RhoAAAAAAAABs72WTszJXS_GlZH2k6TTTjiUQyGdrsvY1SPwnRRJdYW_", "VE1CL2kpG8AAAAAAAAAADA47480I5HDoN84fxkzaKgQN9-X_CZxPOFGzJEivq9Xw"};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			listFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void listFiles() throws DbxApiException, DbxException, FileNotFoundException, IOException {
    // Create Dropbox client
    DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
    DbxClientV2 client = null;//new DbxClientV2(config, ACCESS_TOKENS[0]);
    FullAccount account = null;
    for (String token :ACCESS_TOKENS) {
    	client = new DbxClientV2(config, token);
      // Get current account info
      account = client.users().getCurrentAccount();
      System.out.println(account.getName().getDisplayName());

      // Get files and folder metadata from Dropbox root directory
      ListFolderResult result = client.files().listFolder("");
      while (true) {
          for (Metadata metadata : result.getEntries()) {
              System.out.println(metadata.getPathLower());
          }

          if (!result.getHasMore()) {
              break;
          }

          result = client.files().listFolderContinue(result.getCursor());
      }
    }
    /*
    // Get current account info
    account = client.users().getCurrentAccount();
    System.out.println(account.getName().getDisplayName());

    // Get files and folder metadata from Dropbox root directory
    ListFolderResult result = client.files().listFolder("");
    while (true) {
        for (Metadata metadata : result.getEntries()) {
            System.out.println(metadata.getPathLower());
        }

        if (!result.getHasMore()) {
            break;
        }

        result = client.files().listFolderContinue(result.getCursor());
    }
    */
    //uploadFile(client);
	}
	
	protected static void uploadFile(DbxClientV2 client) throws FileNotFoundException, IOException, UploadErrorException, DbxException {
    // Upload "test.txt" to Dropbox
    String dropboxFolder = "/ebooks";
    String fileName = "C:\\Users\\bqduc_2\\Downloads\\Beginning Spring 5.pdf";
    try (InputStream in = new FileInputStream(fileName)) {
        FileMetadata metadata = client.files().uploadBuilder(dropboxFolder + "/API Beginning Spring 5.pdf")
            .uploadAndFinish(in);
    }	
    System.out.println("Done");
	}
}
