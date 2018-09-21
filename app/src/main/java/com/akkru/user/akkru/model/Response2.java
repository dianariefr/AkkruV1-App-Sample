package com.akkru.user.akkru.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response2 {

	@SerializedName("result")
	private boolean result;

	@SerializedName("outlets")
	private List<Outlet> outlet;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setOutlet(List<Outlet> outlet){
		this.outlet = outlet;
	}

	public List<Outlet> getOutlet(){
		return outlet;
	}
}