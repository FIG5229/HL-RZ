package com.integration.entity;
/**
* @Package: com.integration.entity
* @ClassName: Face
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: 人脸识别
*/
public class Face {
    private String id;

    private String czry_id;

    private String face_id;

    private String img_up;

    private String img_down;

    private String img_left;

    private String img_right;

    private String img_main;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

	public String getCzry_id() {
		return czry_id;
	}

	public void setCzry_id(String czry_id) {
		this.czry_id = czry_id;
	}

	public String getFace_id() {
		return face_id;
	}

	public void setFace_id(String face_id) {
		this.face_id = face_id;
	}

	public String getImg_up() {
		return img_up;
	}

	public void setImg_up(String img_up) {
		this.img_up = img_up;
	}

	public String getImg_down() {
		return img_down;
	}

	public void setImg_down(String img_down) {
		this.img_down = img_down;
	}

	

	public String getImg_left() {
		return img_left;
	}

	public void setImg_left(String img_left) {
		this.img_left = img_left;
	}

	public String getImg_right() {
		return img_right;
	}

	public void setImg_right(String img_right) {
		this.img_right = img_right;
	}

	public String getImg_main() {
		return img_main;
	}

	public void setImg_main(String img_main) {
		this.img_main = img_main;
	}

    	

    
}