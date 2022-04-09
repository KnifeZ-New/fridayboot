package com.knifez.fridayboot.controller;

import com.knifez.fridayboot.entity.AppUser;
import com.knifez.fridayboot.service.IAppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-04-01
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppUserController {

    private final IAppUserService appUserService;

    @ApiOperation("根据账号获取用户")
    @GetMapping("{account}")
    public AppUser query(@PathVariable String account) {

        return appUserService.getByAccount(account);
    }

    @PermitAll
    @PostMapping
    @ApiOperation("添加用户")
    public boolean create(@RequestBody AppUser user) {
        return appUserService.save(user);
    }
}
