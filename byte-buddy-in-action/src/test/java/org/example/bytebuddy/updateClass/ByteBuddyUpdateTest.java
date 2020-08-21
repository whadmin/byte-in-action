package org.example.bytebuddy.updateClass;


import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

/**
 * @Author: wuhao.w
 * @Date: 2020/8/21 15:53
 */
public class ByteBuddyUpdateTest {

    @Test
    public void test_byteBuddy() throws Exception {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(BizMethod.class)   //指定操作类
                .method(ElementMatchers.named("queryUserInfo"))  //指定被操作类指定方法
                .intercept(MethodDelegation.to(MonitorDemo.class)) //委托给谁代理
                .make();

        // 加载类
        Class<?> clazz = dynamicType.load(ByteBuddyUpdateTest.class.getClassLoader())
                .getLoaded();
        // 反射调用
        clazz.getMethod("queryUserInfo", String.class, String.class).invoke(clazz.newInstance(), "10001", "Adhl9dkl");
    }
}
