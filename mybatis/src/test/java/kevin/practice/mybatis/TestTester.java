package kevin.practice.mybatis;


import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.service.TestInterface;
import kevin.practice.mybatis.test1.database.gen.mapper.Test1Mapper;
import kevin.practice.mybatis.test1.database.gen.model.Test1;
import kevin.practice.mybatis.test2.database.gen.mapper.Test2Mapper;
import kevin.practice.mybatis.test2.database.gen.model.Test2;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestTester extends BaseTester implements TestInterface {

    @Autowired
    public Test1Mapper testMapper1;
    
    @Autowired
    public Test2Mapper testMapper2;
    
    @Autowired
    private TestInterface testService;
    
    public void testSave() {
        Test1 t1 = new Test1();
        Test2 t2 = new Test2();
        t1.setName("test1");
        t2.setName("test2");
        testMapper1.insertSelective(t1);
        testMapper2.insertSelective(t2);
    }

    @Override
    public void testAutoCommit() throws ServiceException {
        testService.testAutoCommit();
        
    }

    @Override
    public void testSimpleRollback() throws ServiceException {
        try {
            testService.testSimpleRollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Test
    @Override
    public void testOneRollback() {
        try {
            testService.testOneRollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    
    @Override
    public void testTwoRollback() {
        try {
            testService.testTwoRollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }


    @Override
    public void testCommintBewteen() {
        testService.testCommintBewteen();
        
    }

	@Override
	public void testAopTransaction() {
		try {
			testService.testAopTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void testNestedAOPTransaction() {
		try {
			testService.testNestedAOPTransaction();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
}
