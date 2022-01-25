package top.zxqs.system.service.impi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zxqs.common.core.domain.entity.Role;
import top.zxqs.common.utils.StringUtils;
import top.zxqs.system.mapper.RoleMapper;
import top.zxqs.system.service.IRoleService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色 业务层
 * @Author: zxq
 * @date: 2022-01-24 13:30
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    /**
     * 根据用户ID查询权限
     *
     * @param hrId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByHrId(Long hrId) {
        List<Role> perms = roleMapper.selectRolePermissionByHrId(hrId);
        Set<String> permSet = new HashSet<String>();

        for (Role perm : perms){
            if(StringUtils.isNotNull(perm)){
                String[] split = perm.getRoleKey().trim().split(",");
                permSet.addAll(Arrays.asList(split));
            }
        }
        return permSet;
    }
}
