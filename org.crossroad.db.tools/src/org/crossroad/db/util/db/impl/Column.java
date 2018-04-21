/**
 * 
 */
package org.crossroad.db.util.db.impl;

import org.crossroad.db.util.db.IColumn;

/**
 * @author e.soden
 *
 */
public class Column implements IColumn {
	private int JDBCType = 0;
	private String JDBCTypeName = null;
	private String name = null;
	private int position = 0;
	private boolean nullable = false;
	private String description = null;
	private int size = 0;
	private int scale = 0;

	/**
	 * 
	 */
	public Column(){
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.impl.IColumn#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.impl.IColumn#getJDBCTypeName()
	 */
	@Override
	public String getJDBCTypeName() {
		return this.JDBCTypeName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.impl.IColumn#getJDBCType()
	 */
	@Override
	public int getJDBCType() {
		return this.JDBCType;
	}

	@Override
	public int getPosition() {
		return this.position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}

	public void setJDBCType(int jDBCType) {
		JDBCType = jDBCType;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setJDBCTypeName(String jDBCTypeName) {
		JDBCTypeName = jDBCTypeName;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public boolean isNulleable() {
		return this.nullable;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public int getScale() {
		return this.scale;
	}
	
	
}
