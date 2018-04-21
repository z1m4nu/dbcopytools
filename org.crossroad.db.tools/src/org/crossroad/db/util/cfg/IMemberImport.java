/**
 * 
 */
package org.crossroad.db.util.cfg;

/**
 * @author e.soden
 *
 */
public interface IMemberImport extends IMemberDef{
	String getSQLImportStatement();
	String getSQLCleanStatement();
}
