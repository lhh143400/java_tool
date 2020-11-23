package com.ylz.springboot.oauth.service.impl;

import com.ylz.springboot.commons.exception.DeleteOPException;
import com.ylz.springboot.commons.exception.DuplicationException;
import com.ylz.springboot.commons.exception.NoResultException;
import com.ylz.springboot.commons.exception.UpdateOPException;
import com.ylz.springboot.oauth.common.RequestHolder;
import com.ylz.springboot.oauth.dao.SysDeptMapper;
import com.ylz.springboot.oauth.dao.SysUserMapper;
import com.ylz.springboot.oauth.pojo.SysDept;
import com.ylz.springboot.oauth.service.SysDeptService;
import com.ylz.springboot.oauth.service.SysLogService;
import com.ylz.springboot.oauth.vo.DeptVo;
import com.ylz.springboot.utils.BeanValidatorUtil;
import com.ylz.springboot.utils.IpUtil;
import com.ylz.springboot.utils.KeyGenerateUtil;
import com.ylz.springboot.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * SysDeptServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public void save(DeptVo deptVo) {
        BeanValidatorUtil.check(deptVo);
        if (checkExist(deptVo.getId(), deptVo.getParentId(), deptVo.getName())) {
            throw new DuplicationException("同一层级下不能存在相同名称的部门");
        }
        SysDept dept = SysDept.builder()
                // 生成部门主键
                .id(KeyGenerateUtil.generateOracleId())
                .name(deptVo.getName()).parentId(deptVo.getParentId())
                .seq(deptVo.getSeq()).remark(deptVo.getRemark())
                .build();
        // 计算当前部门层级
        dept.setFullLevel(LevelUtil.calculateLevel(getParentLevel(deptVo.getParentId()), deptVo.getParentId()));
        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());

        sysDeptMapper.insertSelective(dept);
        sysLogService.saveDeptLog(null, dept);
    }

    @Override
    public void update(DeptVo deptVo) {
        BeanValidatorUtil.check(deptVo);
        if (checkExist(deptVo.getId(), deptVo.getParentId(), deptVo.getName())) {
            throw new DuplicationException("同一层级下不能存在相同名称的部门");
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptVo.getId());
        if (before == null) {
            throw new NoResultException("待更新的部门不存在");
        }

        SysDept after = SysDept.builder()
                // 生成部门主键
                .id(deptVo.getId())
                .name(deptVo.getName()).parentId(deptVo.getParentId())
                .seq(deptVo.getSeq()).remark(deptVo.getRemark())
                .build();
        // 计算当前部门层级
        after.setFullLevel(LevelUtil.calculateLevel(getParentLevel(deptVo.getParentId()), deptVo.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        // 递归更新部门及其子部门信息
        recursiveUpdateWithChild(before, after);
        sysLogService.saveDeptLog(before, after);

    }

    /**
     * 递归更新部门及其子部门（如果部门level有变化，需要更新子部门的level信息）
     *
     * @param before
     * @param after
     */
    @Transactional(rollbackFor = UpdateOPException.class)
    public void recursiveUpdateWithChild(SysDept before, SysDept after) {
        try {
            String newLevelPrefix = after.getFullLevel();
            String oldLevelPrefix = before.getFullLevel();
            // 如果更新后的部门层级与更新前的部门层级发生变化，需要更新子部门的层级
            if (!newLevelPrefix.equals(oldLevelPrefix)) {
                List<SysDept> deptList = sysDeptMapper.selectChildDeptByLevel(before.getFullLevel(), before.getId());
                if (CollectionUtils.isNotEmpty(deptList)) {
                    deptList.forEach(beforeDept -> {
                        if (beforeDept.getFullLevel().indexOf(oldLevelPrefix) == 0) {
                            String newLevel = newLevelPrefix + beforeDept.getFullLevel().substring(oldLevelPrefix.length());
                            // 构造更新的Dept，子Dept只做level的更新
                            SysDept afterDept = SysDept.builder()
                                    .id(beforeDept.getId())
                                    .name(beforeDept.getName()).parentId(beforeDept.getParentId())
                                    .seq(beforeDept.getSeq()).remark(beforeDept.getRemark())
                                    .build();
                            afterDept.setFullLevel(newLevel);
                            afterDept.setOperator(RequestHolder.getCurrentUser().getUsername());
                            afterDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                            afterDept.setOperateTime(new Date());

                            // 递归更新子部门
                            recursiveUpdateWithChild(beforeDept, afterDept);
                        }
                    });
                    // sysDeptMapper.batchUpdateLevel(deptList);
                }
            }
            sysDeptMapper.updateByPrimaryKeySelective(after);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UpdateOPException("更新部门操作出错, 错误原因: " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(id);
        if (dept == null) {
            throw new NoResultException("待删除的部门不存在");
        }
        if (sysDeptMapper.countByParentId(dept.getId()) > 0) {
            throw new DeleteOPException("当前部门下面有子部门，无法删除");
        }
        if (sysUserMapper.countByDeptId(dept.getId()) > 0) {
            throw new DeleteOPException("当前部门下有用户，无法删除");
        }
        sysDeptMapper.deleteByPrimaryKey(id);
        sysLogService.saveDeptLog(dept, null);
    }

    /**
     * 判断当前层级是否有同名的部门
     *
     * @param deptId
     * @param parentId
     * @param deptName
     * @return
     */
    private boolean checkExist(String deptId, String parentId, String deptName) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getParentLevel(String deptId) {
        // 查询是否有父节点
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getFullLevel();
    }

}
