package top.zxqs.system.mapper;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import top.zxqs.system.domain.Config;

import java.util.List;

/**
 * 参数配置 数据层
 *
 * @author ruoyi
 */
public interface ConfigMapper {

    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public Config selectConfig(Config config);

    /**
     * 查询所有参数信息
     * @return
     */
    List<Config> selectConfigList();
}
