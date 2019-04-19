package com.diwayou.web.http.robot;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class HttpRobotFactory implements PooledObjectFactory<HttpRobot> {

    public HttpRobotFactory() {
    }

    @Override
    public PooledObject<HttpRobot> makeObject() throws Exception {
        return new DefaultPooledObject<>(new HttpRobot());
    }

    @Override
    public void destroyObject(PooledObject<HttpRobot> p) throws Exception {
        // 默认垃圾回收?
    }

    @Override
    public boolean validateObject(PooledObject<HttpRobot> p) {
        return true;
    }

    @Override
    public void activateObject(PooledObject<HttpRobot> p) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<HttpRobot> p) throws Exception {
    }
}
