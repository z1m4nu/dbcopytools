/**
 * 
 */
package org.crossroad.db.util.connection.driver;

/**
 * @author e.soden
 *
 */
public enum DriversType {
	DB2, MSS, HDB, DBY, ORC, CSV;
	public static DriversType fromString(String x) throws Exception {

		for (DriversType currentType : DriversType.values()) {
			if (x.equalsIgnoreCase(currentType.toString())) {
				return currentType;
			}
		}
		throw new Exception("Unmatched Type");
	}
}
