package ink.akira.helloJdk8;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestCalc {
    @Test
    public void testCalc() throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String foo = "5 * 12 + 3 * 3";
        System.out.println(engine.eval(foo));
    }
}
