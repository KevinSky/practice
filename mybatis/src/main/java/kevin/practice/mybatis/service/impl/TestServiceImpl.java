package kevin.practice.mybatis.service.impl;

import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.dao.factory.MapperFactory;
import kevin.practice.mybatis.service.NormalTestService;
import kevin.practice.mybatis.test1.database.gen.mapper.Test1Mapper;
import kevin.practice.mybatis.test1.database.gen.model.Test1;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServiceImpl implements NormalTestService {
    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);
    
    private static final Logger logTotal = LoggerFactory.getLogger("logTotal");
    private static final Logger logInsert = LoggerFactory.getLogger("logInsert");
    
    private void save(String name) throws ServiceException {
        SqlSession sqlSession = MapperFactory.getSqlSessionFactory()
                .openSession(true);
        try {
            Test1 test1 = new Test1();
            test1.setName(name);
            Test1Mapper mapper = sqlSession
                    .getMapper(Test1Mapper.class);
            long s = System.currentTimeMillis();
            mapper.insertSelective(test1);
            long d = System.currentTimeMillis();
            logInsert.info("insert: {}", d-s);
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            sqlSession.close();
        }
    }

    
    public void saveTwoTest2(String name1, String name2) throws ServiceException {
        long _s = System.currentTimeMillis();
        SqlSession sqlSession = MapperFactory.getSqlSessionFactory()
                .openSession(false);
        try {
            try {
                Test1 test1 = new Test1();
                test1.setName(name1);
                Test1Mapper mapper = sqlSession
                        .getMapper(Test1Mapper.class);
                long s = System.currentTimeMillis();
                mapper.insertSelective(test1);
                long d = System.currentTimeMillis();
                logInsert.info("insert: {}", d-s);
                
                test1 = new Test1();
                test1.setName(name1);
                mapper = sqlSession
                        .getMapper(Test1Mapper.class);
                s = System.currentTimeMillis();
                mapper.insertSelective(test1);
                d = System.currentTimeMillis();
                
                logInsert.info("insert: {}", d-s);
                sqlSession.commit();
            } catch (Exception e) {
                throw new ServiceException(e);
            } 
        } catch (Exception e) {
            log.error("save two error", e);
            throw new ServiceException(e);
        } finally {
            sqlSession.close();
        }
        
        long _d = System.currentTimeMillis();
        logTotal.info("total: {}", _d-_s);
    }
    
    @Override
    public void saveTwoTest(String name1, String name2) throws ServiceException {
        long _s = System.currentTimeMillis();
        try {
            save(name1);
            save(name2);
        } catch (Exception e) {
            log.error("save two error", e);
            throw new ServiceException(e);
        } 
        long _d = System.currentTimeMillis();
        logTotal.info("total: {}", _d-_s);
    }

    
    
}
