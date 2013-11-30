package kevin.practice.mybatis;

import kevin.lib.util.SpringContextUtil;
import kevin.lib.util.exceptions.BusinessException;
import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.service.NewsService;

import org.junit.Test;

public class NewsSpringTest extends BaseTester {
	
	
	@Test
	public void testSave() {
		try {
			NewsService newsService = SpringContextUtil.getBean("newsService");
			newsService.saveNews("广州", "test", "sdf");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
