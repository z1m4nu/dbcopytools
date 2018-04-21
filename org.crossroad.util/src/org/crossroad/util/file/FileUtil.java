/**
 * 
 */
package org.crossroad.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

/**
 * @author e.soden
 *
 */
public final class FileUtil {

	/**
	 * 
	 */
	private FileUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String readTextFile(String file) throws Exception {
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return buffer.toString();
	}

	public static Properties loadProperties(String file) throws Exception {
		Properties properties = null;
		File f = new File(file);

		if (f.exists()) {
			properties = new Properties();
			properties.load(new FileInputStream(f));
		}
		return properties;
	}

}
