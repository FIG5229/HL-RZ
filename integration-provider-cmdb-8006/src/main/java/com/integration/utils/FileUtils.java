
package com.integration.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.integration.config.Advice.MsgException;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-03 15:49:27
 */
@Component
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	public static final String BASE = ProjectPath.RootPath();
	
	public static String getBasePath() {
		return BASE +"path/";
	}
	
	public static String getZipFile() {
		return BASE +"zipFile";
	}
	
	public static String getDown() {
		return BASE +"down/";
	}
	
	public static boolean isWindows() {
		return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
	}
	
	/**
	 * 通用流关闭方法
	 * @param closeable
	 */
	public static void closeStream(Closeable closeable) {
		if (closeable!=null) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}
	
    /**
     * 解压的方法
     *
     * @param srcFile
     * @param destDir
     * @throws IOException
     */
    public void decompression(File srcFile, String destDir) {
        ZipFile zipFile = null;
        String msg = null;
        try {
            Charset gbk = Charset.forName("GBK");
            zipFile = new ZipFile(srcFile, gbk);
            // 创建输出目录
            createDirectory(destDir, null);
            Enumeration<?> enums = zipFile.entries();
            while (enums.hasMoreElements()) {

                ZipEntry entry = (ZipEntry) enums.nextElement();

                if (entry.isDirectory()) {
                    // 是目录
                    // 创建空目录
                    createDirectory(destDir, entry.getName());
                } else {
                    // 是文件
                    zipWriteFile(zipFile, entry, destDir);
                }
            }

        } catch (IOException e) {
        	msg = "解压缩文件出现异常";
        } finally {
        	closeStream(zipFile);
        }
        if (msg!=null) {
        	throw new MsgException(msg);
		}
    }
    
    private  void zipWriteFile(ZipFile zipFile,ZipEntry entry,String destDir) {
    	File tmpFile = null;
    	 InputStream is = null;
         OutputStream os = null;
         String msg = null;
         try {
        	 tmpFile = new File(destDir + "/" + entry.getName());
             // 创建输出目录
        	 createDirectory(tmpFile.getParent() + "/", null);
             is = zipFile.getInputStream(entry);
             os = new FileOutputStream(tmpFile);
             int length = 0;

             byte[] b = new byte[2048];
             while ((length = is.read(b)) != -1) {
                 os.write(b, 0, length);
             }

         } catch (IOException ex) {
        	 msg = "压缩文件转临时文件异常";
         } finally {
        	 closeStream(is);
        	 closeStream(os);
         }
         if (msg!=null) {
        	 throw new MsgException(msg);
		}
	}
    

    /**
     * @Author: ztl
     * date: 2021-08-09
     * @description:创建文件夹
     */
    public void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || "".equals(subDir.trim()))) {
            // 子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
    }

    /**
     * 获取文件的绝对路径
     *
     * @param path
     * @param filePathList
     */
    public ArrayList<String> getFilePath(String path, ArrayList<String> filePathList) {
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                filePathList.add(tempList[i].getAbsolutePath());
            }
            if (tempList[i].isDirectory()) {
                getFilePath(tempList[i].getAbsolutePath(), filePathList);
            }
        }
        return filePathList;
    }

    /**
     * 获取文件名
     *
     * @param path
     * @param filePathList
     * @return
     */
    public ArrayList<String> getFileName(String path, ArrayList<String> filePathList) {
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                filePathList.add(tempList[i].getName());
            }
            if (tempList[i].isDirectory()) {
                getFileName(tempList[i].getAbsolutePath(), filePathList);
            }
        }
        return filePathList;
    }

    /**
     * @Author: ztl
     * date: 2021-08-09
     * @description: 压缩的方法，需要两个参数，源目录和要压缩的目录
     */
    public void compress(String srcFilePath) {
        String destFilePath = getDown()+"icon.zip";
        File file = new File(destFilePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        File src = new File(srcFilePath);
        if (!src.exists()) {
            throw new RuntimeException(srcFilePath + "不存在");
        }
        File zipFile = new File(destFilePath);
        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            String baseDir = "";
            compressbyType(src, zos, baseDir);
            zos.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * @Author: ztl
     * date: 2021-08-09
     * @description: 想要对文件进行压缩操作，这就需要用到ZipOutputStream来对文件压缩操作
     */
    private void compressbyType(File src, ZipOutputStream zos, String baseDir) {
        if (!src.exists()) {
            return;
        }
        //判断压缩的是文件还是文件夹
        if (src.isFile()) {
            compressFile(src, zos, baseDir);
        } else if (src.isDirectory()) {
            compressDir(src, zos, baseDir);
        }

    }

    /**
     * @Author: ztl
     * date: 2021-08-09
     * @description: 压缩文件
     */
    private void compressFile(File file, ZipOutputStream zos, String baseDir) {
        if (!file.exists()) {
            return;
        }
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            //ZipEntry 类用于表示 ZIP 文件条目。利用这个类压缩和解压zip文件
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            //开始编写新的ZIP文件条目，并将流定位到条目数据的开头。
            zos.putNextEntry(entry);
            int count;
            byte[] buf = new byte[1024];
            while ((count = bis.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
        	closeStream(bis);
		}
    }

    /**
     * @Author: ztl
     * date: 2021-08-09
     * @description: 压缩文件夹
     */
    private void compressDir(File dir, ZipOutputStream zos, String baseDir) {
        if (!dir.exists()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files.length == 0) {
            try {
                //在Windows下的路径分隔符和Linux下的路径分隔符是不一样的，当直接使用绝对路径时，
                //跨平台会暴出“No such file or diretory”的异常。
                zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        for (File file : files) {
            compressbyType(file, zos, baseDir + dir.getName() + File.separator);
        }
    }

    /**
     * 获取文件的大小
     *
     * @param file
     */
    public int getFileLength(File file) {
        int fileLength1 = 0;
        int fileLength = 0;
        if (file.exists() && file.isFile()) {
            fileLength1 = (int) file.length();
            fileLength = fileLength1 / 1024;
        }
        return fileLength;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public boolean delAllFile(String path) {
        File file = new File(path);
        delFile(file);
        return true;
    }
    
    static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    public void delFolder(String folderPath) {
        try {
            //删除完里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            //删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}