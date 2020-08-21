package org.example.bytebuddy.updateClass;

import java.util.Random;

/**
 * @Author: wuhao.w
 * @Date: 2020/8/21 15:51
 */
public class BizMethod {

    /**
     * 等待被监控的方法
     */
    public String queryUserInfo(String uid, String token) throws InterruptedException {
        Thread.sleep(new Random().nextInt(500));
        return "德莱联盟，王牌工程师。小傅哥(公众号：bugstack虫洞栈)，申请出栈！";
    }

}
