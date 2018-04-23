/**
 * 
 */
package org.crossroad.db.util.db;

/**
 * @author e.soden
 *
 */
public enum DBTYPE {
	SQL, CSV;

	static DBTYPE fromString(String x) throws Exception {

		for (DBTYPE currentType : DBTYPE.values()) {
			if (x.equalsIgnoreCase(currentType.toString())) {
				return currentType;
			}
		}
		throw new Exception("Unmatched Type");
	}
}
