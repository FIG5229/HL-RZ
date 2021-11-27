package com.integration.config;

/**
 * @ClassName ModuleEnum
 * @Description 模块枚举
 * @Author zhangfeng
 * @Date 2021/8/9 15:08
 * @Version 1.0
 **/
public class ModuleEnum {

    public enum module{
        camp("基础管理", "camp", "/camp"),
        cmdb("配置管理", "cmdb", "/cmdb"),
        alarm("事件可视化", "alarm", "/event"),
        pmv("性能可视化", "pmv", "/perf"),
        dmv("架构可视化", "dmv", "/topology"),
        smv("场景配置化", "smv", "/scene"),
        robot("运维机器人", "robot", "/robot"),
        dcv("3D可视化", "dcv", "/virtual")
        /*,
        interf("数据采集监控", "interface", "interface")*/;

        private String name;
        private String url;
        private String preUrl;

        module(String name, String url, String preUrl) {
            this.name = name;
            this.url = url;
            this.preUrl = preUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPreUrl() {
            return preUrl;
        }

        public void setPreUrl(String preUrl) {
            this.preUrl = preUrl;
        }
    }

}
