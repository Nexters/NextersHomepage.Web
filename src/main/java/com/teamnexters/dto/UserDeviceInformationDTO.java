package com.teamnexters.dto;

import org.springframework.stereotype.Component;

@Component
public class UserDeviceInformationDTO {
	private int deviceNo;
	private String pushServerKind;
	private String relationKey;
	private String deviceId;
	private String pushId;
	private String revokeYN;
	private String revokeReason;
	private String insertDate;
	private String insertTime;
	private String updateDate;
	private String updateTime;

	public void setDeviceNo(int deviceNo){
		this.deviceNo=deviceNo;
	}
	public int getDeviceNo(){
		return deviceNo;
	}
	public void setPushServerKind(String pushServerKind){
		this.pushServerKind=pushServerKind;
	}
	public String getPushServerKind(){
		return pushServerKind;
	}
	public void setRelationKey(String relationKey){
		this.relationKey=relationKey;
	}
	public String getRealtionKey(){
		return relationKey;
	}
	public void setDeviceId(String deviceId){
		this.deviceId=deviceId;
	}
	public String getDeviceId(){
		return deviceId;
	}
	public void setPushId(String pushId){
		this.pushId=pushId;
	}
	public String getPushId(){
		return pushId;
	}
	public void setRevokeYN(String revokeYN){
		this.revokeYN=revokeYN;
	}
	public String getRevokeYN(){
		return revokeYN;
	}
	public void setRevokeReason(String revokeReason){
		this.revokeReason=revokeReason;
	}
	public String getRevokeReason(){
		return revokeReason;
	}
	public void setInsertDate(String insertDate){
		this.insertDate=insertDate;
	}
	public String getInsertDate(){
		return insertDate;
	}
	public void setInsertTime(String insertTime){
		this.insertTime=insertTime;
	}
	public String getInsertTime(){
		return insertTime;
	}
	public void setUpdateDate(String updateDate){
		this.updateDate=updateDate;
	}
	public String getUpdateDate(){
		return updateDate;
	}
	public void setUpdateTime(String updateTime){
		this.updateTime=updateTime;
	}
	public String getUpdateTime(){
		return updateTime;
	}
}
