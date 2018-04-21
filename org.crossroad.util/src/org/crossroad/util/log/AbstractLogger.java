/**
 * 
 */
package org.crossroad.util.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author e.soden
 *
 */
public abstract class AbstractLogger {
	public static final String lineSeparator = System.getProperty("line.separator");
	protected Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 */
	public AbstractLogger() {
		// TODO Auto-generated constructor stub
	}

}
