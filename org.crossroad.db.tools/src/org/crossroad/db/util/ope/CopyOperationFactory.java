/**
 * 
 */
package org.crossroad.db.util.ope;

import org.crossroad.db.util.config.IMemberExport;
import org.crossroad.db.util.config.IMemberImport;
import org.crossroad.db.util.driver.DriversType;

/**
 * @author e.soden
 *
 */
public final class CopyOperationFactory {

	/**
	 * 
	 */
	private CopyOperationFactory() {
	}

	public static AbstractOperation create(IMemberExport src, IMemberImport dst) {
		AbstractOperation operation = null;

		if (DriversType.CSV.equals(src.getDatabase().getDriverId())) {
			operation = new CSVImport(src, dst);
		} else if (DriversType.CSV.equals(dst.getDatabase().getDriverId())) {
			operation = new CSVExport(src, dst);
		} else if (!DriversType.CSV.equals(src.getDatabase().getDriverId()) && !DriversType.CSV.equals(dst.getDatabase().getDriverId())) {
			operation = new SQLImport(src, dst);
		}

		return operation;
	}

}
