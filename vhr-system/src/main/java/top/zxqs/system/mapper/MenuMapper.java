package top.zxqs.system.mapper;

import java.util.List;

/**
 * menu表  数据层
 */
public interface MenuMapper {
    /**
     * 根据用户ID查询权限
     *
     * @param hrId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long hrId);
}
