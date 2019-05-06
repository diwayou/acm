package com.diwayou.lang.script;

import com.google.common.base.Stopwatch;

import javax.script.*;
import java.util.concurrent.TimeUnit;

public class ScriptEngineStudy {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine groovyEngine = scriptEngineManager.getEngineByName("groovy");

        String script = "int b = x + y";
        Bindings bindings = groovyEngine.createBindings();
        bindings.put("x", 10);
        bindings.put("y", 20);

        Stopwatch stopwatch = Stopwatch.createUnstarted();
        stopwatch.start();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            groovyEngine.eval(script, bindings);
        }
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));

        Compilable compilable = (Compilable) groovyEngine;
        CompiledScript compiledScript = compilable.compile(script);
        stopwatch.reset();
        stopwatch.start();
        for (int i = 0; i < count; i++) {
            compiledScript.eval(bindings);
        }
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
