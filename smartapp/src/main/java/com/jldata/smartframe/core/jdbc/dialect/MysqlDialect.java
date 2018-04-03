

package com.jldata.smartframe.core.jdbc.dialect;

/**
 * MysqlDialect.
 */
public class MysqlDialect extends Dialect {
	

	public String forPaginate(int pageNumber, int pageSize, StringBuilder findSql) {
		int offset = pageSize * (pageNumber - 1);
		findSql.append(" limit ").append(offset).append(", ").append(pageSize);	// limit can use one or two '?' to pass paras
		return findSql.toString();
	}
}
