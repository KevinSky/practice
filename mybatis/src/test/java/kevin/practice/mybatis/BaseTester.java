package kevin.practice.mybatis;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/application-service.xml")
public class BaseTester {

    @BeforeClass
    public static void init() {  
    }
} 
