package top.zxqs.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zxqs.common.core.domain.entity.Hr;
import top.zxqs.system.service.IMenuService;
import top.zxqs.system.service.IRoleService;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 * @Author: zxq
 * @date: 2022-01-24 13:46
 */
@Component
public class SysPermissionService {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    /**
     * 获取角色数据权限
     * @param hr
     * @return
     */
    public Set<String> getRolePermission(Hr hr){
        Set<String> roles = new HashSet<String>();
        // 管理员用友所有权限
        if(hr.isAdmin()){
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByHrId(hr.getHrId()));
        }

        return roles;

    }
    /**
     * 获取菜单数据权限
     * @param hr
     * @return
     */
    public Set<String> getMenuPermission(Hr hr){
        Set<String> perms = new HashSet<String>();
        if(hr.isAdmin()){
            perms.add("*.*.*");
        } else {
            perms.addAll(menuService.selectMenuPermsByUserId(hr.getHrId()));
        }

        return perms;
    }
}
