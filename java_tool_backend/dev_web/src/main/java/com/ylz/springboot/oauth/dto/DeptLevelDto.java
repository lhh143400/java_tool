package com.ylz.springboot.oauth.dto;

import com.google.common.collect.Lists;
import com.ylz.springboot.oauth.pojo.SysDept;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * DeptLevelDto
 *
 * @author: Chris
 * @time: 2019.02.15
 */
@Data
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept sysDept) {
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(sysDept, dto);
        return dto;
    }

}
