
package com.knifez.fridaybootapi.controller;

import com.knifez.fridaybootadmin.dto.AppDictionaryQueryRequest;
import com.knifez.fridaybootadmin.entity.AppDictionary;
import com.knifez.fridaybootadmin.service.IAppDictionaryService;
import com.knifez.fridaybootcore.annotation.permission.AllowAuthenticated;
import com.knifez.fridaybootcore.dto.PageResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import com.knifez.fridaybootcore.annotation.ApiRestController;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author KnifeZ
 * @since 2022-10-09
 */
@AllowAuthenticated
@Api(tags = "字典管理")
@ApiRestController
@RequestMapping("/dictionary")
public class AppDictionaryController {


    private final IAppDictionaryService appDictionaryService;

    public AppDictionaryController(IAppDictionaryService appDictionaryService) {
        this.appDictionaryService = appDictionaryService;
    }

    @PostMapping("list")
    @ApiOperation("分页列表")
    public PageResult<AppDictionary> pagedList(@RequestBody AppDictionaryQueryRequest queryRequest) {
        return appDictionaryService.listByPageQuery(queryRequest);
    }

    /**
     * 根据id获取字典
     *
     * @param id id
     * @return {@link AppDictionary}
     */
    @GetMapping("{id}")
    @ApiOperation("根据id获取字典")
    public AppDictionary findById(@PathVariable Long id) {
        return appDictionaryService.getById(id);
    }

    /**
     * 创建
     *
     * @param appDictionary 字典
     * @return {@link Boolean}
     */
    @PostMapping
    @ApiOperation("添加")
    public Boolean create(@RequestBody AppDictionary appDictionary) {
        return appDictionaryService.save(appDictionary);
    }

    /**
     * 更新
     *
     * @param appDictionary 字典
     * @return {@link Boolean}
     */
    @PostMapping("{id}")
    @ApiOperation("修改")
    public Boolean update(@RequestBody AppDictionary appDictionary) {
        return appDictionaryService.updateById(appDictionary);
    }

    /**
     * 删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public Boolean delete(@PathVariable Long id) {
        return appDictionaryService.removeById(id);
    }


}
