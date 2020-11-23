package com.ylz.springboot.modules.file.service.impl;

import com.alibaba.fastjson.JSON;
import com.ylz.springboot.commons.utils.poi.PoiExeclModel;
import com.ylz.springboot.commons.utils.poi.PoiExeclReaderUtils;
import com.ylz.springboot.modules.file.service.PoiServices;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lhh
 * @Date 2019/11/11 0:20
 */
@Service
public class PoiServicesImpl implements PoiServices {
    /**
     * poi文件上传 没有按照顺序来
     *
     * @param excel
     */
    @Override
    public void imp(MultipartFile excel) {
        List<Object> objects = PoiExeclReaderUtils.readExcel(excel, PoiExeclModel.class, 1, 1);
        System.out.println(JSON.toJSONString(objects)+"---null");
    }
}
