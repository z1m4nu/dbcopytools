/**
 * 
 */
package org.crossroad.db.util.ope;

import org.crossroad.db.util.cfg.DriversType;
import org.crossroad.db.util.cfg.IMemberExport;
import org.crossroad.db.util.cfg.IMemberImport;

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

		if (DriversType.CSV.equals(src.getDatabase().getDefinition().getDriverId())) {
			operation = new CSVImport(src, dst);
		} else if (DriversType.CSV.equals(dst)) {
			operation = new CSVExport(src, dst);
		} else if (!DriversType.CSV.equals(src) && !DriversType.CSV.equals(dst)) {
			operation = new SQLImport(src, dst);
		}

		return operation;
	}

}
