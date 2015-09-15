package model;

public class Reserves {
	private String reserve_group;
	private int enrollment_capacity;
	private int enrollment_total;

	public Reserves(String rg, int ec, int et) {
		this.reserve_group = rg;
		this.enrollment_capacity = ec;
		this.enrollment_total = et;
	}

	public String getReserve_group() {
		return reserve_group;
	}

	public int getEnrollment_capacity() {
		return enrollment_capacity;
	}

	public int getEnrollment_total() {
		return enrollment_total;
	}
}
