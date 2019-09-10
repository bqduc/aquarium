/**
 * 
 */
package net.sunrise.dispatch;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

/**
 * @author bqduc
 *
 */
@Component(value="uiUtility")
public class UIUtility {
	/**
	 * Formats a BigInteger to a thousand grouped String
	 * @param number
	 * @return
	 */
	public String formatNumber (BigInteger number) {
		if (null==number)
			return "";

		return String.format("%,d", number);
	}
}
