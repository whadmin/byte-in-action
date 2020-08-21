package org.example.bytebuddy.updateClass;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @Author: wuhao.w
 * @Date: 2020/8/21 15:55
 */
@Slf4j
public class MonitorDemo {

    @RuntimeType  //1 定义运行时的目标方法
    public static Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        Object resObj = null;
        try {
            resObj = callable.call();
            return resObj;
        } finally {
            System.out.println("方法名称：" + method.getName());
            System.out.println("入参个数：" + method.getParameterCount());
            System.out.println("入参类型：" + method.getParameterTypes()[0].getTypeName() + "、" + method.getParameterTypes()[1].getTypeName());
            System.out.println("入参内容：" + args[0] + "、" + args[1]);
            System.out.println("出参类型：" + method.getReturnType().getName());
            System.out.println("出参结果：" + resObj);
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

}