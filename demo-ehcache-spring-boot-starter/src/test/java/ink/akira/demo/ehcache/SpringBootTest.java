package ink.akira.demo.ehcache;

import ink.akira.ehcache.dao.EhcacheDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootTest {
    @Autowired
    private EhcacheDAO ehcacheDAO;

    @Test
    public void testEhcache(){
        ehcacheDAO.put("my_key", "my_value");
        Object myValue = ehcacheDAO.get("my_key");
        System.out.println(myValue);
        System.out.println((String) myValue);
    }
}
