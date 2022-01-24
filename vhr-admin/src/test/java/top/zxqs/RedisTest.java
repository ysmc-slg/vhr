package top.zxqs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import top.zxqs.common.core.redis.RedisCache;
import top.zxqs.framework.web.service.TokenService;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zxq
 * @date: 2022-01-18 11:41
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Test
    void contextLoads() {
        System.out.println(tokenService.secret);
        System.out.println(tokenService.expireTime);
    }

}
