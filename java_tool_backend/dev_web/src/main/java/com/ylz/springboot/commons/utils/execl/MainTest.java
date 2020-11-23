package com.ylz.springboot.commons.utils.execl;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Dreamer-1
 * Date: 2019-03-01
 * Time: 10:13
 * Description: 示例程序入口类
 */
public class MainTest {

    private static Logger logger = Logger.getLogger(MainTest.class.getName());

    public static void main(String[] args) {

        /************** 读取Excel流程 ******************/
        // 设定Excel文件所在路径
        String excelFileName = "D:/writeExample.xlsx";
        // 读取Excel文件内容
        List<ExcelDataVo> readResult = ExcelReader.readExcel(excelFileName);
        System.out.println(JSON.toJSONString(readResult));

        // todo 进行业务操作

        /************** 写入Excel流程 ******************/
        // 创建需要写入的数据列表
        List<ExcelDataVo> dataVOList = new ArrayList<>(2);
        ExcelDataVo dataVO = new ExcelDataVo();
        dataVO.setName("小明");
        dataVO.setAge(18);
        dataVO.setLocation("广州");
        dataVO.setJob("大学生");
        ExcelDataVo dataVO2 = new ExcelDataVo();
        dataVO2.setName("小花");
        dataVO2.setAge(19);
        dataVO2.setLocation("深圳");
        dataVO2.setJob("大学生");
        dataVOList.add(dataVO);
        dataVOList.add(dataVO2);

        // 写入数据到工作簿对象内
        Workbook workbook = ExcelWriter.exportData(dataVOList);

        // 以文件的形式输出工作簿对象
        FileOutputStream fileOut = null;
        try {
            String exportFilePath = "D:/writeExample.xlsx";
            File exportFile = new File(exportFilePath);
            if (!exportFile.exists()) {
                exportFile.createNewFile();
            }

            fileOut = new FileOutputStream(exportFilePath);
            workbook.write(fileOut);
            fileOut.flush();
        } catch (Exception e) {
            logger.warning("输出Excel时发生错误，错误原因：" + e.getMessage());
        } finally {
            try {
                if (null != fileOut) {
                    fileOut.close();
                }
                if (null != workbook) {
                    workbook.close();
                }
            } catch (IOException e) {
                logger.warning("关闭输出流时发生错误，错误原因：" + e.getMessage());
            }
        }

    }

}
