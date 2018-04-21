package org.crossroad.db.util.db;

import java.util.List;

public interface IBaseIndex {
	List<IColumnIndex> getColumns();
	String getName();
}
