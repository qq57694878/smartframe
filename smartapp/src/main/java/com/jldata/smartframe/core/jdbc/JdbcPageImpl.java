package com.jldata.smartframe.core.jdbc;

import com.jldata.smartframe.core.jdbc.dialect.Dialect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class JdbcPageImpl {

    private JdbcTemplate jdbcTemplate;

    private Dialect dialect;

    public JdbcPageImpl(JdbcTemplate jdbcTemplate, Dialect dialect){
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
    }

    /**
     /**
     * 未来考虑处理字符串常量中的字符：
     * 1：select * from article where title = 'select * from'
     *    此例可以正常处理，因为在第一个 from 之处就会正确返回
     *
     * 2：select (select x from t where y = 'select * from ...') as a from article
     *   此例无法正常处理，暂时交由 paginateByFullSql(...)
     *
     * 3：如果一定要处理上例中的问题，调用 public Page paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras)
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param sql  sql
     * @param paras the parameters of sql
     * @return the Page object
     */
    public Page paginate(int pageNumber, int pageSize, String sql, Object... paras) {
        String[] parts = PageSqlKit.parsePageSql(sql);
        String select = parts[0];
        String sqlExceptSelect = parts[1];
        return paginate(pageNumber, pageSize,  select, sqlExceptSelect, paras);
    }
    /**
     * Paginate.
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param select the select part of the sql statement
     * @param sqlExceptSelect the sql statement excluded select part
     * @param paras the parameters of sql
     * @return the Page object
     */
    public Page paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        return doPaginate(pageNumber, pageSize,  select, sqlExceptSelect, paras);
    }

    protected Page doPaginate(int pageNumber, int pageSize,  String select, String sqlExceptSelect, Object... paras) {
            String totalRowSql = "select count(*) from (select 1 " + this.dialect.replaceOrderBy(sqlExceptSelect)+") as alisa";
            StringBuilder findSql = new StringBuilder();
            findSql.append(select).append(' ').append(sqlExceptSelect);
            return doPaginateByFullSql( pageNumber, pageSize, totalRowSql, findSql, paras);
    }


    protected Page doPaginateByFullSql( int pageNumber, int pageSize, String totalRowSql, StringBuilder findSql, Object... paras)  {
        Long totalRow = jdbcTemplate.queryForObject(totalRowSql,paras,Long.class);
        if (totalRow == 0) {
            return new PageImpl(new ArrayList(0), PageRequest.of(pageNumber,pageSize),totalRow);
        }
        String sql = dialect.forPaginate(pageNumber, pageSize, findSql);
        List list = jdbcTemplate.queryForList(sql,paras);
        return new PageImpl(list, PageRequest.of(pageNumber,pageSize),totalRow);
    }


}
