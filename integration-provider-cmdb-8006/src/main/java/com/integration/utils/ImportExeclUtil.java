package com.integration.utils;

import com.integration.entity.IsNeeded;
import com.integration.entity.Type;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
* @Package: com.integration.utils
* @ClassName: ImportExeclUtil
* @Author: ztl
* @Date: 2021-08-09
* @Version: 1.0
* @description: 导入表格工具类
*/
public class ImportExeclUtil {
	private static final Logger logger = LoggerFactory.getLogger(ImportExeclUtil.class);
	/**
	 * 总行数
	 */
    private static int totalRows = 0;
    /**
     * 总列数
     */
    private static int totalCells = 0;
    /**
     * 错误信息
     */
    private static String errorInfo;
    
    /** 无参构造方法 */
    public ImportExeclUtil()
    {
    }
    
    public static int getTotalRows()
    {
        return totalRows;
    }
    
    public static int getTotalCells()
    {
        return totalCells;
    }
    
    public static String getErrorInfo()
    {
        return errorInfo;
    }


    /**
     * 
     * 根据流读取Excel文件
     * 
     * 
     * @param inputStream
     * @param isExcel2003
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<List<String>> read(InputStream inputStream, boolean isExcel2003)
        throws IOException
    {
        
        List<List<String>> dataLst = null;
        
        /** 根据版本选择创建Workbook的方式 */
        Workbook wb = null;
        
        if (isExcel2003)
        {
            wb = new HSSFWorkbook(inputStream);
        }
        else
        {
            wb = new XSSFWorkbook(inputStream);
        }
        dataLst = readDate(wb);
        
