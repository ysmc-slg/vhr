package top.zxqs.system.service;

import top.zxqs.common.core.domain.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色  业务层
 */
public interface IRoleService {
    /**
     *
     * @param hrId
     * @return
     */
    Set<String> selectRolePermissionByHrId(Long hrId);
}
