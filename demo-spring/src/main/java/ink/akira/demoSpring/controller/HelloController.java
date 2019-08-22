package ink.akira.demoSpring.controller;

import ink.akira.demoSpring.pojo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value="/hello")
    public JsonResult hello(String param1, String[] param2) {
        logger.info("===== param1 = " + param1);
        logger.info("===== param2 = " + param2);
        return JsonResult.success("你好！");
    }
}
