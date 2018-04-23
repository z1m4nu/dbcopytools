/**
 * 
 */
package org.crossroad.db.util.config;

/**
 * @author e.soden
 *
 */
public interface IMemberImport extends IMemberDef{
	String getSQLImportStatement();
	String getSQLCleanStatement();
}
