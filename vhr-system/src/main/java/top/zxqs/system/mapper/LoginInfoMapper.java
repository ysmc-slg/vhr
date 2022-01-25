package top.zxqs.system.mapper;

import top.zxqs.system.domain.LoginInfo;

/**
 * login_info  数据层
 */
public interface LoginInfoMapper {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    void insertLogininfor(LoginInfo logininfor);
}
