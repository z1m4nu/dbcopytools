/**
 * 
 */
package org.crossroad.jdbc.dump.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author e.soden
 *
 */
public abstract class AbstractJDBCLogger {
	protected Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 */
	public AbstractJDBCLogger() {
		
	}

}
