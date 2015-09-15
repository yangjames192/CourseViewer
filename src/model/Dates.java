package model;

public class Dates {
	private String startTime;
	private String endTime;
	private String weekdays;
	private String startDate;
	private String endDate;
	/*private boolean isTba; // is TBA
	private boolean isCanceled;
	private boolean isClosed;*/

	public Dates(String st, String et, String wd, String sd, String ed) {
		this.startTime = st;
		this.endTime = et;
		this.weekdays = wd;
		this.startDate = sd;
		this.endDate = ed;
		/*this.isTba = istba;
		this.isCanceled = isca;
		this.isClosed = iscd;*/
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getWeekdays() {
		return weekdays;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	/*public boolean getIsTba() {
		return isTba;
	}

	public boolean getIsCanceled() {
		return isCanceled;
	}
	
	public boolean getIsClosed() {
		return isClosed;
	}*/
}
