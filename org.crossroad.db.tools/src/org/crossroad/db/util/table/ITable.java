package org.crossroad.db.util.table;

import java.util.Collection;

public interface ITable {
	String getName();
	String getSchema();
	String getCatalog();
	
	
	Collection<IColumn> getColumns();
	void addColumn(IColumn column);
	String getStorageType();
	IColumn getColumn(String name);
	boolean hasColumn(String name);
	
	Collection<IPrimaryKey> getPrimaryKeys();
	IPrimaryKey getPrimaryKey(String name);
	void addPrimaryKey(IPrimaryKey pk);
	boolean hasPrimaryKey(String name);

	
	
	Collection<IIndex> getIndexes();
	IIndex getIndex(String name);
	void addIndex(IIndex index);
	boolean hasIndex(String index);

}
