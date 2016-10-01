package com.example.dao;

public class ComputerRoomStatus {

	int id;
	String time1,time2,time3,time4,time5,time6;
	
	
	public ComputerRoomStatus(int id, String time1, String time2, String time3,
			String time4, String time5, String time6) {
		super();
		this.id = id;
		this.time1 = time1;
		this.time2 = time2;
		this.time3 = time3;
		this.time4 = time4;
		this.time5 = time5;
		this.time6 = time6;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public String getTime3() {
		return time3;
	}
	public void setTime3(String time3) {
		this.time3 = time3;
	}
	public String getTime4() {
		return time4;
	}
	public void setTime4(String time4) {
		this.time4 = time4;
	}
	public String getTime5() {
		return time5;
	}
	public void setTime5(String time5) {
		this.time5 = time5;
	}
	public String getTime6() {
		return time6;
	}
	public void setTime6(String time6) {
		this.time6 = time6;
	}
	@Override
	public String toString() {
		return "ComputerRoomStatus [id=" + id + ", time1=" + time1 + ", time2="
				+ time2 + ", time3=" + time3 + ", time4=" + time4 + ", time5="
				+ time5 + ", time6=" + time6 + "]";
	}
	
}
