package Main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

/**
 * Utils.java
 * Fonctions générales
 *
 */
public class Utils {

	public static final SimpleDateFormat TIMEFORMAT
			= new SimpleDateFormat(Data.TIMEFORMAT_PATTERN);

	public static double getMoyenne(int[] tab) {
		int moyenne = 0;
		for (int t : tab) {
			moyenne += t;
		}
		
		return moyenne / tab.length;
	}

	public static String sha1(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(message.getBytes("UTF-8"));

			//converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			digest = sb.toString();

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
		}
		return digest;
	}

}
