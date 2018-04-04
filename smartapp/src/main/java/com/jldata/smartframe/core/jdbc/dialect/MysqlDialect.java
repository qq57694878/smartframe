

package com.jldata.smartframe.core.jdbc.dialect;

/**
 * MysqlDialect.
 */
public class MysqlDialect extends Dialect {

	/**
	 *
	 * @param pageNumber 0代表第一页
	 * @param pageSize
	 * @param findSql
	 * @return
	 */
	public String forPaginate(int pageNumber, int pageSize, StringBuilder findSql) {
		int offset = pageSize * pageNumber;
		findSql.append(" limit ").append(offset).append(", ").append(pageSize);	// limit can use one or two '?' to pass paras
		return findSql.toString();
	}
}
