/**
 * 
 */
package org.crossroad.db.util.db.table;

/**
 * @author e.soden
 *
 */
public interface IIndex extends IBaseIndex {
	boolean isUnique();
	String getColumnSort();
	String getFilterCond();

}
