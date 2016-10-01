package com.example.dao;

public class Course {
	private String name;// 课程名称、上课教室，教师，课程编号
	int weekday, classindex,room;
	int start, step; // 开始上课节次， 一共几节课
	String appoint;
	public Course(String name, int room, int start, int step, int classindex,int weekday) {
		super();
		this.name = name;
		this.room = room;
		this.start = start;
		this.step = step;
		this.weekday = weekday;
		this.classindex = classindex;

	}
	
	
	public Course(String name, int room, int start, int step, int classindex,int weekday,String appoint) {
		super();
		this.name = name;
		this.room = room;
		this.start = start;
		this.step = step;
		this.weekday = weekday;
		this.classindex = classindex;
		this.appoint = appoint;
	}
	

	public String getAppoint() {
		return appoint;
	}

	public void setAppoint(String appoint) {
		this.appoint = appoint;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getClassindex() {
		return classindex;
	}

	public void setClassindex(int classindex) {
		this.classindex = classindex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String jifang=null;
		switch (this.room) {
		case 0:
			jifang="204";
			break;
		case 1:
			jifang="208";
			break;
		case 2:
			jifang="108";
			break;
		case 3:
			jifang="109";
			break;
		default:
			break;
		}
		return jifang+"机房，星期"+weekday+"的第"+classindex+"节";
	}
}