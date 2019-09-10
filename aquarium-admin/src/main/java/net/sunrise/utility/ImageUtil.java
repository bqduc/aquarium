package net.sunrise.utility;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 *  Image utility class that handles conversion of String images to byte
 *  arrays and vice versa.  Also holds default no-image String pictures.
 *
 */

public class ImageUtil {
	private static final String SMALL_NO_IMAGE = "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";
	private static final String LARGE_NO_IMAGE = "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";
	
	/**
	 *  Returns a String image that shows small 'No Image' pic with a grey background. 
	 */
	public static String smallNoImage() {
		return SMALL_NO_IMAGE;
	}
	
	/**
	 * Returns a String image that shows large 'No Image' pic with a grey background.
	 */ 
	public static String largeNoImage() {
		return LARGE_NO_IMAGE;
	}

	/**
	 * Convert String image to byte array.
	 */
	public static byte[] decode(String image) {
		Decoder decoder = Base64.getDecoder();
		return decoder.decode(image);
	}
	
	/**
	 * Convert byte array to String image. 
	 */
	public static String encodeToString(byte[] byteArray) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(byteArray);
	}
}
