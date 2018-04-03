package com.jldata.smartframe.core.jdbc;

import com.jldata.smartframe.core.jdbc.dialect.Dialect;
import com.jldata.smartframe.core.jdbc.dialect.H2Dialect;
import com.jldata.smartframe.core.jdbc.dialect.MysqlDialect;
import com.jldata.smartframe.core.jdbc.dialect.OracleDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcPageKit {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.jpa.database}")
    private String database;

    private Dialect getDialect(){
        Dialect dialect = new MysqlDialect();
         if("mysql".equals(database)){
             dialect = new MysqlDialect();
         }
         else  if("h2".equals(database)){
             dialect = new H2Dialect();
         }
         else  if("oracle".equals(database)){
             dialect = new OracleDialect();
         }
         return dialect;
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
        JdbcPageImpl jdbcPage =  new JdbcPageImpl(this.jdbcTemplate,this.getDialect());
        return jdbcPage.paginate(pageNumber,pageSize,sql,paras);
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
        JdbcPageImpl jdbcPage =  new JdbcPageImpl(this.jdbcTemplate,this.getDialect());
        return jdbcPage.paginate(pageNumber,pageSize,select,sqlExceptSelect,paras);
    }

}
