package com.cn;

import cn.hutool.core.date.DateUtil;
import com.cn.common.utils.ShiroUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangheng
 * @date 2018-11-08  16:35:00
 */
@SpringBootTest
public class AesPasswordTest {

    @Test
    public void test() {
        String decryptedPassword =  "d4125cec57ce83dd6a7e1493ae8faacc::4ea29924cff061f04592436bbf9a0585::qlRaoKLxSge4WnZh0i74Mg==";
        System.out.println(ShiroUtils.getDecrypt(decryptedPassword));
    }
    @Test
    public void test2() {

        long sumDay = DateUtil.betweenDay(DateUtil.parseDate("2019-03-11"),DateUtil.parseDate("2019-04-15"),true) +1;
        System.err.println(sumDay);
    }

}
