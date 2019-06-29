package ink.akira.demo.cas.controller;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by akira on 2019/6/29.
 */
@RestController
public class HelloController {
    @RequestMapping(value="/hello")
    public String hello(){
        Assertion assertion = AssertionHolder.getAssertion();
        AttributePrincipal principal = assertion.getPrincipal();
        String name = principal.getName();
        System.out.println(name);
        Map<String, Object> attributes = principal.getAttributes();
        attributes.forEach((k, v) -> System.out.println(k + " --> " + v));
        return "success";
    }
}
