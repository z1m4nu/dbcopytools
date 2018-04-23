package org.crossroad.db.util.table;

import java.util.List;

public interface IBaseIndex {
	List<IColumnIndex> getColumns();
	String getName();
}
