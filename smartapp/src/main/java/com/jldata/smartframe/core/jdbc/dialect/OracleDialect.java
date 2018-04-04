package com.jldata.smartframe.core.jdbc.dialect;

/**
 * OracleDialect.
 */
public class OracleDialect extends Dialect {

	/**
	 *
	 * @param pageNumber 0代表第一页
	 * @param pageSize
	 * @param findSql
	 * @return
	 */
	public String forPaginate(int pageNumber, int pageSize, StringBuilder findSql) {
		int start = pageNumber * pageSize;
		int end = pageNumber * pageSize;
		StringBuilder ret = new StringBuilder();
		ret.append("select * from ( select row_.*, rownum rownum_ from (  ");
		ret.append(findSql);
		ret.append(" ) row_ where rownum <= ").append(end).append(") table_alias");
		ret.append(" where table_alias.rownum_ > ").append(start);
		return ret.toString();
	}
}
