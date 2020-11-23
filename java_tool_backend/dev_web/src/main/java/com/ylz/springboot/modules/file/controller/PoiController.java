package com.ylz.springboot.modules.file.controller;

import com.ylz.springboot.commons.response.ResponseData;
import com.ylz.springboot.commons.utils.execl.ExcelDataVo;
import com.ylz.springboot.commons.utils.poi.PoiExeclReaderUtils;
import com.ylz.springboot.modules.file.service.PoiServices;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * poi 文件管理
 *
 * @author lhh
 * @Date 2019/11/11 0:22
 */
@RestController
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    private PoiServices poiServices;

    @ApiOperation(value = "Poi文件导入", notes = "Poi文件导入")
    @RequestMapping(value = "/imp", method = RequestMethod.POST)
    public ResponseData importExecl(@RequestParam(value = "excel") MultipartFile excel) throws IOException {
            poiServices.imp(excel);

        return ResponseData.successMessage("导入成功");
    }


    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request,HttpServletResponse response)  throws IOException {

        //List<BusClick> resultList = excelService.getBusClick();

        long t1 = System.currentTimeMillis();

     //   PoiExeclReaderUtils.writeExcel(response, resultList, BusClick.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }


}
