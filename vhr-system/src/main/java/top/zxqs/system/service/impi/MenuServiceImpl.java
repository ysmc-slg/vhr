package top.zxqs.system.service.impi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zxqs.common.utils.StringUtils;
import top.zxqs.system.mapper.MenuMapper;
import top.zxqs.system.service.IMenuService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: zxq
 * @date: 2022-01-24 13:40
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 根据hrID 查询菜单数据权限
     * @param hrId
     * @return
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long hrId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(hrId);

        Set<String> permSet = new HashSet<>();

        for (String perm : perms){
            if(StringUtils.isNotEmpty(perm)){
                permSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permSet;
    }
}
