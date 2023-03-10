package com.knifez.fridaybootapi.controller;

import com.knifez.fridaybootadmin.dto.AppRolePagedQueryRequest;
import com.knifez.fridaybootadmin.dto.AppUserResponse;
import com.knifez.fridaybootadmin.entity.AppRole;
import com.knifez.fridaybootadmin.service.IAppPermissionGrantService;
import com.knifez.fridaybootadmin.service.IAppRoleService;
import com.knifez.fridaybootcore.annotation.ApiRestController;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import com.knifez.fridaybootcore.dto.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-07-06
 */
@AllowAuthenticated
@Api(tags = "角色管理")
@ApiRestController
@RequestMapping("/role")
public class AppRoleController {


    private final IAppRoleService roleService;

    private final IAppPermissionGrantService permissionService;

    public AppRoleController(IAppRoleService roleService, IAppPermissionGrantService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }


    /**
     * 分页列表
     *
     * @param queryRequest 查询请求
     * @return {@link PageResult}<{@link AppRole}>
     */
    @PostMapping("list")
    @ApiOperation("分页列表")
    public PageResult<AppRole> pagedList(@RequestBody AppRolePagedQueryRequest queryRequest) {
        return roleService.listByPageQuery(queryRequest);
    }

    /**
     * 所有角色列表
     *
     * @return {@link List}<{@link AppRole}>
     */
    @AllowAuthenticated
    @PostMapping("all")
    @ApiOperation("所有角色列表")
    public List<AppRole> allRoles() {
        return roleService.list();
    }

    @AllowAuthenticated
    @GetMapping("{roleName}/permission-list")
    @ApiOperation("根据角色名获取角色绑定菜单列表")
    public int[] permissionsByRoleName(@PathVariable String roleName) {
        var list = permissionService.getSelectMenusByRoleName(roleName);
        return list.stream().mapToInt(Integer::parseInt).toArray();
    }

    /**
     * 根据id获取角色
     *
     * @param id id
     * @return {@link AppUserResponse}
     */
    @GetMapping("{id}")
    @ApiOperation("根据id获取角色")
    public AppRole findById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    /**
     * 创建
     *
     * @param role 角色
     * @return {@link Boolean}
     */
    @PostMapping
    @ApiOperation("新增角色")
    public Boolean create(@RequestBody AppRole role) {
        permissionService.saveByRole(role.getPermissions(), role.getName());
        return roleService.save(role);
    }

    /**
     * 更新
     *
     * @param role 角色
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @ApiOperation("修改角色")
    public Boolean update(@RequestBody AppRole role) {
        permissionService.saveByRole(role.getPermissions(), role.getName());
        return roleService.updateById(role);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除角色")
    public Boolean delete(@PathVariable Long id) {
        return roleService.removeById(id);
    }
}
