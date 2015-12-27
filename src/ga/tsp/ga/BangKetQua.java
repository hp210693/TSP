package ga.tsp.ga;

import java.util.ArrayList;


public class BangKetQua {
	private ArrayList<Integer> cityList;
	private double fx;// hàm thích nghi
	private double pi;// xác suất chọn pi
	private double qi;// xác suất tích luỹ pi

	public ArrayList<Integer> getCityList() {
		return cityList;
	}

	public void setCityList(ArrayList<Integer> CityList) {
		this.cityList = CityList;
	}

	public double getFx() {
		return fx;
	}

	public void setFx(double fx) {
		this.fx = fx;
	}

	public double getPi() {
		return pi;
	}

	public void setPi(double pi) {
		this.pi = pi;
	}

	public double getQi() {
		return qi;
	}

	public void setQi(double qi) {
		this.qi = qi;
	}

}
