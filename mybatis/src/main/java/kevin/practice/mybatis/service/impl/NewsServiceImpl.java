package kevin.practice.mybatis.service.impl;

import java.util.List;

import kevin.lib.util.exceptions.BusinessException;
import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.dao.custom.CustomNewsMapper;
import kevin.practice.mybatis.dao.factory.MapperFactory;
import kevin.practice.mybatis.dao.model.News;
import kevin.practice.mybatis.service.NewsService;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsServiceImpl implements NewsService{
	private static final Logger log = LoggerFactory
			.getLogger(NewsService.class);

	private static NewsService newsService;

	public static NewsService getNewsService() {
		log.debug("geting newsService.");
		if (newsService == null) {
			log.debug("newsService is not init, do it");
			synchronized (NewsService.class) {
				if (newsService == null) {
					newsService = new NewsServiceImpl();
					log.debug("newsService init ok");
				} else {
					log.debug("some one has do it, just return");
				}
			}
		}
		return newsService;
	}

	@Override
	public List<News> getNewsList() throws ServiceException {
		int start = 0;
		int count = 10;
		List<News> list = null;
		Exception ex = null;
		SqlSession sqlSession = MapperFactory.getSqlSessionFactory()
				.openSession();
		try {
			CustomNewsMapper newsMapper = sqlSession
					.getMapper(CustomNewsMapper.class);
			list = newsMapper.getNewsList(start, count);
		} catch (Exception e) {
			log.error("getNewsList error. start:" + start + ", count:" + count,
					e);
			ex = e;
		} finally {
			sqlSession.close();
		}
		if (ex != null)
			throw new ServiceException(ex);
		return list;
	}

	@Override
	public void saveNewsWithTransaction(String host, String url,
			String title) throws BusinessException, ServiceException {

		News news = new News();
		news.setHost(host);
		news.setTitle(title);
		news.setUrl(url);

		SqlSession sqlSession = MapperFactory.getSqlSessionFactory()
				.openSession();
		Transaction transaction = MapperFactory.getTransaction(sqlSession
				.getConnection());
		Exception ex = null;
		try {
			CustomNewsMapper newsMapper = sqlSession
					.getMapper(CustomNewsMapper.class);
			newsMapper.insertSelective(news);
		} catch (Exception e) {
			log.error("saveNewsWithTransaction error. host:" + host + ", url:"
					+ url + ", title" + title, e);
			MapperFactory.transactionRollback(transaction);
			ex = e;
		} finally {
			MapperFactory.transactionClose(transaction);
		}
		if (ex != null)
			throw new ServiceException(ex);
	}
	
	@Override
	public void saveNews(String host, String url,
			String title) throws BusinessException, ServiceException {

		News news = new News();
		news.setHost(host);
		news.setTitle(title);
		news.setUrl(url);

		SqlSession sqlSession = MapperFactory.getSqlSessionFactory()
				.openSession(true);
		Exception ex = null;
		try {
			CustomNewsMapper newsMapper = sqlSession
					.getMapper(CustomNewsMapper.class);
			newsMapper.insertSelective(news);
		} catch (Exception e) {
			log.error("saveNewsWithTransaction error. host:" + host + ", url:"
					+ url + ", title" + title, e);
			ex = e;
		} finally {
			sqlSession.close();
		}
		if (ex != null)
			throw new ServiceException(ex);
	}
}