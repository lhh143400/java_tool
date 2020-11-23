package com.ylz.springboot.oauth.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.ylz.springboot.oauth.dao.SysAclMapper;
import com.ylz.springboot.oauth.dao.SysAclModuleMapper;
import com.ylz.springboot.oauth.dao.SysDeptMapper;
import com.ylz.springboot.oauth.dto.AclDto;
import com.ylz.springboot.oauth.dto.AclModuleLevelDto;
import com.ylz.springboot.oauth.dto.DeptLevelDto;
import com.ylz.springboot.oauth.pojo.SysAcl;
import com.ylz.springboot.oauth.pojo.SysAclModule;
import com.ylz.springboot.oauth.pojo.SysDept;
import com.ylz.springboot.oauth.service.SysCoreService;
import com.ylz.springboot.oauth.service.SysTreeService;
import com.ylz.springboot.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SysTreeServiceImpl
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysCoreService sysCoreService;

    @Autowired
    private SysAclMapper sysAclMapper;

    //////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////// 部门树结构 /////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<DeptLevelDto> deptTree() {
        List<SysDept> deptList = sysDeptMapper.selectAllDept();
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        deptList.forEach(dept -> {
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        });
        return deptList2Tree(dtoList);
    }

    private List<DeptLevelDto> deptList2Tree(List<DeptLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        // Map<String, List<DeptLevelDto>>  eg: level -> [dept1, dept2, ...]
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        // 计算第一层级rootList
        List<DeptLevelDto> rootList = Lists.newArrayList();
        deptLevelList.forEach(dto -> {
            levelDeptMap.put(dto.getFullLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getFullLevel())) {
                rootList.add(dto);
            }
        });
        // 按照seq从小到大进行排序
        Collections.sort(rootList, deptSeqComparator);
        // 递归生成树
        // 递归生成树
        transDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    /**
     * level:0  [0 -> 0.1, 0.2]
     * level:0.1  [0.1 -> 0.1.1, 0.1.2]
     * level:0.2  [0.2 -> 0.2.1, 0.2.1, 0.2.3]
     * level:0.1.1  [0.1.1 -> 0.1.1.1, 0.1.1.2]
     *
     * @param deptLevelList 当前层级的部门列表
     * @param level         当前层级
     * @param levelDeptMap  层级和部门列表的对应Map
     */
    private void transDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            // 遍历该层的每个元素
            DeptLevelDto deptLevelDto = deptLevelList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            // 处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                Collections.sort(tempDeptList, deptSeqComparator);
                // 设置下一层部门
                deptLevelDto.setDeptList(tempDeptList);
                // 进入到下一层处理
                transDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    /**
     * 部门的SEQ比较器
     */
    private Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq().compareTo(o2.getSeq());
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////// 部门树结构 /////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// 权限模块树结构 ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> sysAclModuleList = sysAclModuleMapper.selectAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        sysAclModuleList.forEach(sysAclModule -> {
            AclModuleLevelDto dto = AclModuleLevelDto.adapt(sysAclModule);
            dtoList.add(dto);
        });
        return aclModuleList2Tree(dtoList);
    }

    private List<AclModuleLevelDto> aclModuleList2Tree(List<AclModuleLevelDto> aclModuleLevelList) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return Lists.newArrayList();
        }
        // level -> [aclmodule1, aclmodule2, ...] Map<String, List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        // 计算第一层级rootList
        List<AclModuleLevelDto> rootList = Lists.newArrayList();
        aclModuleLevelList.forEach(dto -> {
            levelAclModuleMap.put(dto.getFullLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getFullLevel())) {
                rootList.add(dto);
            }
        });
        // 按照seq从小到大进行排序
        Collections.sort(rootList, aclModuleSeqComparator);
        // 递归生成树
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    private void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList, aclModuleSeqComparator);
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }

    private Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq().compareTo(o2.getSeq());
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////// 权限模块树结构 ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// 权限点树结构 ////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<AclModuleLevelDto> roleTree(String roleId) {
        // 获取当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        // 获取当前角色已分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getAclListByRoleId(roleId);
        // 获取系统所有的权限点
        List<SysAcl> allAclList = sysAclMapper.selectAllAcl();

        List<AclDto> aclDtoList = Lists.newArrayList();

        Set<String> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<String> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        allAclList.forEach(sysAcl -> {
            AclDto dto = AclDto.adapt(sysAcl);
            if (userAclIdSet.contains(sysAcl.getId())) {
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(sysAcl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        });
        return aclListToTree(aclDtoList);
    }

    @Override
    public List<AclModuleLevelDto> functionTree() {
        // 获取当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        // 获取系统所有的权限点
        List<SysAcl> allAclList = sysAclMapper.selectAllAcl();

        List<String> aclModuleIdList = Lists.newArrayList();

        Set<String> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        for (SysAcl sysAcl : allAclList) {
            if (sysAcl.getType() != 1) {
                continue;
            }
            if (userAclIdSet.contains(sysAcl.getId())) {
                aclModuleIdList.add(sysAcl.getAclModuleId());
            }
        }
        if (CollectionUtils.isEmpty(aclModuleIdList)) {
            return Lists.newArrayList();
        }
        return sysAclModuleMapper.findByAclModuleIdList(aclModuleIdList);
    }

    /**
     * 构建权限模块和权限点的树结构
     *
     * @param aclDtoList
     * @return
     */
    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        Multimap<String, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    /**
     * 将权限点绑定到权限模块树上
     *
     * @param aclModuleLevelList
     * @param moduleIdAclMap
     */
    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<String, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, aclSeqComparator);
                dto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq().compareTo(o2.getSeq());
        }
    };


    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// 权限点树结构 ////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
}
