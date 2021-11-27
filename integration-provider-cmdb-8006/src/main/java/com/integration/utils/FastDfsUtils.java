package com.integration.utils;

import com.integration.config.Advice.MsgException;
import com.integration.entity.CiIconInfo;
import com.integration.entity.Download;
import com.integration.service.CiIconService;
import com.integration.service.impl.CiIconServiceImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hbr
 * @version 1.0
 * @date 2018-12-03 15:49:27
 */
@Component
public class FastDfsUtils {
	private final static Logger logger = LoggerFactory.getLogger(FastDfsUtils.class);
	@Autowired
	CiIconService ciIconService;
	
	private static String httpPort;
	private static String trackerHostname;
	private static String trackerPort;
	private static String storageip;

	public static String getHttpPort() {
		return httpPort;
	}

	@Value("${fastconf.httpport}")
	public void setHttpPort(String httpPort) {
		FastDfsUtils.httpPort = httpPort;
	}

	public static String getTrackerHostname() {
		return trackerHostname;
	}

	@Value("${fastconf.trackerhostname}")
	public void setTrackerHostname(String trackerHostname) {
		FastDfsUtils.trackerHostname = trackerHostname;
	}

	public static String getTrackerPort() {
		return trackerPort;
	}

	@Value("${fastconf.trackerport}")
	public void setTrackerPort(String trackerPort) {
		FastDfsUtils.trackerPort = trackerPort;
	}

	public static String getStorageip() {
		return storageip;
	}

	@Value("${fastconf.storageip}")
	public void setStorageip(String storageip) {
		FastDfsUtils.storageip = storageip;
	}

