/**
 * 
 */
package org.crossroad.db.util.operations;

/**
 * @author e.soden
 *
 */
public interface ICommitListener {
	
     void onCommit(int commitBlock);
     
}
