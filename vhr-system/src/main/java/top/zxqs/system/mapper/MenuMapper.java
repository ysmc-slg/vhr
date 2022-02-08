package top.zxqs.system.mapper;

import top.zxqs.common.core.domain.entity.Menu;

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

    /**
     * 获取所有的菜单
     * @return
     */
    List<Menu> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单树信息
     * @param userId
     * @return
     */
    List<Menu> selectMenuTreeByUserId(Long userId);
}
