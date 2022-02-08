package top.zxqs.system.service;

import top.zxqs.common.core.domain.entity.Menu;
import top.zxqs.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

public interface IMenuService {

    /**
     * 根据用户id 查询权限
     * @param hrId
     * @return
     */
    Set<String> selectMenuPermsByUserId(Long hrId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<Menu> menus);
}
