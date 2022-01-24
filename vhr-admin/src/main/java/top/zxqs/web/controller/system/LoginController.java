package top.zxqs.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.zxqs.common.core.domain.AjaxResult;
import top.zxqs.common.core.domain.model.LoginBody;
import top.zxqs.common.core.domain.model.LoginUser;
import top.zxqs.framework.web.service.LoginService;

/**
 * @Author: zxq
 * @date: 2022-01-21 14:31
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        AjaxResult ajax = AjaxResult.success();

        String token = loginService.login(loginBody.getUsername(),loginBody.getPassword(),loginBody.getCode(),loginBody.getUuid());

        return null;

    }
}
