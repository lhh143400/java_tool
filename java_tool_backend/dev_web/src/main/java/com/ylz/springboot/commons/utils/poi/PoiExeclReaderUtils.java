package com.ylz.springboot.commons.utils.poi;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * poi Execl工具
 *
 * @author lhh
 * @Date 2019/11/10 10:36
 */
public class PoiExeclReaderUtils {
    /**
     * 日志打印类
     */
    private static Logger logger = LoggerFactory.getLogger(PoiExeclReaderUtils.class);

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";
    private static final int INDEX=99999;
    private static final int SHEET_NO=0;

    /**
     * 读取Excel文件内容
     *  @param excel 上传的Excel文件
     * @param clazz
     * @param sheetNo 工作表 <=0  为读取所有 sheet  >0  为指定读取工作表
     * @param rowNo  从哪行 开始读取数据
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<Object> readExcel(MultipartFile excel, Class<?> clazz, int sheetNo, int rowNo) {
        Workbook   workbook = getWorkbook(excel);
        TreeMap classMap=parseExcelAnnotation(clazz);
        if(MapUtils.isEmpty(classMap)){
            throw new RuntimeException("ExcelPoiProperty no index");
        }
        return  parseExcelData(workbook, classMap, clazz,sheetNo,rowNo);
    }


    /**
     * 根据文件后缀名类型获取对应的工作簿对象（第一步）
     *
     *
     * @param excel 上传的文件
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(MultipartFile excel){
        Workbook workbook = null;
        // 获取Excel后缀名
        String fileName = excel.getOriginalFilename();
        if (fileName == null || fileName.isEmpty() || fileName.lastIndexOf(".") < 0) {
            logger.error("解析Excel失败，因为获取到的Excel文件名非法！");
            return null;
        }
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());


        try {
            if (fileType.equalsIgnoreCase(XLS)) {
                workbook = new HSSFWorkbook(excel.getInputStream());
            } else if (fileType.equalsIgnoreCase(XLSX)) {
                workbook = new XSSFWorkbook(excel.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("解析Excel失败，文件名：" + excel.getOriginalFilename() + " 错误信息：" + e.getMessage());
        }finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
            } catch (Exception e) {
                logger.error("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
        return workbook;
    }


    /**
     * 解析 实体类 注解数据 （第二步）
     *
     * @param clazz Excel工作簿对象
     * @return 解析结果
     */
    private static TreeMap parseExcelAnnotation(Class<?> clazz) {
        TreeMap<Integer, List<Field>> classMap = new TreeMap<>();
        List<Field> fields = Stream.of(clazz.getDeclaredFields()).collect(Collectors.toList());
        fields.forEach(
                field -> {
                    ExcelPoiProperty annotation = field.getAnnotation(ExcelPoiProperty.class);
                    if (annotation != null) {
                        int index = annotation.index();
                        if (index == INDEX) {
                            //return起到的作用和continue是相同的 语法
                            return;
                        }
                        if (!classMap.containsKey(index)) {
                            classMap.put(index, new ArrayList<>());
                        }
                        //设置私有变量可以访问
                        field.setAccessible(true);
                        classMap.get(index).add(field);
                    }
                }
        );
      return classMap;
    }

    /**
     * 解析execl 数据
     *
     * @param workbook 工作簿
     * @param reflectionMap 类中的字段
     * @param cls  类
     * @param sheetNo 工作表 <=0  为读取所有 sheet  >0  为指定读取工作表
     * @param rowNo  从哪行 开始读取数据
     * @return  execl里的数据
     */
    public static <T> List<T> parseExcelData(Workbook workbook, TreeMap<Integer, List<Field>> reflectionMap, Class<T> cls,int sheetNo ,int rowNo){
        List<T> dataList = new ArrayList<>();

        int startSheetNum=0;
        int endSheetNum=workbook.getNumberOfSheets();
       //sheetNo 工作表 <=-1 为读取所有 sheet  >=0  为指定读取工作表
       if(sheetNo<=SHEET_NO){
           startSheetNum=0;
           endSheetNum=workbook.getNumberOfSheets();
       }else{
           startSheetNum=sheetNo-1;
           endSheetNum=sheetNo;
       }


        // 解析sheet
        for (int sheetNum = startSheetNum; sheetNum < endSheetNum; sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }


            int startRow = rowNo;
            int endRow = sheet.getLastRowNum();

            // 解析每一行的数据，构造数据对象
            System.out.println(sheet.getFirstRowNum()+"行数");

             //行
            for (int i = startRow; i <= endRow; i++) {

                //忽略空白行
                Row row = sheet.getRow(i);
                if (null == row) {
                    continue;
                }

                try {
                    T t = cls.newInstance();
                    //判断是否为空白行
                    boolean allBlank = true;

                    //列
                    for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                        if (reflectionMap.containsKey(j)) {
                            Cell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            if (StringUtils.isNotBlank(cellValue)) {
                                allBlank = false;
                            }
                            List<Field> fieldList = reflectionMap.get(j);
                            fieldList.forEach(
                                    x -> {
                                        try {
                                            handleField(t, cellValue, x);
                                        } catch (Exception e) {
                                            logger.error(String.format("reflect field:%s value:%s exception!", x.getName(), cellValue), e);
                                        }
                                    }
                            );
                        }
                    }
                    if (!allBlank) {
                        dataList.add(t);
                    } else {
                        logger.error(String.format("row:%s is blank ignore!", i));
                    }
                } catch (Exception e) {
                    logger.info(String.format("parse row:%s exception!", i), e);
                }

            }

        }
        return dataList;
    }


    /**
     * 获取单元格数据
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
            } else {
                return new BigDecimal(cell.getNumericCellValue()).toString();
            }
        } else if (cell.getCellType() == CellType.STRING) {
            return StringUtils.trimToEmpty(cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            return StringUtils.trimToEmpty(cell.getCellFormula());
        } else if (cell.getCellType() == CellType.BLANK) {
            return "";
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.ERROR) {
            return "ERROR";
        } else {
            return cell.toString().trim();
        }

    }

    /**
     * 处理字段
     */
    private static <T> void handleField(T t, String value, Field field) throws Exception {
        Class<?> type = field.getType();
        if (type == null || type == void.class || StringUtils.isBlank(value)) {
            return;
        }
        if (type == Object.class) {
            field.set(t, value);
            //数字类型
        } else if (type.getSuperclass() == null || type.getSuperclass() == Number.class) {
            if (type == int.class || type == Integer.class) {
                field.set(t, NumberUtils.toInt(value));
            } else if (type == long.class || type == Long.class) {
                field.set(t, NumberUtils.toLong(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(t, NumberUtils.toByte(value));
            } else if (type == short.class || type == Short.class) {
                field.set(t, NumberUtils.toShort(value));
            } else if (type == double.class || type == Double.class) {
                field.set(t, NumberUtils.toDouble(value));
            } else if (type == float.class || type == Float.class) {
                field.set(t, NumberUtils.toFloat(value));
            } else if (type == char.class || type == Character.class) {
                field.set(t, CharUtils.toChar(value));
            } else if (type == boolean.class) {
                field.set(t, BooleanUtils.toBoolean(value));
            } else if (type == BigDecimal.class) {
                field.set(t, new BigDecimal(value));
            }
        } else if (type == Boolean.class) {
            field.set(t, BooleanUtils.toBoolean(value));
        } else if (type == Date.class) {
            //
            field.set(t, value);
        } else if (type == String.class) {
            field.set(t, value);
        } else {
            Constructor<?> constructor = type.getConstructor(String.class);
            field.set(t, constructor.newInstance(value));
        }
    }

}
