package com.integration.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.net.www.protocol.http.HttpURLConnection;

/**
* @Package: com.integration.utils
* @ClassName: Base64ImageUtils
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 图片加密工具类
*/
public class Base64ImageUtils {
	private final static Logger logger = LoggerFactory.getLogger(Base64ImageUtils.class);
	/**
	 * 本地图片转换成base64字符串
	 * @param imgFile	图片本地路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:40:46
	 */
	public static String ImageToBase64ByLocal(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
 
		InputStream in = null;
		byte[] data = null;
 
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			
			data = new byte[in.available()];
			in.read(data);
			
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (in!=null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}

	
	/**
	 * 在线图片转换成base64字符串
	 * 
	 * @param imgURL	图片线上路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:43:18
	 */
	public static String ImageToBase64ByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			// 关闭流
			try {
				if (is!=null) {
					is.close();
				}
			} catch (IOException e) {
			}
			
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data.toByteArray());
	}

	
	/**
	 * base64字符串转换成图片
	 * @param imgStr		base64字符串
	 * @param imgFilePath	图片存放路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:42:17
	 */
	public static boolean Base64ToImage(String imgStr,String imgFilePath) {
		// 对字节数组字符串进行Base64解码并生成图片
		boolean flag = true;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					// 调整异常数据
					b[i] += 256;
				}
			}
 
			out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
		} catch (IOException e) {
			flag = false;
		} finally {
			if (out!=null) {
				try {
					out.close();
				} catch (IOException e) {
				}
				
			}
		}
		return flag;
 
	}



}
