package kevin.practice.mybatis;

import kevin.lib.util.exceptions.ServiceException;

/**
 * Hello world!
 * 
 */
public class App {

    public static void test() throws ServiceException {
        try {
            System.out.println("start");
            throw new ServiceException("sdf");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch");
            return;
        } finally {
            for(int i=0; i<10; i++)
            System.out.println("哈哈");
        }
    }

    public static void main(String[] args) {
        try {
            test();
            System.out.println("完了");
        } catch (Exception e) {
            System.out.println("再catch");
            e.printStackTrace();
        }
    }
}
