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
    
    private void save(String name) throws ServiceException {
        SqlSession sqlSession = MapperFactory.getSqlSessionFactory()
                .openSession(true);
        try {
            Test1 test1 = new Test1();
            test1.setName(name);
            Test1Mapper mapper = sqlSession
                    .getMapper(Test1Mapper.class);
            mapper.insertSelective(test1);
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void saveTwoTest(String name1, String name2) throws ServiceException {
        try {
            save(name1);
            save(name2);
        } catch (Exception e) {
            log.error("save two error", e);
            throw new ServiceException(e);
        } 
    }

    
    
}
