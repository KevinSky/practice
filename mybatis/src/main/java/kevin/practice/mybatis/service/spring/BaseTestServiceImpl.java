package kevin.practice.mybatis.service.spring;

import kevin.practice.mybatis.service.TestService;
import kevin.practice.mybatis.test1.database.gen.mapper.Test1Mapper;
import kevin.practice.mybatis.test1.database.gen.model.Test1;
import kevin.practice.mybatis.test2.database.gen.mapper.Test2Mapper;
import kevin.practice.mybatis.test2.database.gen.model.Test2;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseTestServiceImpl implements TestService {

    @Autowired
    protected Test1Mapper testMapper1;
    @Autowired
    protected Test2Mapper testMapper2;
    
    @Override
    public void saveTest1(String name) {
        Test1 t = new Test1();
        t.setName(name);
        testMapper1.insertSelective(t);
    }

    @Override
    public void deleteTest1(int id) {
        testMapper1.deleteByPrimaryKey(id);
    }

    @Override
    public void updateTest1(int id, String name) {
        Test1 t = new Test1();
        t.setId(id);
        t.setName(name);
        testMapper1.updateByPrimaryKeySelective(t);
    }

    @Override
    public void saveTest2(String name) {
        Test2 t = new Test2();
        t.setName(name);
        testMapper2.insertSelective(t);
    }

    @Override
    public void deleteTest2(int id) {
        testMapper2.deleteByPrimaryKey(id);
    }

    @Override
    public void updateTest2(int id, String name) {
        Test2 t = new Test2();
        t.setId(id);
        t.setName(name);
        testMapper2.updateByPrimaryKeySelective(t);
    }

}
