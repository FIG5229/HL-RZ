package com.integration.utils;


import com.alibaba.fastjson.JSONObject;
import com.integration.config.IomPlatformParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 获取服务器信息工具类
 */
public class SerialNumberUtil {

    private static Logger logger = LoggerFactory.getLogger(SerialNumberUtil.class);

    //cpu序列号
    public static final String CPUID = "cpuId";
    //硬盘信息
    public static final String DISKID = "diskId";
    //ip
    public static final String IP = "ip";
    //mac
    public static final String MAC = "mac";
    //主板序列号
    public static final String MAINBOARD = "mainboard";

    //授权产品到期时间
    public static final String ENDDATE = "validDate";

    //授权码注册时间
    public static final String REGISTERDATE = "registerTime";

    public static final String REGISTERMAN = "registerMan";

    //授权码有效天数
    public static final int VALIDATEDAY = 1;

    //识别码中版本key
    public static final String VERSION = "version";

    //授权码中授权版本
    public static final String AUTHVERSION = "authVersion";

    public static final String CLIENTSIGN = "client-sign";
    public static final String ALLOWFLAG = "allow-flag";

    //可访问模块
    public static final String ALLOWMODULE = "allowModule";

    public static final String ALLOWMODULENAME = "allowModuleName";

    public static final String [] INFOPROPERS = {CPUID,IP,MAC,MAINBOARD};


