package kevin.practice.mybatis.service;

import kevin.lib.util.exceptions.ServiceException;

public interface NormalTestService {
    public void saveTwoTest(String name1, String name2) throws ServiceException;
}
