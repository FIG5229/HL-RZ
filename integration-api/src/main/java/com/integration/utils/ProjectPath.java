package com.integration.utils;

import java.io.File;

public class ProjectPath {

    //获取项目的根路径
    public final static String classPath;

    static {
        //获取的是classpath路径，适用于读取resources下资源
        classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    /**
     * 项目根目录
     */
    public static String getRootPath() {
        return RootPath("");
    }

    /**
     * 自定义追加路径
     */
    public static String getRootPath(String u_path) {
        return RootPath("");
    }

    public static String RootPath() {
        return RootPath("");
    }
    
    /**
     * 获取运行目录
     * 私有处理方法
     */
    public static String RootPath(String u_path) {
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(0,classPath.lastIndexOf("integration-provider")) + u_path;
            rootPath = rootPath.replaceAll("/", "\\\\");
            if (rootPath.substring(0, 1).equals("\\")) {
                rootPath = rootPath.substring(1);
            }

            rootPath = getUpPath(rootPath);

            if(rootPath.indexOf("file:")==0){
                return rootPath.substring(6);
            }
        }
        //linux下
        if ("/".equals(File.separator)) {

            rootPath = classPath.substring(0,classPath.lastIndexOf("integration-provider")) + u_path;
            rootPath = rootPath.replaceAll("\\\\", "/");

            if(rootPath.indexOf("file:")==0){
                return rootPath.substring(5);
            }
        }
        return rootPath;
    }

    /**
     * 获取运行目录
     * 私有处理方法
     */
    public static String RootPathUpImg(String u_path) {
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
           rootPath = classPath.substring(0,classPath.lastIndexOf("integration-provider")) + u_path;
            rootPath = rootPath.replaceAll("/", "\\\\");
            if (rootPath.substring(0, 1).equals("\\")) {
                rootPath = rootPath.substring(1);
            }
        }
        //linux下
        if ("/".equals(File.separator)) {

            rootPath = classPath.substring(0,classPath.lastIndexOf("integration-provider")) + u_path;
            rootPath = rootPath.replaceAll("\\\\", "/");

            if(rootPath.indexOf("file:")==0){
                return rootPath.substring(5);
            }
        }
        return rootPath;
    }

    public static String RootPathUpImg() {
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(0,classPath.lastIndexOf("integration-provider"));
            rootPath = rootPath.replaceAll("/", "\\\\");
            if (rootPath.substring(0, 1).equals("\\")) {
                rootPath = rootPath.substring(1);
            }
        }
        //linux下
        if ("/".equals(File.separator)) {

            rootPath = classPath.substring(0,classPath.lastIndexOf("integration-provider"));
            rootPath = rootPath.replaceAll("\\\\", "/");

            if(rootPath.indexOf("file:")==0){
                return rootPath.substring(5);
            }
        }
        return rootPath;
    }
    /**
     * 获取上级目录地址
     * @param url
     */
    private static String getUpPath(String url) {
		String res = url;
    	//String url = "\F:AAA\BBB\CCC\";
		if (url!=null&&!"".equals(url)) {
			if (url.lastIndexOf("\\")>0) {
				url = url.substring(0,url.lastIndexOf("\\"));
				if (url.lastIndexOf("\\")>0) {
					return url.substring(0,url.lastIndexOf("\\")+1);
				}
			}
		}
		return res;
	}
    
}