    /**
     * 获取主板序列号
     * @return
     */
    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取硬盘序列号(该方法获取的是 盘符的逻辑序列号,并不是硬盘本身的序列号)
     * 硬盘序列号还在研究中
     * @param drive 盘符
     * @return
     */
    public static String getHardDiskSN(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\""
                    + drive
                    + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取CPU序列号
     * @return
     */
    public static String getCPUSerial() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            // + "    exit for  \r\n" + "Next";
            fw.write(vbs);
            fw.close();
            String path = file.getPath().replace("%20", " ");
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + path);
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result = "无CPU_ID被读取";
        }
        return result.trim();
    }

    public static List<String> getLocalHostLANAddress()	throws UnknownHostException, SocketException {
        List<String> ips = new ArrayList<String>();
        Enumeration<NetworkInterface> interfs = NetworkInterface.getNetworkInterfaces();
        while (interfs.hasMoreElements()) {
            NetworkInterface interf = interfs.nextElement();
            Enumeration<InetAddress> addres = interf.getInetAddresses();
            while (addres.hasMoreElements()) {
                InetAddress in = addres.nextElement();
                if (in instanceof Inet4Address) {
                    if(!"127.0.0.1".equals(in.getHostAddress())){
                        ips.add(in.getHostAddress());
                    }
                }
            }
        }
        Collections.sort(ips);
        return ips;
    }


    public static String getMacByIP(String ip){
        logger.info(ip);
        InetAddress ia = null;// 获取本地IP对象
        try {
            ia = InetAddress.getByName(ip);
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(ia)
                    .getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            return sb.toString().toUpperCase();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MAC
     * 通过jdk自带的方法,先获取本机所有的ip,然后通过NetworkInterface获取mac地址
     * @return
     */
    public static String getMac() {
        try {
            String resultStr = "";
            List<String> ls = getLocalHostLANAddress();
            List<String> macList = new ArrayList<>();
            for(String str : ls){
                logger.info(str);
                InetAddress ia = InetAddress.getByName(str);// 获取本地IP对象
                // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
                byte[] mac = NetworkInterface.getByInetAddress(ia)
                        .getHardwareAddress();
                // 下面代码是把mac地址拼装成String
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    if (i != 0) {
                        sb.append("-");
                    }
                    // mac[i] & 0xFF 是为了把byte转化为正整数
                    String s = Integer.toHexString(mac[i] & 0xFF);
                    sb.append(s.length() == 1 ? 0 + s : s);
                }
                // 把字符串所有小写字母改为大写成为正规的mac地址并返回
//                resultStr += sb.toString().toUpperCase()+",";
                macList.add(sb.toString().toUpperCase());
            }
            String [] macArr = macList.toArray(new String [macList.size()]);
            Arrays.sort(macArr);
            resultStr = String.join(",",macArr);
            return resultStr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /***************************linux*********************************/

    public static String executeLinuxCmd(String cmd)  {
        try {
            logger.debug("got cmd job : " + cmd);
            Runtime run = Runtime.getRuntime();
            Process process;
            process = run.exec(cmd);
            InputStream in = process.getInputStream();
            BufferedReader bs = new BufferedReader(new InputStreamReader(in));
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[8192];
            for (int n; (n = in.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }

            in.close();
            process.destroy();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param cmd 命令语句
     * @param record 要查看的字段
     * @param symbol 分隔符
     * @return
     */
    public static String getSerialNumber(String cmd ,String record,String symbol) {
        String execResult = executeLinuxCmd(cmd);
        String[] infos = execResult.split("\n");

        Arrays.sort(infos);

        for(String info : infos) {
            info = info.trim();
            if(info.indexOf(record) != -1) {
                info.replace(" ", "");
                String[] sn = info.split(symbol);
                if(sn.length > 1){
                    return sn[1];
                }
            }
        }

        return null;
    }

    /**
     * 获取CPUID、硬盘序列号、MAC地址、主板序列号
     * @return
     */
    public static Map<String, String> getAllSn(){
        String os = System.getProperty("os.name");
        os = os.toUpperCase();
        logger.debug(os);

        Map<String, String> snVo = new HashMap<String, String>();

        String ip = IomPlatformParam.authServerIp;
        if(StringUtils.isEmpty(ip)){
            logger.error("请配置授权服务ip");
        }
        String mac = 				getMacByIP(ip);
        if("LINUX".equals(os)) {
            logger.debug("=============>for linux");
            String cpuid = 				getSerialNumber("sudo dmidecode -t processor | grep 'ID'", "ID",":");
            String mainboardNumber = 	getSerialNumber("sudo dmidecode |grep 'Serial Number'", "Serial Number",":");
            String diskNumber = 		getSerialNumber("sudo fdisk -l", "Disk identifier",":");

//            String mac = 				getSerialNumber("ifconfig -a", "ether"," ");

            snVo.put(CPUID,cpuid.toUpperCase().replace(" ", ""));
            //snVo.put(DISKID,diskNumber.toUpperCase().replace(" ", ""));
            snVo.put(MAC,mac.toUpperCase().replace(" ", ""));
            snVo.put(MAINBOARD,mainboardNumber.toUpperCase().replace(" ", ""));
        }else {
            logger.debug("=============>for windows");
            String cpuid = SerialNumberUtil.getCPUSerial();
            String mainboard = SerialNumberUtil.getMotherboardSN();
            String disk = SerialNumberUtil.getHardDiskSN("c");
//            String mac = SerialNumberUtil.getMac();


            snVo.put(CPUID,cpuid.toUpperCase().replace(" ", ""));
            //snVo.put(DISKID,disk.toUpperCase().replace(" ", ""));
            snVo.put(MAC,mac.toUpperCase().replace(" ", ""));
            snVo.put(MAINBOARD,mainboard.toUpperCase().replace(" ", ""));
        }
        snVo.put(IP,ip);

        return snVo;
    }

    /**
     * 服务器授权状态
     * @return
     */
    public static int authFlag(){
        String allowFlag = System.getProperty(ALLOWFLAG);
        if(allowFlag == null){
            return AllowFlag.unauthorized.getCode();
        }
        return Integer.parseInt(allowFlag);
    }

    /**
     * 服务器授权模块
     * @return
     */
    public static List<String> allowModule(){
        String allowModule = System.getProperty(ALLOWMODULE);
        if(allowModule == null){
            return Collections.emptyList();
        }
        return JSONObject.parseObject(allowModule, List.class);
    }

    /**
     * 服务器是否可用
     * @return
     */
    public static boolean isUseable(){
        String allowFlag = System.getProperty(ALLOWFLAG);
        if(allowFlag == null){
            return false;
        }
        if(allowFlag.equals(String.valueOf(AllowFlag.authorized.getCode()))){
            return true;
        }
        return false;
    }


    /**
     * linux
     * cpuid : dmidecode -t processor | grep 'ID'
     * mainboard : dmidecode |grep 'Serial Number'
     * disk : fdisk -l
     * mac : ifconfig -a
     * @param args
     */
    public static void main(String[] args) {
        /*getAllSn().entrySet().forEach(entry->{
            logger.info(String.format("%s:%s",entry.getKey(),entry.getValue()));
        });*/

        //getMac();

        String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                + "Set colItems = objWMIService.ExecQuery _ \n"
                + "   (\"Select * from Win32_Processor\") \n"
                + "For Each objItem in colItems \n"
                + "    Wscript.Echo objItem.ProcessorId \n"
                + "    exit for  ' do the first cpu only! \n" + "Next \n";
        System.out.println(vbs);
    }

}