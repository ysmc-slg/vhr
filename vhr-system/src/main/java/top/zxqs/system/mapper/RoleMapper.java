package top.zxqs.system.mapper;

import top.zxqs.common.core.domain.entity.Role;

import java.util.List;

/**
 * role 表   数据层
 */
public interface RoleMapper {
    /**
     * 根据用户ID查询权限
     *
     * @param hrId 用户ID
     * @return 权限列表
     */
    List<Role> selectRolePermissionByHrId(Long hrId);
}
