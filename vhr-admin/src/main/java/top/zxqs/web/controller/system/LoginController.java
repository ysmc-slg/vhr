package top.zxqs.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.zxqs.common.constant.Constants;
import top.zxqs.common.core.domain.AjaxResult;
import top.zxqs.common.core.domain.entity.Hr;
import top.zxqs.common.core.domain.entity.Menu;
import top.zxqs.common.core.domain.model.LoginBody;
import top.zxqs.common.core.domain.model.LoginUser;
import top.zxqs.common.utils.SecurityUtils;
import top.zxqs.framework.web.service.LoginService;
import top.zxqs.framework.web.service.SysPermissionService;
import top.zxqs.system.service.IMenuService;

import java.util.List;
import java.util.Set;

/**
 * @Author: zxq
 * @date: 2022-01-21 14:31
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录接口
     * @param loginBody
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        AjaxResult ajax = AjaxResult.success();

        String token = loginService.login(loginBody.getUsername(),loginBody.getPassword(),loginBody.getCode(),loginBody.getUuid());

        ajax.put(Constants.TOKEN, token);
        return ajax;

    }

    /**
     * 获取用户详细信息
     * @return
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo(){
        Hr hr = SecurityUtils.getLoginUser().getHr();
        // 菜单权限字符串的集合
        Set<String> menuPermission = permissionService.getMenuPermission(hr);
        // 登录用户角色的集合
        Set<String> rolePermission = permissionService.getRolePermission(hr);

        AjaxResult ajax = AjaxResult.success();
        ajax.put("user",hr);
        ajax.put("permissions",menuPermission);
        ajax.put("roles",rolePermission);

        return ajax;
    }

    /**
     * 获取路由
     * @return
     */
    @GetMapping("/getRouters")
    public AjaxResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.selectMenuTreeByUserId(userId);

        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
