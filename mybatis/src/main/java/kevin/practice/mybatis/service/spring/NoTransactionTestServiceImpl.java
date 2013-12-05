package kevin.practice.mybatis.service.spring;

import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.service.NormalTestService;
import kevin.practice.mybatis.test1.database.gen.mapper.Test1Mapper;
import kevin.practice.mybatis.test1.database.gen.model.Test1;
import kevin.practice.mybatis.test2.database.gen.mapper.Test2Mapper;
import kevin.practice.mybatis.test2.database.gen.model.Test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 无事务
 * 
 * @author huangjinjie@yy.com
 * 
 */
public class NoTransactionTestServiceImpl extends BaseTestServiceImpl implements NormalTestService {
    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private Test1Mapper test1Mapper;
    @Autowired
    private Test2Mapper test2Mapper;

    @Override
    public void saveTwoTest(String name1, String name2) throws ServiceException {
        try {
            saveTest1(name1);
            saveTest1(name2);
        } catch (Exception e) {
            log.error("no transaction error", e);
            throw new ServiceException(e);
        }
    }

}
