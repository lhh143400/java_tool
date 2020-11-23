package com.ylz.springboot.oauth.controller;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.oauth.dto.DeptLevelDto;
import com.ylz.springboot.oauth.service.SysDeptService;
import com.ylz.springboot.oauth.service.SysTreeService;
import com.ylz.springboot.oauth.vo.DeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SysDeptController
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Api(value = "部门管理模块", description = "部门管理模块包括对部门的增删改查")
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    @ApiOperation(value = "新增部门", notes = "新增部门")
    @RequestMapping("/save")
    public ResponseData saveDept(DeptVo deptVo) {
        sysDeptService.save(deptVo);
        return ResponseData.successMessage("新增部门成功");
    }

    @ApiOperation(value = "获取部门树结构", notes = "获取部门树结构")
    @RequestMapping("/tree")
    public ResponseData tree() {
        return ResponseData.success(sysTreeService.deptTree());
    }

    @ApiOperation(value = "更新部门", notes = "更新部门")
    @RequestMapping("/update")
    public ResponseData updateDept(DeptVo deptVo) {
        sysDeptService.update(deptVo);
        return ResponseData.successMessage("更新部门成功");
    }

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @RequestMapping("/delete")
    public ResponseData delete(@RequestParam("id") String id) {
        sysDeptService.delete(id);
        return ResponseData.successMessage("删除部门成功");
    }

}
