/**
 * 
 */
package org.crossroad.db.util.db.table.impl;

import org.crossroad.db.util.db.table.IColumnIndex;

/**
 * @author e.soden
 *
 */
public class ColumnIndex implements IColumnIndex {
	private String name = null;
	private int position = 0;

	/**
	 * 
	 */
	public ColumnIndex() {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int ordinalPosition() {
		return position;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

}
