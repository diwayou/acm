package com.diwayou.lang.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineStudy {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine groovyEngine = scriptEngineManager.getEngineByName("groovy");

        groovyEngine.eval("println('hello')");
    }
}
