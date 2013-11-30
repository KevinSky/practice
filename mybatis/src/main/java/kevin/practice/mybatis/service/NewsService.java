package kevin.practice.mybatis.service;

import java.util.List;

import kevin.lib.util.exceptions.BusinessException;
import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.dao.model.News;

public interface NewsService {

	List<News> getNewsList() throws ServiceException;

	void saveNewsWithTransaction(String host, String url, String title)
			throws BusinessException, ServiceException;

	void saveNews(String host, String url, String title)
			throws BusinessException, ServiceException;

}
