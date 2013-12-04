package kevin.practice.mybatis.service.spring;

import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.service.NormalTestService;
import kevin.practice.mybatis.service.TestInterface;
import kevin.practice.mybatis.service.TestService;
import kevin.practice.mybatis.test1.database.gen.mapper.Test1Mapper;
import kevin.practice.mybatis.test1.database.gen.model.Test1;
import kevin.practice.mybatis.test2.database.gen.mapper.Test2Mapper;
import kevin.practice.mybatis.test2.database.gen.model.Test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * jta事务
 * 
 * @author huangjinjie@yy.com
 * 
 */
public class TestServiceJtaImpl extends BaseTestServiceImpl implements TestInterface, TestService, NormalTestService {
    private static final Logger log = LoggerFactory.getLogger(TestServiceJtaImpl.class);

    @Autowired
    private JtaTransactionManager jtaTransactionManager;
    @Autowired

    /**
     * 测试是否autocommit
     * 使用了spring，autocommit就开启了，datasource的defaultAutoCommit不生效
     * 
     * @throws ServiceException
     */
    @Override
    public void testAutoCommit() throws ServiceException {
        saveTest1("jtaAutoCommit");
        // throw new ServiceException("testAutoCommit");
    }

    /**
     * 测试简单的回滚
     * get getTransaction后，后面的就属于事务管理，若不commit，就无法提交
     * 
     * @throws ServiceException
     */
    @Override
    public void testSimpleRollback() throws ServiceException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = jtaTransactionManager.getTransaction(def);
        try {
            saveTest1("jta");
            saveTest2("jta");
            jtaTransactionManager.commit(status);
        } catch (Exception ex) {
            jtaTransactionManager.rollback(status);
            ex.printStackTrace();
        }
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
        TransactionStatus status = jtaTransactionManager.getTransaction(def);
        saveTest1("tansaction1");
        saveTest2("tansaction1");
        jtaTransactionManager.rollback(status);
        saveTest1("notTansaction1");
    }

    /**
     * 测试一个rollback，另一个会如何
     * 
     * 嵌套transaction的话。commit取决于最外层的transaction，即使里面的commit了，外层未commit也无效
     * 里面如果rollback了，外层的只能进行rollbac操作，而不能进行commit（会报UnexpectedRollbackException:
     * Transaction rolled back because it has been marked as rollback-only）
     * 
     * @throws ServiceException
     */
    @Override
    public void testOneRollback() throws ServiceException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        saveTest1("jta1");
        TransactionStatus status = jtaTransactionManager.getTransaction(def);
        testSimpleRollback();
        jtaTransactionManager.commit(status);
        saveTest2("jta2");
    }

    public void testCommit1() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = jtaTransactionManager.getTransaction(def);
        saveTest1("testCommit1");
        jtaTransactionManager.commit(status);
    }

    public void testCommit2() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = jtaTransactionManager.getTransaction(def);
        saveTest1("testCommit1");
        jtaTransactionManager.commit(status);
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
        TransactionStatus status = jtaTransactionManager.getTransaction(def);
        testCommit2();
        saveTest2("2");
        jtaTransactionManager.rollback(status);
    }

    @Override
    public void testAopTransaction() throws ServiceException {
        saveTest1("aop");
        throw new ServiceException("test");
    }

    @Override
    public void testNestedAOPTransaction() throws ServiceException {
        saveTest1("aopnested");
        saveTest2("aopnested");
        testAopTransaction();

    }

    @Override
    public void saveTwoTest(String name1, String name2) throws ServiceException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = jtaTransactionManager.getTransaction(def);
        try {
            saveTest1(name1);
            saveTest2(name2);
            jtaTransactionManager.commit(status);
        } catch (Exception ex) {
            jtaTransactionManager.rollback(status);
            log.error("jta error", ex);
        }
    }

}
