package in.co.pro4.utility;

import java.util.ResourceBundle;

/**
 * Read the property values from application properties file using Resource
 * Bundle.
 * 
 * @author Uttam Singh
 *
 */
public class PropertyReader {
	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.resource.pro4.system");

	/**
	 * Return value of key
	 *
	 * @param key
	 * @return val
	 */
	public static String getValue(String key) {
		String val = null;
		try {
			val = rb.getString(key);
		} catch (Exception e) {
			val = key;
		}
		return val;
	}

	/**
	 * Gets String after placing params values
	 *
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getValue(String key, String param) {
		String msg = getValue(key);

		msg = msg.replace("{0}", param);

		return msg;

	}

	/**
	 * Gets String after placing param values
	 *
	 * @param key
	 * @param param
	 * @return String
	 */
	public static String getValue(String key, String[] param) {
		String msg = getValue(key);

		for (int i = 0; i < param.length; i++) {
			msg = msg.replace("{" + i + "}", param[i]);
		}
		return msg;
	}

	/**
	 * Test method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String[] params = { "RollNo" };
		System.out.println(PropertyReader.getValue("error.email", params));
	}
}
