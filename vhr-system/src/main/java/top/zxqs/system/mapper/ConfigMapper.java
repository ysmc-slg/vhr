package top.zxqs.system.mapper;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import top.zxqs.system.domain.Config;

/**
 * 参数配置 数据层
 *
 * @author ruoyi
 */
public interface ConfigMapper {
    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    public boolean selectCaptchaOnOff();

    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public Config selectConfig(Config config);
}
