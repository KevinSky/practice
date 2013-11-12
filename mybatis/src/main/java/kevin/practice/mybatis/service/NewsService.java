package kevin.practice.mybatis.service;

import java.util.List;

import kevin.lib.util.exceptions.ServiceException;
import kevin.practive.mybatis.dao.custom.CustomNewsMapper;
import kevin.practive.mybatis.dao.factory.MapperFactory;
import kevin.practive.mybatis.dao.model.News;
import kevin.practive.mybatis.dao.model.NewsExample;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsService {
    private static final Logger log = LoggerFactory.getLogger(NewsService.class);

    private static NewsService newsService;

    public static NewsService getNewsService() {
        log.debug("geting newsService.");
        if (newsService == null) {
            log.debug("newsService is not init, do it");
            synchronized (NewsService.class) {
                if (newsService == null) {
                    newsService = new NewsService();
                    log.debug("newsService init ok");
                } else {
                    log.debug("some one has do it, just return");
                }
            }
        }
        return newsService;
    }

    public List<News> getNewsList() throws ServiceException {
        int start = 0;
        int count = 10;
        List<News> list = null;
        Exception ex = null;
        SqlSession sqlSession = MapperFactory.getSqlSessionFactory().openSession();
        try {
            CustomNewsMapper newsMapper = sqlSession.getMapper(CustomNewsMapper.class);
            list = newsMapper.getNewsList(start, count);
        } catch (Exception e) {
            log.error("getNewsList error. start:" + start + ", count:" + count, e);
            ex = e;
        } finally {
            sqlSession.close();
        }
        if (ex != null)
            throw new ServiceException(ex);
        return list;
    }
}