	/**
	 * 把图片上传到fastdfs服务器
	 *
	 * @param filePathList
	 * @return
	 */
	public ArrayList<String> upload(ArrayList<String> filePathList) {
		// 读取配置文件

		// 连接超时的时限，单位为毫秒
		ClientGlobal.setG_connect_timeout(2000);

		// 网络超时的时限，单位为毫秒
		ClientGlobal.setG_network_timeout(30000);

		ClientGlobal.setG_anti_steal_token(false);

		// 字符集
		ClientGlobal.setG_charset("UTF-8");

		ClientGlobal.setG_secret_key(null);

		ClientGlobal.setG_tracker_http_port(Integer.parseInt(httpPort));

		InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		tracker_servers[0] = new InetSocketAddress(trackerHostname, Integer.parseInt(trackerPort));
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));

		// 创建一个调度客户端对象
		TrackerClient trackerClient = new TrackerClient();
		// 创建连接获取服务对象
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e) {

			throw new MsgException(e.getMessage());
		}
		// 创建储存服务引用
		StorageServer storageServer = null;
		// 创建存储客户端对象
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 上传图片需要三个参数，路径，扩展名
		ArrayList<String> list = new ArrayList<>();
		for (String s : filePathList) {
			// 切割扩展名
			String split = s.substring(s.lastIndexOf(".") + 1);
			// String hz = split[1];
			String[] strings;
			try {
				strings = storageClient.upload_file(s, split, null);
			} catch (IOException e) {
				logger.error("图标上传异常1：",e);
				throw new MsgException(e.getMessage());
			} catch (Exception e) {
				logger.error("图标上传异常2：",e);
				throw new MsgException(e.getMessage());
			}
			list.add(strings[0] + "/" + strings[1]);
		}
		return list;
	}

	/**
	 * 下载图标
	 *
	 * @param ciIconList
	 * @throws Exception
	 */
	public String download(ArrayList<CiIconInfo> ciIconList) throws Exception {
		// 连接超时的时限，单位为毫秒
		ClientGlobal.setG_connect_timeout(2000);

		// 网络超时的时限，单位为毫秒
		ClientGlobal.setG_network_timeout(30000);

		ClientGlobal.setG_anti_steal_token(false);

		// 字符集
		ClientGlobal.setG_charset("UTF-8");

		ClientGlobal.setG_secret_key(null);

		ClientGlobal.setG_tracker_http_port(Integer.parseInt(httpPort));

		InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		tracker_servers[0] = new InetSocketAddress(trackerHostname, Integer.parseInt(trackerPort));
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));

		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 名称集合
		ArrayList<Download> downList = new ArrayList<>();
		// 截取名称
		for (int i = 0; i < ciIconList.size(); i++) {
			String g = ciIconList.get(i).getIcon_path().substring(ciIconList.get(i).getIcon_path().indexOf("g"));
			String[] split = g.split("/");
			Download download = new Download();
			download.setGroup(split[0]);
			download.setRemote(g.substring(7));
			downList.add(download);
		}
		// 根据文件标识下载文件
		String destDir = FileUtils.getZipFile();
		File file2 = new File(destDir);
		if (!file2.getParentFile().exists()) {
			file2.getParentFile().mkdirs();
		}

		// 文件字符集合
		ArrayList<byte[]> list = new ArrayList<>();

		for (Download download : downList) {
			byte[] by = storageClient.download_file(download.getGroup(), download.getRemote());
			// 将数据写入输出流
			File file = new File(destDir);
			if (!file.exists()) {
				file.mkdirs();
			}
			list.add(by);
		}

		List<String> names = new ArrayList<String>();
		for (CiIconInfo ciIconInfo : ciIconList) {
			names.add(ciIconInfo.getIcon_name());
		}
		names = changeNames(names);
		
		for (int i = 0; i < list.size(); i++) {
			FileOutputStream fileOutputStream = new FileOutputStream(
					destDir + "/" + names.get(i) + "." + ciIconList.get(i).getIcon_form());
			try {
				IOUtils.write(list.get(i), fileOutputStream);
			} finally {
				fileOutputStream.close();
			}
		}
		return destDir;
	}

	/*
	 * public static void main(String[] args) { List<String> names = new
	 * ArrayList<String>(); names.add("a"); names.add("b"); names.add("b");
	 * names.add("b"); names.add("c"); names.add("c"); names.add("d");
	 * names.add("d"); names.add("d"); }
	 */

	public static List<String> changeNames(List<String> names) {
		List<String> res = new ArrayList<String>();
		if (names != null && names.size() > 0) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String name : names) {
				Integer count = map.get(name);
				if (count == null) {
					count = 0;
					res.add(name);
				} else {
					count++;
					res.add(name + "(" + count + ")");
				}
				map.put(name, count);
			}
		}
		return res;
	}

	public int delete(String iconPath) throws Exception {

		// 连接超时的时限，单位为毫秒
		ClientGlobal.setG_connect_timeout(2000);

		// 网络超时的时限，单位为毫秒
		ClientGlobal.setG_network_timeout(30000);

		ClientGlobal.setG_anti_steal_token(false);

		// 字符集
		ClientGlobal.setG_charset("UTF-8");

		ClientGlobal.setG_secret_key(null);

		ClientGlobal.setG_tracker_http_port(Integer.parseInt(httpPort));

		InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		tracker_servers[0] = new InetSocketAddress(trackerHostname, Integer.parseInt(trackerPort));
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));

		// 创建一个调度客户端对象
		TrackerClient trackerClient = new TrackerClient();
		// 创建连接获取服务对象
		TrackerServer trackerServer = trackerClient.getConnection();
		// 创建储存服务引用
		StorageServer storageServer = null;
		// 创建存储客户端对象
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);

		String g = iconPath.substring(iconPath.indexOf("g"));
		String group = g.substring(0, 6);
		String storagePath = g.substring(g.indexOf("M"));
		int i = storageClient.delete_file(group, storagePath);
		return i;
	}

	/**
	 *
	 * @param url  文件服务器上文件的路径
	 * @param dest 文件保存到本地的路径
	 * @throws Exception
	 */
	public static void downloadFile(String url, String dest) throws Exception {
		// 连接超时的时限，单位为毫秒
		ClientGlobal.setG_connect_timeout(2000);

		// 网络超时的时限，单位为毫秒
		ClientGlobal.setG_network_timeout(30000);

		ClientGlobal.setG_anti_steal_token(false);

		// 字符集
		ClientGlobal.setG_charset("UTF-8");

		ClientGlobal.setG_secret_key(null);

		ClientGlobal.setG_tracker_http_port(Integer.parseInt(httpPort));

		InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		tracker_servers[0] = new InetSocketAddress(trackerHostname, Integer.parseInt(trackerPort));
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));

		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);

		// ===截取字符串
		// 此处26非固定数值(http://192.168.7.123:8888/)
		String str = url.substring(26);
		// a = group1
		String a = StringUtils.substringBefore(str, "/");
		// b=M00/00/01/wKgHe10kTouAVXHMAAACjWiw16w219.xml
		String b = str.substring(a.length() + 1, str.length());
		// ===截取字符串

		byte[] bytes = storageClient.download_file(a, b);
		IOUtils.write(bytes, new FileOutputStream(dest));
	}
}