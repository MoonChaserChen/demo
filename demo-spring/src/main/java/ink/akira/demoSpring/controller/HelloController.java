package ink.akira.demoSpring.controller;

import ink.akira.demoSpring.pojo.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping(value="/hello")
    public JsonResult hello() {
        return JsonResult.success("你好！");
    }
}
