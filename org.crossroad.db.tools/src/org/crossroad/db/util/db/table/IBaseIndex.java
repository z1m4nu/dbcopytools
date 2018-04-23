package org.crossroad.db.util.db.table;

import java.util.List;

public interface IBaseIndex {
	List<IColumnIndex> getColumns();
	String getName();
}
