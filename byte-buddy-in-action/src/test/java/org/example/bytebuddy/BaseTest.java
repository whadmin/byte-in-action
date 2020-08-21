package org.example.bytebuddy;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: wuhao.w
 * @Date: 2020/8/21 16:04
 */
@Slf4j
public class BaseTest {


    /**
     * 将创建类的字节码写入Class文件
     *
     * @param bytes 字节码
     */
    public static void outputClazz(byte[] bytes, Class sourceClass, String classFileName) {
        FileOutputStream out = null;
        try {
            String classpath=sourceClass.getResource("").getPath()+classFileName;
            out = new FileOutputStream(new File(classpath));
            log.info("类输出路径：" + classpath);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
