package top.zxqs.system.service;

import org.springframework.stereotype.Service;

/**
 * 参数配置 服务层
 *
 * @author zxq
 */

public interface IConfigService {
    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    public boolean selectCaptchaOnOff();

    /**
     * 加载参数缓存数据
     */
    void loadingConfigCache();
}
