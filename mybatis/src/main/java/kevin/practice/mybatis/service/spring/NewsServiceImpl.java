package kevin.practice.mybatis.service.spring;

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

public class NewsServiceImpl implements NewsService {
	private static final Logger log = LoggerFactory
			.getLogger(NewsService.class);

	private CustomNewsMapper customNewsMapper;
	public void setCustomNewsMapper(CustomNewsMapper customNewsMapper) {
		this.customNewsMapper = customNewsMapper;
	}


	public List<News> getNewsList() throws ServiceException {
		int start = 0;
		int count = 10;
		try {
			List<News> list = customNewsMapper.getNewsList(start, count);
			return list;
		} catch (Exception e) {
			log.error("getNewsList error. start:" + start + ", count:" + count,
					e);
			throw new ServiceException(e);
		}
	}

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
	
	public void saveNews(String host, String url,
			String title) throws BusinessException, ServiceException {

		News news = new News();
		news.setHost(host);
		news.setTitle(title);
		news.setUrl(url);

		try {
			customNewsMapper.insertSelective(news);
		} catch (Exception e) {
			log.error("saveNewsWithTransaction error. host:" + host + ", url:"
					+ url + ", title" + title, e);
			throw new ServiceException(e);
		}
	}
}
