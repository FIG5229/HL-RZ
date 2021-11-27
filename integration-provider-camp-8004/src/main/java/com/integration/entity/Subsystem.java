package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: Subsystem
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description:
*/
public class Subsystem {

    private String id;
    private String name;
    private String describes;
    private String serverName;
    private String isorther;
    private String commonPageUrl;
    


	public String getCommonPageUrl() {
		return commonPageUrl;
	}

	public void setCommonPageUrl(String commonPageUrl) {
		this.commonPageUrl = commonPageUrl;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}



    public String getIsorther() {
		return isorther;
	}

	public void setIsorther(String isorther) {
		this.isorther = isorther;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describe) {
        this.describes = describe;
    }
}
