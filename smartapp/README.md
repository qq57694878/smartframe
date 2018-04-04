# com.jldata.smartframe
a spring boot smart development framework
#spring boot 实现热部署
https://jingyan.baidu.com/article/870c6fc367b4d7b03fe4beba.html
# **框架简述**
  resttest:是专门测试restapi接口的工程，集成restdocs 可生成rest接口文档。生成命令：mvn package就会在target/generated-docs下生成html
  startapp：基于spring boot，集成spring data jpa，spring security，spring data redis，spring data jdbc，和对spring jdbc template，和sping data的简单封装，为了高效的完成分页查询。

# **startapp工程介绍** 
  com.jldata.startframe包下
  core
      config:JPA,WebMvc，spring secutiry的配置类
      jdbc：对jdbcTemplate的分页查询的封装
      jpa：对spring jpa的封装，为了解决查询条件多造成程序要写很复杂的代码
      mapper：Json，和bean的转化
      security：jwt验证
      simple：是登录获取token等的公共基础实现
  demo  写的一些应用的例子
      controller，service，repository，model，这样的层次结构。
      下有一个综合的例子ArticleController.实现了jdbcTemplate和jpa，封装的jpa三种形式实现的分页查询的例子。
      什么情况的选择不同的形式总结：
        统计类的查询： jdbcTemplate写统一sql语句。 
        过滤多条件多的简单查询： 直接用封装的jpa形式，但是要按约束去传递参数。
        过滤多条件少的简单查询： 直接jpa方法名定义的形式
   
   使用封装的JPA实现分页的注意事项。
      持久化接口ArticleRepository必须继承自BaseRepository，这样才有
       <S extends T> Page<S> findAll(List<PropertyFilter> propertyFilters, Pageable pageable);这个方法。
      
        
      对jpa封装过滤条件参数传递的约定：
      例如：filter_CONTAINS_title 拆分为4段解释： [filter_,CONTAIN,S,title]
      第一部分filter_：传递需过滤的查询条件前缀
      第二部分CONTAIN:是什么形式的条件，CONTAIN相当于 like '%内容%'，集体参见：
      public enum MatchType {
          /** equals. */
          EQ,
          /** %value% */
          CONTAIN,
          /** %value */
          START,
          /** value% */
          END,
          /** less than. */
          LT,
          /** greater than. */
          GT,
          /** less equals. */
          LE,
          /** greater equals. */
          GE,
          /** in. */
          IN,
          /** NOT. */
          NOT,
          /** IS NULL. */
          INL,
          /** NOT NULL. */
          NNL,
          /** unknown. */
          UNKNOWN;
      }
              
      第三部分S:代表传递的值是什么类型，具体参见 
            PropertyType类
            /** String. */
            S(String.class),
            /** Integer. */
            I(Integer.class),
            /** Long. */
            L(Long.class),
            /** Double. */
             N(Double.class),
             /** Date. */
             D(Date.class),
            /** Boolean. */
             B(Boolean.class);       
      第四部分title:数据库表对应model模型的属性字段。
      
      
      