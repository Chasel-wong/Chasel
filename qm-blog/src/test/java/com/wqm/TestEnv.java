package com.wqm;

import com.wqm.utils.AliOssPropertiesUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEnv {
@Autowired
AliOssPropertiesUtils utils;
  @Test
    public void test() throws Exception {
    utils.afterPropertiesSet();
  }
}
