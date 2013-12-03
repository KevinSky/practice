package kevin.practice.mybatis;

import kevin.practice.mybatis.service.impl.NewsServiceImpl;
import java.sql.SQLException;

import kevin.lib.util.exceptions.BusinessException;
import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.service.NewsService;

public class NewsTest {

    public static void testTransaction() {
        String host = "google";
        String url = "http://www.google.com";
        String title = "index";
        try {
            NewsServiceImpl.getNewsService().saveNewsWithTransaction(host, title, url);
        } catch (BusinessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
	public static void testSave() {

		String host = "www.goole.com";
		String url = "http://www.google.com";
		String title = "index11";

		try {
			NewsServiceImpl.getNewsService().saveNews(host, url, title);
			System.out.println("save!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testSaveTransaction() {
		String host = "www.baidu.com";
		String url = "http://www.baidu.com";
		String title = "test";

		try {
			NewsServiceImpl.getNewsService().saveNewsWithTransaction(host, url,
					title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testSaveNewsWithNestedTransaction(){
	    String host = "www.baidu.com";
        String url = "http://www.baidu.com";
        String title = "test";

        try {
            NewsServiceImpl.getNewsService().saveNewsWithNestedTransaction(host, url, title);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void testSaveNewsSameTransaction(){
        String host = "www.baidu.com";
        String url = "http://www.baidu.com";
        String title = "test4";

        try {
            NewsServiceImpl.getNewsService().saveNewsSameTransaction(host, url, title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
	    testSave();
	}
}
