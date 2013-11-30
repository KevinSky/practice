package kevin.practice.mybatis;

import kevin.lib.performance.PerformanceTest;
import kevin.lib.performance.tools.HttpJsonPerform;

public class NewsPerformanceTest {

    public static void main(String[] args) {
        HttpJsonPerform t = new HttpJsonPerform("http://localhost:8080/test");
        PerformanceTest t1 = new PerformanceTest(t, 1000, 100, 100);
        t1.start();
        System.out.println(t1.getReport());
    }

}
