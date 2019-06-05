package com.diwayou.lang.reflect;

import com.diwayou.web.ui.event.UpdateUrlEvent;
import com.diwayou.web.ui.fx.FxToolPanel;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Method;
import java.util.List;

public class ReflectStudy {
    public static void main(String[] args) throws NoSuchMethodException {
        Method getMethod = List.class.getMethod("get", int.class);
        Invokable<List<String>, ?> invokable = new TypeToken<List<String>>() {}.method(getMethod);
        System.out.println(invokable.getReturnType()); // Not Object.class!
        System.out.println(invokable.getOwnerType());

        Invokable<?, Object> objectInvokable = Invokable.from(FxToolPanel.class.getMethod("updateUrl", UpdateUrlEvent.class));
        System.out.println(objectInvokable.getReturnType()); // Not Object.class!
        System.out.println(objectInvokable.getOwnerType());
        System.out.println(objectInvokable.getAnnotations()[0]);
    }
}
