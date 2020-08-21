package org.example.bytebuddy.createClass;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.example.bytebuddy.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;


/**
 * @Author: wuhao.w
 * @Date: 2020/8/21 10:43
 */
@Slf4j
public class ByteBuddyCreateTest extends BaseTest {


    private String classpath;

    private String packagepath;

    @Before
    public void setup() {
        classpath = ByteBuddyCreateTest.class.getResource("").getPath();
        packagepath="org.example.bytebuddy.createClass.";
    }

    /**
     * 创建类重写已存在方法
     *
     * package org.example.bytebuddy.createClass;
     *
     * public class HelloWorld {
     *     public String toString() {
     *         return "Hello World";
     *     }
     *
     *     public HelloWorld() {
     *     }
     * }
     */
    @Test
    public void testcreateClass1() throws Exception {
        String className="HelloWorld";
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)  //指定操作的类（）
                .name(packagepath+className) // 设置类名称
                .method(ElementMatchers.named("toString"))  //操作toString方法
                .intercept(FixedValue.value("Hello World")) //intercept 拦截了 toString 方法并返回固定的 value
                .make();
        // 输出类字节码

        Class<?> helloWorldClass = dynamicType.load(ByteBuddyCreateTest.class.getClassLoader()) //加载到类加载器
                                              .getLoaded();//获取被加载class

        Object instance = helloWorldClass.newInstance();
        String toString = instance.toString();
        log.info(toString);
        log.info((instance.getClass().getCanonicalName()));

        //字节码写入Class文件（每次执行都需要清理）
        outputClazz(dynamicType.getBytes(),ByteBuddyCreateTest.class,className+".class");
    }


    /**
     * @throws Exception
     */
    @Test
    public void testcreateClass2() throws Exception {
        String className="HelloWorld2";
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)  //新创建的类的父类
                .name("org.example.bytebuddy.createClass."+className) // 设置生成类名称和包路径
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)  //定义方法；名称、返回类型、属性public static
                .withParameter(String[].class, "args")   //定义参数；参数类型、参数名称
                .intercept(FixedValue.value("Hello World")) //intercept 拦截了 toString 方法并返回固定的 value
                .make();

        //字节码写入Class文件（每次执行都需要清理）
        outputClazz(dynamicType.getBytes(),ByteBuddyCreateTest.class,className+".class");
    }

    @Test
    public void testcreateClass3() throws Exception {
        String className="HelloWorld3";
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)  //新创建的类的父类
                .name("org.example.bytebuddy.createClass."+className) // 设置生成类名称和包路径
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)  //定义方法；名称、返回类型、属性public static
                .withParameter(String[].class, "args")   //定义参数；参数类型、参数名称
                .intercept(MethodDelegation.to(ByteBuddyCreateTest.class)) //intercept 拦截了 toString 方法并返回固定的 value
                .make();

        Class<?> clazz = dynamicType.load(ByteBuddyCreateTest.class.getClassLoader()) //加载到类加载器
                .getLoaded();//获取被加载class

        // 反射调用
        clazz.getMethod("main", String[].class).invoke(clazz.newInstance(), (Object) new String[1]);

        //字节码写入Class文件（每次执行都需要清理）
        outputClazz(dynamicType.getBytes(),ByteBuddyCreateTest.class,className+".class");
    }


    public static void main(String[] args) {
        log.info("Hello World");
    }


}
