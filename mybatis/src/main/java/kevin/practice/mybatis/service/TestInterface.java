package kevin.practice.mybatis.service;

import kevin.lib.util.exceptions.ServiceException;

public interface TestInterface {

    /**
     * 测试是否autocommit
     * @throws ServiceException 
     */
    public void testAutoCommit() throws ServiceException;

    /**
     * 测试简单的回滚
     * @throws ServiceException 
     */
    public void testSimpleRollback() throws ServiceException;
    /**
     * 测试一个rollback，另一个会如何
     * @throws ServiceException 
     */
    public void testOneRollback() throws ServiceException;
    
    /**
     * 测试两个都rollback，会如何
     */
    public void testTwoRollback();

    void testCommintBewteen();
    
    void testAopTransaction() throws ServiceException;
    
    void testNestedAOPTransaction() throws ServiceException;

}
