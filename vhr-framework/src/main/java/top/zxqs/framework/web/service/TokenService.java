package top.zxqs.framework.web.service;

import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.zxqs.common.constant.Constants;
import top.zxqs.common.core.domain.model.LoginUser;
import top.zxqs.common.core.redis.RedisCache;
import top.zxqs.common.utils.ServletUtils;
import top.zxqs.common.utils.StringUtils;
import top.zxqs.common.utils.ip.AddressUtils;
import top.zxqs.common.utils.ip.IpUtils;
import top.zxqs.common.utils.uuid.IdUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zxq
 * @date: 2022-01-18 14:36
 */
@Component
public class TokenService {
    // 令牌自定义标识
    @Value("${token.header}")
    public String header;

    // 令牌秘钥
    @Value("${token.secret}")
    public String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    public int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private RedisCache redisCache;



    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            try
            {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = getTokenKey(uuid);
                LoginUser user = redisCache.getCacheObject(userKey);
                return user;
            }
            catch (Exception e)
            {
            }
        }
        return null;
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }


    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token){
        if (StringUtils.isNotEmpty(token))
        {
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }


    /**
     * 组装userKey  userKey = login_tokens: + uuid
     * @param uuid
     * @return
     */
    private String getTokenKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 创建令牌
     * @param loginUser
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        // 此时 这个token 并不是真正返回到前端的 token，只是一个UUID 标识，用来获取缓存的用户信息
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        setUserAgent(loginUser);
        refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public String createToken(Map<String, Object> claims){
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 设置用户代理信息
     * @param loginUser
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        // 登录IP
        loginUser.setIpaddr(ip);
        // 登录地点
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        // 登录浏览器名称
        loginUser.setBrowser(userAgent.getBrowser().getName());
        // 操作系统
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }
}