        return dataLst;
    }
    
    /**
     * 
     * 读取数据
     * 
     * @param wb
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static List<List<String>> readDate(Workbook wb)
    {
        
        List<List<String>> dataLst = new ArrayList<List<String>>();
        
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        
        /** 得到Excel的行数 */
        totalRows = sheet.getPhysicalNumberOfRows();
        
        /** 得到Excel的列数 */
        if (totalRows >= 1 && sheet.getRow(0) != null)
        {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        
        /** 循环Excel的行 */
        for (int r = 0; r < totalRows; r++)
        {
            Row row = sheet.getRow(r);
            if (row == null)
            {
                continue;
            }
            
            List<String> rowLst = new ArrayList<String>();
            
            /** 循环Excel的列 */
            for (int c = 0; c < getTotalCells(); c++)
            {
                
                Cell cell = row.getCell(c);
                String cellValue = "";
                
                if (null != cell)
                {
                    // 以下是判断数据的类型
                    switch (cell.getCellType())
                    {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            // 数字
                            double cellValues = cell.getNumericCellValue();
                            NumberFormat nf = NumberFormat.getInstance();
                            String s = nf.format(cellValues);
                            if (s.indexOf(",") >= 0) {
                                //这种方法对于自动加".0"的数字可直接解决
                                //但如果是科学计数法的数字就转换成了带逗号的，
                                // 例如：12345678912345的科学计数法是1.23457E+13，
                                // 经过这个格式化后就变成了字符串“12,345,678,912,345”，
                                // 这也并不是想要的结果，所以要将逗号去掉
                                cellValue = s.replace(",", "");
                            }else{
                                cellValue=s;
                            }
                            break;
                        
                        case HSSFCell.CELL_TYPE_STRING:
                            // 字符串
                            cellValue = cell.getStringCellValue();
                            break;
                        
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        
                        case HSSFCell.CELL_TYPE_FORMULA:
                            // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;
                        
                        case HSSFCell.CELL_TYPE_BLANK:
                            // 空值
                            cellValue = "";
                            break;
                        
                        case HSSFCell.CELL_TYPE_ERROR:
                            // 故障
                            cellValue = "非法字符";
                            break;
                        
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                
                rowLst.add(cellValue);
            }
            
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        
        return dataLst;
    }
    
    /**
     * 
     * 按指定坐标读取实体数据
     * <按顺序放入带有注解的实体成员变量中>
     * 
     * @param wb 工作簿
     * @param t 实体
     * @param in 输入流
     * @param integers 指定需要解析的坐标
     * @return T 相应实体
     * @throws IOException
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unused")
    public static <T> T readDateT(Workbook wb, T t, InputStream in, Integer[]... integers)
        throws IOException, Exception
    {
        // 获取该工作表中的第一个工作表
        Sheet sheet = wb.getSheetAt(0);
        
        // 成员变量的值
        Object entityMemberValue = "";
        
        // 所有成员变量
        Field[] fields = t.getClass().getDeclaredFields();
        // 列开始下标
        int startCell = 0;
        
        /** 循环出需要的成员 */
        for (int f = 0; f < fields.length; f++)
        {
            
            fields[f].setAccessible(true);
            String fieldName = fields[f].getName();
            boolean fieldHasAnno = fields[f].isAnnotationPresent(IsNeeded.class);
            // 有注解
            if (fieldHasAnno)
            {
                IsNeeded annotation = fields[f].getAnnotation(IsNeeded.class);
                boolean isNeeded = annotation.isNeeded();
                
                // Excel需要赋值的列
                if (isNeeded)
                {
                    
                    // 获取行和列
                    int x = integers[startCell][0] - 1;
                    int y = integers[startCell][1] - 1;
                    
                    Row row = sheet.getRow(x);
                    Cell cell = row.getCell(y);
                    
                    if (row == null)
                    {
                        continue;
                    }
                    
                    // Excel中解析的值
                    String cellValue = getCellValue(cell);
                    // 需要赋给成员变量的值
                    entityMemberValue = getEntityMemberValue(entityMemberValue, fields, f, cellValue);
                    // 赋值
                    PropertyUtils.setProperty(t, fieldName, entityMemberValue);
                    // 列的下标加1
                    startCell++;
                }
            }
            
        }
        
        return t;
    }
    
    /**
     * 
     * 读取列表数据 
     * <按顺序放入带有注解的实体成员变量中>
     * 
     * @param wb 工作簿
     * @param t 实体
     * @param beginLine 开始行数
     * @param totalcut 结束行数减去相应行数
     * @return List<T> 实体列表
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> readDateListT(Workbook wb, T t, int beginLine, int totalcut)
        throws Exception
    {
        List<T> listt = new ArrayList<T>();
        
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        
        /** 得到Excel的行数 */
        totalRows = sheet.getPhysicalNumberOfRows();
        
        /** 得到Excel的列数 */
        if (totalRows >= 1 && sheet.getRow(0) != null)
        {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        
        /** 循环Excel的行 */
        for (int r = beginLine - 1; r < totalRows - totalcut; r++)
        {
            Object newInstance = t.getClass().newInstance();
            Row row = sheet.getRow(r);
            if (row == null)
            {
                continue;
            }
            
            // 成员变量的值
            Object entityMemberValue = "";
            
            // 所有成员变量
            Field[] fields = t.getClass().getDeclaredFields();
            // 列开始下标
            int startCell = 0;
            
            for (int f = 0; f < fields.length; f++)
            {
                
                fields[f].setAccessible(true);
                String fieldName = fields[f].getName();
                boolean fieldHasAnno = fields[f].isAnnotationPresent(IsNeeded.class);
                // 有注解
                if (fieldHasAnno)
                {
                    IsNeeded annotation = fields[f].getAnnotation(IsNeeded.class);
                    boolean isNeeded = annotation.isNeeded();
                    // Excel需要赋值的列
                    if (isNeeded)
                    {
                        Cell cell = row.getCell(startCell);
                        String cellValue = getCellValue(cell);
                        entityMemberValue = getEntityMemberValue(entityMemberValue, fields, f, cellValue);
                        // 赋值
                        PropertyUtils.setProperty(newInstance, fieldName, entityMemberValue);
                        // 列的下标加1
                        startCell++;
                    }
                }
                
            }
            
            listt.add((T)newInstance);
        }
        
        return listt;
    }
    
    /**
     * 
     * 根据Excel表格中的数据判断类型得到值
     * 
     * @param cell
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static String getCellValue(Cell cell)
    {
        String cellValue = "";
        
        if (null != cell)
        {
            // 以下是判断数据的类型
            switch (cell.getCellType())
            {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    // 数字
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell))
                    {
                        Date theDate = cell.getDateCellValue();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = dff.format(theDate);
                    }
                    else
                    {
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    // 字符串
                    double cellValues = cell.getNumericCellValue();
                    NumberFormat nf = NumberFormat.getInstance();
                    String s = nf.format(cellValues);
                    if (s.indexOf(",") >= 0) {
                        //这种方法对于自动加".0"的数字可直接解决
                        //但如果是科学计数法的数字就转换成了带逗号的，
                        // 例如：12345678912345的科学计数法是1.23457E+13，
                        // 经过这个格式化后就变成了字符串“12,345,678,912,345”，
                        // 这也并不是想要的结果，所以要将逗号去掉
                        cellValue = s.replace(",", "");
                    }else{
                        cellValue=s;
                    }
                    break;
                
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                
                case HSSFCell.CELL_TYPE_FORMULA:
                    // 公式
                    cellValue = cell.getCellFormula() + "";
                    break;
                
                case HSSFCell.CELL_TYPE_BLANK:
                    // 空值
                    cellValue = "";
                    break;
                
                case HSSFCell.CELL_TYPE_ERROR:
                    // 故障
                    cellValue = "非法字符";
                    break;
                
                default:
                    cellValue = "未知类型";
                    break;
            }
            
        }
        return cellValue;
    }
    
    /**
     * 
     * 根据实体成员变量的类型得到成员变量的值
     * 
     * @param realValue
     * @param fields
     * @param f
     * @param cellValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static Object getEntityMemberValue(Object realValue, Field[] fields, int f, String cellValue)
    {
        String type = fields[f].getType().getName();
        switch (type)
        {
            case "char":
            case "java.lang.Character":
            case "java.lang.String":
                realValue = cellValue;
                break;
            case "java.util.Date":
                realValue = StringUtils.isBlank(cellValue) ? null : DateUtil.strToDate(cellValue, DateUtil.YYYY_MM_DD);
                break;
            case "java.lang.Integer":
                realValue = StringUtils.isBlank(cellValue) ? null : Integer.valueOf(cellValue);
                break;
            case "int":
            case "float":
            case "double":
            case "java.lang.Double":
            case "java.lang.Float":
            case "java.lang.Long":
            case "java.lang.Short":
            case "java.math.BigDecimal":
                realValue = StringUtils.isBlank(cellValue) ? null : new BigDecimal(cellValue);
                break;
            default:
                break;
        }
        return realValue;
    }
    
    /**
     * 
     * 根据路径或文件名选择Excel版本
     * 
     * 
     * @param filePathOrName
     * @param in
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static Workbook chooseWorkbook(String filePathOrName, InputStream in)
        throws IOException
    {
        /** 根据版本选择创建Workbook的方式 */
        Workbook wb = null;
        boolean isExcel2003 = ExcelVersionUtil.isExcel2003(filePathOrName);
        
        if (isExcel2003)
        {
            wb = new HSSFWorkbook(in);
        }
        else
        {
            wb = new XSSFWorkbook(in);
        }
        
        return wb;
    }


    static class ExcelVersionUtil
    {
        
        /**
         * 
         * 是否是2003的excel，返回true是2003
         * 
         * 
         * @param filePath
         * @return
         * @see [类、类#方法、类#成员]
         */
        public static boolean isExcel2003(String filePath)
        {
            return filePath.matches("^.+\\.(?i)(xls)$");
            
        }
        
        /**
         * 
         * 是否是2007的excel，返回true是2007
         * 
         * 
         * @param filePath
         * @return
         * @see [类、类#方法、类#成员]
         */
        public static boolean isExcel2007(String filePath)
        {
            return filePath.matches("^.+\\.(?i)(xlsx)$");
            
        }
        
    }
    
    public static class DateUtil
    {
        
        // ======================日期格式化常量=====================//
        
        public static final String YYYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";
        
        public static final String YYYY_MM_DD = "yyyy-MM-dd";
        
        public static final String YYYY_MM = "yyyy-MM";
        
        public static final String YYYY = "yyyy";
        
        public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        
        public static final String YYYYMMDD = "yyyyMMdd";
        
        public static final String YYYYMM = "yyyyMM";
        
        public static final String YYYYMMDDHHMMSS_1 = "yyyy/MM/dd HH:mm:ss";
        
        public static final String YYYY_MM_DD_1 = "yyyy/MM/dd";
        
        public static final String YYYY_MM_1 = "yyyy/MM";
        
        /**
         * 
         * 自定义取值，Date类型转为String类型
         * 
         * @param date 日期
         * @param pattern 格式化常量
         * @return
         * @see [类、类#方法、类#成员]
         */
        public static String dateToStr(Date date, String pattern)
        {
            SimpleDateFormat format = null;
            
            if (null == date)
            {
                return null;
            }
            format = new SimpleDateFormat(pattern, Locale.getDefault());
            
            return format.format(date);
        }
        
        /**
         * 将字符串转换成Date类型的时间
         * <hr>
         * 
         * @param s 日期类型的字符串<br>
         *            datePattern :YYYY_MM_DD<br>
         * @return java.util.Date
         */
        public static Date strToDate(String s, String pattern)
        {
            if (s == null)
            {
                return null;
            }
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try
            {
                date = sdf.parse(s);
            }
            catch (ParseException e)
            {
                logger.error(e.getMessage());
            }
            return date;
        }
    }
    
    /**
     * 
     * 根据流读取Excel文件,导入类数据封装
     * 
     * 
     * @param inputStream
     * @param isExcel2003
     * @return
     * @throws IOException 
     * @see [类、类#方法、类#成员]
     */
    public static List<List<String>> readType(InputStream inputStream, boolean isExcel2003,Type type) throws IOException
    {
               
    	List<List<String>> dataLst = null;
        /** 根据版本选择创建Workbook的方式 */
        Workbook wb = null;
        
        if (isExcel2003)
        {
            wb = new HSSFWorkbook(inputStream);
        }
        else
        {
            wb = new XSSFWorkbook(inputStream);
        }
        dataLst = readDate(wb,type);
        
        return dataLst;
    }
    
    
    /**
     * 
     * 读取数据，导入类数据封装
     * 
     * @param wb
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<List<String>> readDate(Workbook wb,Type type)
    {
        
        List<List<String>> dataLst = new ArrayList<List<String>>();
        Sheet sheet = null;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			if(wb.getSheetName(i).equals(type.getCi_type_mc())){
                /** 得到shell */
				sheet = wb.getSheetAt(i);
			}
		}
        if(sheet==null){
        	return null;
        }
       
        /** 得到Excel的行数 */
        totalRows = sheet.getPhysicalNumberOfRows();
        
        /** 得到Excel的列数 */
        if (totalRows >= 1 && sheet.getRow(0) != null)
        {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        
        /** 循环Excel的行 */
        for (int r = 0; r < totalRows; r++)
        {
            Row row = sheet.getRow(r);
            if (row == null)
            {
                continue;
            }
            
            List<String> rowLst = new ArrayList<String>();
            
            /** 循环Excel的列 */
            for (int c = 0; c < getTotalCells(); c++)
            {
                
                Cell cell = row.getCell(c);
                String cellValue = "";
                
                if (null != cell)
                {
                    // 以下是判断数据的类型
                    switch (cell.getCellType())
                    {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            // 数字
                           double cellValues = cell.getNumericCellValue();
                            NumberFormat nf = NumberFormat.getInstance();
                            String s = nf.format(cellValues);
                            if (s.indexOf(",") >= 0) {
                                 //这种方法对于自动加".0"的数字可直接解决
                                //但如果是科学计数法的数字就转换成了带逗号的，
                                // 例如：12345678912345的科学计数法是1.23457E+13，
                                // 经过这个格式化后就变成了字符串“12,345,678,912,345”，
                                // 这也并不是想要的结果，所以要将逗号去掉
                                cellValue = s.replace(",", "");
                            }else{
                                cellValue=s;
                            }
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            // 字符串
                            cellValue = cell.getStringCellValue();
                            break;
                        
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        
                        case HSSFCell.CELL_TYPE_FORMULA:
                            // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;
                        
                        case HSSFCell.CELL_TYPE_BLANK:
                            // 空值
                            cellValue = "";
                            break;
                        
                        case HSSFCell.CELL_TYPE_ERROR:
                            // 故障
                            cellValue = "非法字符";
                            break;
                        
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                
                rowLst.add(cellValue);
            }
            
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        
        return dataLst;
    }

    /**
     * 解析excel，返回list
     * @param file
     * @return
     */
    public static List getDataList(MultipartFile file){
        List dataList = new ArrayList();

        //判断Excel文件是否有内容
        boolean notNull=false;
        String filename=file.getOriginalFilename();
        if (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NumberFormatException("上传文件格式不正确！");
        }
        //判断Excel文件的版本
        boolean isExcel2003=true;
        if (filename.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        Workbook wb = null;
        try {
            InputStream fis=file.getInputStream();
            if (isExcel2003) {
                wb = new HSSFWorkbook(fis);
            } else  {
                wb = new XSSFWorkbook(fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 总共有多少张sheet表
        int sheetnum = wb.getNumberOfSheets();

        for (int i = 0; i < sheetnum; i++) {
            Sheet sheet = wb.getSheetAt(i);
            // 表头数据
            Row namerow = sheet.getRow(0);

            // 数据
            Row headrow = sheet.getRow(1);

            if (null != headrow) {

                // 总行数
                int rowNum = sheet.getPhysicalNumberOfRows();

                // 总列数
                int colNum = headrow.getPhysicalNumberOfCells();

                // 判断工作表是否为空
                if (rowNum == 0) {
                    continue;
                }

            //第一行是列名，所以不读
                int firstRowIndex = sheet.getFirstRowNum()+1;
                int lastRowIndex = sheet.getLastRowNum();
                int lastCellIndex = sheet.getRow(0).getLastCellNum();
                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                    //遍历行
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        List colList = new ArrayList();
                        int firstCellIndex = row.getFirstCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                            //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                String cellValue = "";
                                //判断数据的类型
                                switch (cell.getCellType()){
                                    case Cell.CELL_TYPE_NUMERIC:
                                        //数字
                                        double cellValues = cell.getNumericCellValue();
                                        NumberFormat nf = NumberFormat.getInstance();
                                        String s = nf.format(cellValues);
                                        if (s.indexOf(",") >= 0) {
                                            //这种方法对于自动加".0"的数字可直接解决
                                            //但如果是科学计数法的数字就转换成了带逗号的，
                                            // 例如：12345678912345的科学计数法是1.23457E+13，
                                            // 经过这个格式化后就变成了字符串“12,345,678,912,345”，
                                            // 这也并不是想要的结果，所以要将逗号去掉
                                            cellValue = s.replace(",", "");
                                        }else{
                                            cellValue=s;
                                        }
                                        break;
                                    case Cell.CELL_TYPE_STRING:
                                        //字符串
                                        cellValue = String.valueOf(cell.getStringCellValue());
                                        break;
                                    case Cell.CELL_TYPE_BOOLEAN:
                                        //Boolean
                                        cellValue = String.valueOf(cell.getBooleanCellValue());
                                        break;
                                    case Cell.CELL_TYPE_FORMULA:
                                        //公式
                                        cellValue = String.valueOf(cell.getCellFormula());
                                        break;
                                    case Cell.CELL_TYPE_BLANK:
                                        //空值
                                        cellValue = "";
                                        break;
                                    case Cell.CELL_TYPE_ERROR:
                                        //故障
                                        cellValue = "非法字符";
                                        break;
                                    default:
                                        cellValue = "未知类型";
                                        break;
                                }
                                colList.add(cellValue);
                            }else{
                                colList.add("");
                            }
                        }
                        dataList.add(colList);
                    }
                }
            }
        }
        return dataList;
    }

    public static String stringDateProcess(Cell cell){
        String result = new String();
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
            // 处理日期格式、时间格式
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {
                // 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if ("General".equals(temp)) {
                format.applyPattern("#.00");
            }
            result = format.format(value);
        }
        return result;
    }

    /**
     * 根据sheet名获取该表格的表头
     *
     * @param wb
     * @param type
     * @return
     */
    public static List<String> readExcellHeader(Workbook wb,String type)
    {
        Sheet sheet = null;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            if(wb.getSheetName(i).equals(type)){
                /** 得到shell */
                sheet = wb.getSheetAt(i);
            }
        }
        if(sheet==null){
            return null;
        }
        /** 得到Excel的行数 */
        totalRows = sheet.getPhysicalNumberOfRows();
        /** 得到Excel的列数 */
        if (totalRows >= 1 && sheet.getRow(0) != null)
        {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        Row row = sheet.getRow(0);
        List<String> rowLst = new ArrayList<String>();

        /** 循环Excel的列 */
        for (int c = 0; c < getTotalCells(); c++)
        {

            Cell cell = row.getCell(c);
            String cellValue = "";

            if (null != cell)
            {
                // 以下是判断数据的类型
                switch (cell.getCellType())
                {
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        // 数字
                        double cellValues = cell.getNumericCellValue();
                        NumberFormat nf = NumberFormat.getInstance();
                        String s = nf.format(cellValues);
                        if (s.indexOf(",") >= 0) {
                            //这种方法对于自动加".0"的数字可直接解决
                            //但如果是科学计数法的数字就转换成了带逗号的，
                            // 例如：12345678912345的科学计数法是1.23457E+13，
                            // 经过这个格式化后就变成了字符串“12,345,678,912,345”，
                            // 这也并不是想要的结果，所以要将逗号去掉
                            cellValue = s.replace(",", "");
                        }else{
                            cellValue=s;
                        }
                        break;

                    case HSSFCell.CELL_TYPE_STRING:
                        // 字符串
                        cellValue = cell.getStringCellValue();
                        break;

                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        // Boolean
                        cellValue = cell.getBooleanCellValue() + "";
                        break;

                    case HSSFCell.CELL_TYPE_FORMULA:
                        // 公式
                        cellValue = cell.getCellFormula() + "";
                        break;

                    case HSSFCell.CELL_TYPE_BLANK:
                        // 空值
                        cellValue = "";
                        break;

                    case HSSFCell.CELL_TYPE_ERROR:
                        // 故障
                        cellValue = "非法字符";
                        break;

                    default:
                        cellValue = "未知类型";
                        break;
                }
            }

            rowLst.add(cellValue);
        }
        return rowLst;
    }
    public static List<Map<Object, Object>> readExcelDate(Workbook wb, String name, int pageNum, int pageSize) {
        List<Map<Object, Object>> dataLst = new ArrayList<Map<Object, Object>>();
        Sheet sheet = null;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            if(wb.getSheetName(i).equals(name)){
                /** 得到shell */
                sheet = wb.getSheetAt(i);
            }
        }
        if(sheet==null){
            return null;
        }
        /** 得到Excel的行数 */
        totalRows = sheet.getPhysicalNumberOfRows();

        /** 得到Excel的列数 */
        if (totalRows >= 1 && sheet.getRow(0) != null)
        {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        /** 循环Excel的行 */
        for (int r = (pageNum-1)*pageSize+1; r < pageNum*pageSize+1; r++)
        {
            Row row = sheet.getRow(r);
            if (row == null)
            {
                continue;
            }
            Map<Object,Object> rowLst = new HashMap<>();
            /** 循环Excel的列 */
            for (int c = 0; c < getTotalCells(); c++)
            {
                Cell cell = row.getCell(c);
                String cellValue = "";

                if (null != cell)
                {
                    // 以下是判断数据的类型
                    switch (cell.getCellType())
                    {
                        // 数字
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            double cellValues = cell.getNumericCellValue();
                            NumberFormat nf = NumberFormat.getInstance();
                            String s = nf.format(cellValues);
                            if (s.indexOf(",") >= 0) {
                                //这种方法对于自动加".0"的数字可直接解决
                                //但如果是科学计数法的数字就转换成了带逗号的，
                                // 例如：12345678912345的科学计数法是1.23457E+13，
                                // 经过这个格式化后就变成了字符串“12,345,678,912,345”，
                                // 这也并不是想要的结果，所以要将逗号去掉
                                cellValue = s.replace(",", "");
                            }else{
                                cellValue=s;
                            }
                            break;
                        // 字符串
                        case HSSFCell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        // Boolean
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        // 公式
                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellValue = cell.getCellFormula() + "";
                            break;
                        // 空值
                        case HSSFCell.CELL_TYPE_BLANK:
                            cellValue = "";
                            break;
                        // 故障
                        case HSSFCell.CELL_TYPE_ERROR:
                            cellValue = "非法字符";
                            break;

                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.put(c,cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }

        return dataLst;
    }

    /**
     * 获取表格中总行数
     * @param wb
     * @param type
     * @return
     */
    public static int readExcelRows(Workbook wb,String type)
    {
        Sheet sheet = null;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            if(wb.getSheetName(i).equals(type)){
                /** 得到shell */
                sheet = wb.getSheetAt(i);
            }
        }
        if(sheet==null){
            return 0;
        }
        /** 得到Excel的行数 */
        totalRows = sheet.getPhysicalNumberOfRows();
        return totalRows;
    }

    /**
     * 解析导入指标excel表头，判断表头是否符合
     * @param file
     * @return
     */
    public static String checkData(MultipartFile file){
        String[] headDate = new String[]{"指标名","别名","描述","单位","指标大类","对象组","是否匹配字段","最大值","最小值"};
        String result = null;
        //判断Excel文件是否有内容
        boolean notNull=false;
        String filename=file.getOriginalFilename();
        if (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$")) {
            throw new NumberFormatException("上传文件格式不正确！");
        }
        //判断Excel文件的版本
        boolean isExcel2003=true;
        if (filename.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        Workbook wb = null;
        try {
            InputStream fis=file.getInputStream();
            if (isExcel2003) {
                wb = new HSSFWorkbook(fis);
            } else  {
                wb = new XSSFWorkbook(fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 总共有多少张sheet表
        int sheetnum = wb.getNumberOfSheets();

        for (int i = 0; i < sheetnum; i++) {
            Sheet sheet = wb.getSheetAt(i);
            // 表头数据
            Row namerow = sheet.getRow(0);
            if (null != namerow) {
                // 总行数
                int rowNum = sheet.getPhysicalNumberOfRows();
                // 总列数
                int colNum = namerow.getPhysicalNumberOfCells();
                if (colNum!=9){
                    result = "导入表格列数与模板不对应";
                    break;
                }
                // 判断工作表是否为空
                if (rowNum == 0) {
                    continue;
                }
                Row row = sheet.getRow(0);
                if (row != null) {
                    //遍历列
                    for (int cIndex = 0; cIndex < 9; cIndex++) {
                        Cell cell = row.getCell(cIndex);
                        if (cell != null) {
                            String cellValue = "";
                            //判断数据的类型
                            switch (cell.getCellType()){
                                case Cell.CELL_TYPE_NUMERIC:
                                    //数字
                                    double cellValues = cell.getNumericCellValue();
                                    NumberFormat nf = NumberFormat.getInstance();
                                    String s = nf.format(cellValues);
                                    if (s.indexOf(",") >= 0) {
                                        //这种方法对于自动加".0"的数字可直接解决
                                        //但如果是科学计数法的数字就转换成了带逗号的，
                                        // 例如：12345678912345的科学计数法是1.23457E+13，
                                        // 经过这个格式化后就变成了字符串“12,345,678,912,345”，
                                        // 这也并不是想要的结果，所以要将逗号去掉
                                        cellValue = s.replace(",", "");
                                    }else{
                                        cellValue=s;
                                    }
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    //字符串
                                    cellValue = String.valueOf(cell.getStringCellValue());
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    //Boolean
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case Cell.CELL_TYPE_FORMULA:
                                    //公式
                                    cellValue = String.valueOf(cell.getCellFormula());
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    //空值
                                    cellValue = "";
                                    break;
                                case Cell.CELL_TYPE_ERROR:
                                    //故障
                                    cellValue = "非法字符";
                                    break;
                                default:
                                    cellValue = "未知类型";
                                    break;
                            }
                            if (!cellValue.equals(headDate[cIndex])){
                                result = "导入表格列名与模板不一致";
                                break;
                            }
                        }
                    }
                }

            }
        }
        return result;
    }
}
