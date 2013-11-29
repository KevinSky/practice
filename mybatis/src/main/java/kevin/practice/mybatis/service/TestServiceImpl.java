package kevin.practice.mybatis.service;

import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.test1.database.gen.mapper.Test1Mapper;
import kevin.practice.mybatis.test1.database.gen.model.Test1;
import kevin.practice.mybatis.test2.database.gen.mapper.Test2Mapper;
import kevin.practice.mybatis.test2.database.gen.model.Test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service("testService")
public class TestServiceImpl implements TestInterface, TestService{

    @Autowired
    private DataSourceTransactionManager test1TransactionManager;
    @Autowired
    private DataSourceTransactionManager test2TransactionManager;
    @Autowired
    public Test1Mapper testMapper1;
    @Autowired
    public Test2Mapper testMapper2;
    
    /**
     * 测试是否autocommit
     * 使用了spring，autocommit就开启了，datasource的defaultAutoCommit不生效
     * @throws ServiceException 
     */
    @Override
    public void testAutoCommit() throws ServiceException {
        saveTest1("testAutoCommit");
        throw new ServiceException("testAutoCommit");
    }

    /**
     * 测试简单的回滚
     * get getTransaction后，后面的就属于事务管理，若不commit，就无法提交
     * @throws ServiceException 
     */
    @Override
    public void testSimpleRollback() throws ServiceException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = test1TransactionManager.getTransaction(def);
        try {
            testAutoCommit();
            test1TransactionManager.commit(status);
        } catch (Exception ex) {
            test1TransactionManager.rollback(status);
            ex.printStackTrace();
        }
        throw new ServiceException("testSimpleRollback");
    }
    
    /**
     * 测试transaction间的sql
     * 
     * rollback只对本datasource的操作有效，并且只影响status之间的操作
     * 同一个transaction不能同时两个操作
     */
    @Override
    public void testCommintBewteen() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        saveTest1("nottransaction");
        TransactionStatus status = test1TransactionManager.getTransaction(def);
        saveTest1("tansaction1");
        saveTest2("tansaction1");
        test1TransactionManager.rollback(status);
        saveTest1("notTansaction1");
    }
    
    /**
     * 测试一个rollback，另一个会如何
     * 
     * 嵌套transaction的话。commit取决于最外层的transaction，即使里面的commit了，外层未commit也无效
     * 里面如果rollback了，外层的只能进行rollbac操作，而不能进行commit（会报UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only）
     */
    @Override
    public void testOneRollback() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        saveTest1("1");
        TransactionStatus status = test1TransactionManager.getTransaction(def);
        testCommit1();
        test1TransactionManager.commit(status);
        saveTest1("2");
    }
    
    public void testCommit1() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = test1TransactionManager.getTransaction(def);
        saveTest1("testCommit1");
        test1TransactionManager.rollback(status);
    }
    public void testCommit2() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = test1TransactionManager.getTransaction(def);
        saveTest1("testCommit1");
        test1TransactionManager.commit(status);
    }
    
    /**
     * 测试两个都rollback，会如何
     * 
     * 两个txManager的事务相互不影响
     */
    @Override
    public void testTwoRollback() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = test2TransactionManager.getTransaction(def);
        testCommit2();
        saveTest2("2");
        test2TransactionManager.rollback(status);
    }

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
