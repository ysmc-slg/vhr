package top.zxqs.system.service.impi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zxqs.common.constant.Constants;
import top.zxqs.common.core.redis.RedisCache;
import top.zxqs.common.core.text.Convert;
import top.zxqs.common.utils.StringUtils;
import top.zxqs.system.domain.Config;
import top.zxqs.system.mapper.ConfigMapper;
import top.zxqs.system.service.IConfigService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: zxq
 * @date: 2022-01-19 15:46
 */
@Service
public class ConfigServiceImpl implements IConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init(){
        loadingConfigCache();
    }
    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    @Override
    public boolean selectCaptchaOnOff() {
        String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
        // 如果 captchaOnOff 是 ""，就默认为开(true)
        if(StringUtils.isEmpty(captchaOnOff)){
            return true;
        }
        // 将返回的结果转换为boolean
        return Convert.toBool(captchaOnOff);
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {
        List<Config> configList = configMapper.selectConfigList();
        for(Config config : configList){
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()),config.getConfigValue());
        }
    }

    /**
     * 根据键名查询参数配置信息
     * @param configKey 参数key
     * @return 参数键值
     */
    public String selectConfigByKey(String configKey){
        // 根据 configKey 获取缓存中的value
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if(StringUtils.isNotEmpty(configValue)){
            return configValue;
        }
        // 如果缓存中没有查到，那就重新存到缓存中去
        Config config = new Config();
        config.setConfigKey(configKey);
        Config retConfig = configMapper.selectConfig(config);

        if(StringUtils.isNotNull(retConfig)){
            // 将configKey 和 configValue 缓存
            redisCache.setCacheObject(getCacheKey(configKey),retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        // ""
        return StringUtils.EMPTY;
    }

    /**
     * 设置 cache key
     * @param configKey 参数键
     * @return 缓存键key
     */
    public String getCacheKey(String configKey){
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
