package com.akkru.user.akkru.model;

import com.google.gson.annotations.SerializedName;

public class Avatar{

	@SerializedName("url")
	private String url;

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}
}