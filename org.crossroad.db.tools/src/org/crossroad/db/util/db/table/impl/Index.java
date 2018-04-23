/**
 * 
 */
package org.crossroad.db.util.db.table.impl;

import org.crossroad.db.util.db.table.IIndex;


/**
 * @author e.soden
 *
 */
public class Index extends AbstractIndex implements IIndex {

	private boolean unique = false;
	private String sort = null;
	private String filterCond = null;

	/**
	 * 
	 */
	public Index() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.crossroad.db.util.db.table.IIndex#isUnique()
	 */
	@Override
	public boolean isUnique() {
		return unique;
	}

	/* (non-Javadoc)
	 * @see org.crossroad.db.util.db.table.IIndex#getColumnSort()
	 */
	@Override
	public String getColumnSort() {
		return this.sort;
	}

	/* (non-Javadoc)
	 * @see org.crossroad.db.util.db.table.IIndex#getFilterCond()
	 */
	@Override
	public String getFilterCond() {
		return this.filterCond;
	}
	
	
	public void setFilterCond(String filterCond) {
		this.filterCond = filterCond;
	}
	
	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public void setUnique(boolean unique) {
		this.unique = unique;
	}


	
	
	
}
