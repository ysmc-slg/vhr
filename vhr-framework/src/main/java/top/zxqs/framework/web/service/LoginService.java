package top.zxqs.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import top.zxqs.common.constant.Constants;
import top.zxqs.common.core.domain.entity.Hr;
import top.zxqs.common.core.domain.model.LoginUser;
import top.zxqs.common.core.redis.RedisCache;
import top.zxqs.common.exception.ServiceException;
import top.zxqs.common.exception.user.CaptchaExpireException;
import top.zxqs.common.exception.user.UserPasswordNotMatchException;
import top.zxqs.common.utils.DateUtils;
import top.zxqs.common.utils.MessageUtils;
import top.zxqs.common.utils.ServletUtils;
import top.zxqs.common.utils.ip.IpUtils;
import top.zxqs.framework.manager.AsyncManager;
import top.zxqs.framework.manager.factory.AsyncFactory;
import top.zxqs.system.service.IConfigService;
import top.zxqs.system.service.IHrService;

import javax.annotation.Resource;

/**
 * 登录校验方法
 * @Author: zxq
 * @date: 2022-01-21 14:35
 */
@Service
public class LoginService {
    @Autowired
    private IConfigService configService;
    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IHrService hrService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录验证
     * @param username
     * @param password
     * @param code
     * @param uuid
     * @return
     */
    public String login(String username, String password, String code, String uuid) {
        // 验证码开关
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        if(captchaOnOff){
            validateCaptcha(username,code,uuid);
        }

        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        } catch (Exception e){
            // 用户或密码不正确
            if(e instanceof BadCredentialsException){
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        // 记录登录日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 更新HR 登录信息
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     * @param username
     * @param code
     * @param uuid
     */
    private void validateCaptcha(String username, String code, String uuid) {
        // redis中保存验证码的key
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String captcha = redisCache.getCacheObject(verifyKey);

        if(captcha == null){
            // 异步保存到登录信息
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }

        if(!code.equalsIgnoreCase(captcha)){
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaExpireException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param hrId 登录ID
     */
    public void recordLoginInfo(Long hrId)
    {
        Hr hr = new Hr();
        hr.setHrId(hrId);
        hr.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        hr.setLoginDate(DateUtils.getNowDate());
        hrService.updateHrProfile(hr);
    }

}
