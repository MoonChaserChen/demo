package ink.akira.dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iflytek.iplat.uc.entity.Member;
import com.iflytek.iplat.uc.ws.IMemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootTest {
    @Reference
    private IMemberService iMemberService;

    @Test
    public void testDubbo(){
        List<Member> listByClassIdStudent = iMemberService.getListByClassIdStudent("2811000226000149253", "10602");
    }
}
