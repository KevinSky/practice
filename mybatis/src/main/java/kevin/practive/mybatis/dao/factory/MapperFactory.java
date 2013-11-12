package kevin.practive.mybatis.dao.factory;

import kevin.lib.util.JsonUtil;
import kevin.practive.mybatis.dao.custom.CustomNewsMapper;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperFactory {
    private static final Logger log = LoggerFactory.getLogger(MapperFactory.class);

    private static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSessionFactory getSqlSessionFactory() {
        log.debug("geting sqlSessionFactory.");
        if (sqlSessionFactory == null) {
            log.debug("sqlSessionFactory is not init, do it");
            synchronized (MapperFactory.class) {
                if (sqlSessionFactory == null) {
                    try {
                        initSqlSessionFactory();
                    } catch (Exception e) {
                        log.error("init sqlSessionFactory error!", e);
                    }
                } else {
                    log.debug("some one has do it, just return");
                }
            }
        }
        return sqlSessionFactory;
    }

    private static void initSqlSessionFactory() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource
                .setUrl("jdbc:mysql://127.0.0.1:3306/news_system?useUnicode=true&amp;characterEncoding=UTF-8&amp;zeroDateTimeBehavior=convertToNull&amp;autoReconnect=true");
        dataSource.setUsername("root");
        dataSource.setPassword("mysql");
        dataSource.setMaxActive(50);
        dataSource.setMaxIdle(20);
        dataSource.setMaxWait(10);
        dataSource.setDefaultAutoCommit(true);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(CustomNewsMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        log.debug("init sqlSessionFactory ok!");
    }
}
