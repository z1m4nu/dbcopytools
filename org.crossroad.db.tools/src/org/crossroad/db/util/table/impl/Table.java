/**
 * 
 */
package org.crossroad.db.util.table.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.crossroad.db.util.table.IColumn;
import org.crossroad.db.util.table.IIndex;
import org.crossroad.db.util.table.IPrimaryKey;
import org.crossroad.db.util.table.ITable;

/**
 * @author e.soden
 *
 */
public class Table implements ITable {
	private String catalog = null;
	private String name = null;
	private String schema = null;
	private Map<String, IColumn> columns = new HashMap<String, IColumn>();
	private Map<String, IPrimaryKey> primaryKeys = new HashMap<String, IPrimaryKey>();
	private Map<String, IIndex> indexes = new HashMap<String, IIndex>();
	private String storageType = null;


	/**
	 * 
	 */
	protected Table() {
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSchema() {
		return this.schema;
	}

	@Override
	public Collection<IColumn> getColumns() {
		return columns.values();
	}

	public void addColumn(IColumn column) {
		this.columns.put(column.getName(), column);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	@Override
	public String getStorageType() {
		return this.storageType;
	}

	@Override
	public IColumn getColumn(String name) {
		return this.columns.get(name);
	}

	@Override
	public Collection<IPrimaryKey> getPrimaryKeys() {
		return this.primaryKeys.values();
	}

	@Override
	public IPrimaryKey getPrimaryKey(String name) {
		return primaryKeys.get(name);
	}

	public void addPrimaryKey(IPrimaryKey pk) {
		this.primaryKeys.put(pk.getName(), pk);
	}

	@Override
	public Collection<IIndex> getIndexes() {
		return indexes.values();
	}

	@Override
	public IIndex getIndex(String name) {
		return indexes.get(name);
	}

	@Override
	public void addIndex(IIndex index) {
		indexes.put(index.getName(), index);
	}

	@Override
	public String getCatalog() {
		return this.catalog;
	}

	@Override
	public boolean hasColumn(String name) {
		return this.columns.containsKey(name);
	}

	@Override
	public boolean hasPrimaryKey(String name) {
		return this.primaryKeys.containsKey(name);
	}

	@Override
	public boolean hasIndex(String index) {
		return this.indexes.containsKey(index);
	}


}
