package com.example.dao;

import com.example.utils.DataUtils;

public class OrderMessage {
	public String appointdate;
	public String num;
	public String roomindex;
	public String isCancel;
	
	public OrderMessage(){}
	
	public OrderMessage(String appointdate, String num, String roomindex,String isCancel) {
		super();
		this.appointdate = appointdate;
		this.num = num;
		this.roomindex = roomindex;
		this.isCancel = isCancel;
	}

	public String getRoomindex() {
		return roomindex;
	}

	public void setRoomindex(String roomindex) {
		this.roomindex = roomindex;
	}

	public String getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(String isCancel) {
		this.isCancel = isCancel;
	}

	public String getClassindex() {
		return roomindex;
	}

	public void setClassindex(String roomindex) {
		this.roomindex = roomindex;
	}

	public String getAppointdate() {
		return appointdate;
	}

	public void setAppointdate(String appointdate) {
		this.appointdate = appointdate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		String jifang=null;
		switch (Integer.parseInt(this.roomindex)) {
		case 0:
			jifang="ÒÝ·òÂ¥108";
			break;
		case 1:
			jifang="ÒÝ·òÂ¥109";
			break;
		case 2:
			jifang="Ö÷Â¥204";
			break;
		case 3:
			jifang="Ö÷Â¥208";
			break;
		default:
			break;
		}
		String[] split = appointdate.split(" ");
		return jifang+","+split[0]+"µÄµÚ"+num+"½Ú";
	}
}
