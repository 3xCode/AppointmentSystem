package com.example.dao;

import com.example.utils.DataUtils;

public class RoomMessage {
	private int roomid;
	private int total;
	private int remaim;
	private int weekday;
	private int num;
	private String appointmentdate;

	public String getAppointmentdate() {
		return appointmentdate;
	}

	public void setAppointmentdate(String appointmentdate) {
		
		this.appointmentdate = DataUtils.getWeek(appointmentdate);
	}

	public RoomMessage() {
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setRemaim(int remaim) {
		this.remaim = remaim;
	}

	public int getRoomid() {
		return roomid;
	}

	public int getTotal() {
		return total;
	}

	public int getRemaim() {
		return remaim;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "星期" + appointmentdate + "第" + num + "节课还有" + remaim + "个座位";
	}
}
