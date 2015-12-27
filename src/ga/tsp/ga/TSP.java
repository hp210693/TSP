package ga.tsp.ga;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;


public class TSP {
	// danh sách lưu quãng đường đi tốt nhất
	private ArrayList<Integer> dsduongditotnhat;
	// Số cá thể trong quần thể
	private int socathe;
	// danh sách bảng kết quả
	private ArrayList<BangKetQua> dsbangketqua;
	
	// Lấy về quãng đường đi tốt nhất
	public ArrayList<Integer> getDSDDTotNhat() {
		return dsduongditotnhat;
	}
	
	// Lấy về danh sách bảng kết quả
	public ArrayList<BangKetQua> getDSBKetQua() {
		return dsbangketqua;
	}

	/**
	 * @param dstoadothanhpho
	 *            : danh sách lưu toạ độ các thành phố
	 * @param soquanthe
	 *            : số lượng phần tử trong quần thể
	 * @param solanlap
	 *            : số thế hệ lặp
	 */
	public TSP(ArrayList<Point> dstoadothanhpho, int soquanthe, int solanlap) {
		socathe = soquanthe;

		// n số thành phố
		int n = dstoadothanhpho.size();

		// Khởi tạo quần thể
		int[][] quanthe = new int[socathe][n];
		khoitaoquanthe(quanthe, n);

		// Bảng kết quả
		ArrayList<BangKetQua> dsketqua = new ArrayList<>();
		taodl(dsketqua, quanthe, n);

		// Tính độ thích nghi cho bảng kết quả
		tinhdothichnghi(dsketqua, dstoadothanhpho);

		// Lấy vị trí thích nghi tốt nhất trong bảng kết quả
		int vt = vitrithichnghitotnhat(dsketqua);
		// Gán thành phố tốt nhất
		dsbangketqua=dsketqua;
		dsduongditotnhat = dsketqua.get(vt).getCityList();
		double fx = dsketqua.get(vt).getFx();
		hienthi(dsketqua);

		int i = 0;
		do {
			// Chọn lọc cá thể tốt nhất trong quần thể
			ChonLocCaThe clct = new ChonLocCaThe(dsketqua, socathe);
			ArrayList<BangKetQua> dsChonLoc = clct.getdsMoiThanhPho();
			System.out.println("Kết quả của quá trình chọn lọc:");
			hienthi(dsChonLoc);

			// Lai Ghép
			LaiGhep tdc = new LaiGhep(dsChonLoc, socathe);
			ArrayList<BangKetQua> dssaulaighep = tdc.getKetQuaMoi();
			System.out.println("Kết quả của quá trình lai ghép:");
			hienthi(dssaulaighep);

			// Đột biến
			DotBien db = new DotBien(dssaulaighep, dssaulaighep.size());
			ArrayList<BangKetQua> dotbien = db.getKetQuaMoi();
			System.out.println("Kết quả của quá trình đột biến:");
			hienthi(dotbien);

			// Chọn lọc P(t) từ P'(t) Q(t) R(t) và P(t)
			dsketqua = chonQT(dsketqua, dsChonLoc, dssaulaighep, dotbien);
			tinhdothichnghi(dsketqua, dstoadothanhpho);
			System.out.println("Kết quả:");
			hienthi(dsketqua);

			int vt1 = vitrithichnghitotnhat(dsketqua);

			double Fx = dsketqua.get(vt1).getFx();
			if (Fx > fx) {
				dsduongditotnhat = dsketqua.get(vt1).getCityList();
				fx = Fx;
			}
			i++;
		} while (i < solanlap);
	}

	/**
	 * @param dsketqua
	 *            : danh sách kết quả
	 * @param dscacthanhphochonloctuquanthe
	 *            : danh sách các thành phố được chọn lọc n từ thành phố
	 * @param dssaulaighep
	 *            : danh sách sau khi tiến hành lai ghép.
	 * @param dscacthanhphosaudotbien
	 *            : danh sách sau khi tiến hành đột biến.
	 * @return
	 */
	public ArrayList<BangKetQua> chonQT(ArrayList<BangKetQua> dsketqua,
			ArrayList<BangKetQua> dscacthanhphochonloctuquanthe,
			ArrayList<BangKetQua> dssaulaighep,
			ArrayList<BangKetQua> dscacthanhphosaudotbien) {
		ArrayList<BangKetQua> dstatcacacthanhpho = dsketqua;
		dsketqua.clear();

		dstatcacacthanhpho.addAll(dscacthanhphochonloctuquanthe);
		dstatcacacthanhpho.addAll(dssaulaighep);
		dstatcacacthanhpho.addAll(dscacthanhphosaudotbien);

		/*
		 * Lấy giao của danh sách kết quả, danh sách các thành phố được chọn lọc
		 * từ n thành phố, danh sách sau khi lai, danh sách sau khi đột biến.
		 */
		giaoQL(dstatcacacthanhpho);

		if (dstatcacacthanhpho.size() < socathe)
			themCT(dstatcacacthanhpho);

		if (dstatcacacthanhpho.size() > socathe)
			xoaCT(dstatcacacthanhpho);

		return dstatcacacthanhpho;
	}

	/**
	 * hàm xoá thành phố
	 * 
	 * @param dstatcacacthanhpho
	 *            : danh sách của n thành phố
	 */
	public void xoaCT(ArrayList<BangKetQua> dstatcacacthanhpho) {
		sortQT(dstatcacacthanhpho);
		hienthi(dstatcacacthanhpho);
		while (dstatcacacthanhpho.size() != socathe) {
			dstatcacacthanhpho.remove(0);
		}
	}

	/**
	 * hàm sắp xếp các thành phố dựa theo độ thích nghi
	 * 
	 * @param dstatcacacthanhpho
	 *            : danh sách của n thành phố
	 */
	public void sortQT(ArrayList<BangKetQua> dstatcacacthanhpho) {
		for (int i = 0; i < dstatcacacthanhpho.size() - 1; i++)
			for (int j = i + 1; j < dstatcacacthanhpho.size(); j++)
				if (dstatcacacthanhpho.get(i).getFx() > dstatcacacthanhpho.get(
						j).getFx()) {
					BangKetQua temp = dstatcacacthanhpho.get(i);
					dstatcacacthanhpho.set(i, dstatcacacthanhpho.get(j));
					dstatcacacthanhpho.set(j, temp);
				}
	}

	/**
	 * hàm thêm cá thể vào trong quần thể
	 * 
	 * @param dsbangketqua
	 *            : danh sách lưu bảng kết quả của n thành phố
	 */
	public void themCT(ArrayList<BangKetQua> dsbangketqua) {
		// Khởi tạo quần thể
		int n = dsbangketqua.get(0).getCityList().size();
		int[][] quanthe = new int[socathe][n];
		khoitaoquanthe(quanthe, n);

		// danh sách các thành phố
		ArrayList<BangKetQua> dscthanhpho = new ArrayList<>();
		taodl(dscthanhpho, quanthe, n);
		int i = 0;
		while (dsbangketqua.size() != socathe) {
			dsbangketqua.add(dscthanhpho.get(i));
			i++;
		}
	}

	/**
	 * lấy giao của danh sách các thành phố để loại bỏ các thành phố bị trùng
	 * trong quá trình chọn lọc, lai ghép, đột biến
	 * 
	 * @param dsbangketqua
	 *            : danh sách lưu bảng kết quả của n thành phố
	 */
	public void giaoQL(ArrayList<BangKetQua> dsbangketqua) {
		for (int i = 0; i < dsbangketqua.size(); i++)
			for (int j = i + 1; j < dsbangketqua.size(); j++)
				if (dsbangketqua.get(i).getCityList()
						.equals(dsbangketqua.get(j).getCityList()))
					dsbangketqua.remove(j);
	}

	/**
	 * @param dsketqua
	 *            : danh sách lưu bảng kết quả
	 * @return vị trí có độ thích nghi tốt nhất
	 */
	public int vitrithichnghitotnhat(ArrayList<BangKetQua> dsketqua) {
		int vt = 0;
		double fx = 0;
		for (int i = 0; i < dsketqua.size(); i++)
			if (dsketqua.get(i).getFx() > fx) {
				fx = dsketqua.get(i).getFx();
				vt = i;
			}
		return vt;
	}

	/**
	 * hàm hiển thị kết quả
	 * 
	 * @param dsbangketqua
	 *            : danh sách lưu bảng kết quả
	 */
	public void hienthi(ArrayList<BangKetQua> dsbangketqua) {
		for (int i = 0; i < dsbangketqua.size(); i++)
			System.out.println(i + "->" + dsbangketqua.get(i).getCityList()
					+ " | " + dsbangketqua.get(i).getFx() + "|"
					+ dsbangketqua.get(i).getPi() + "|"
					+ dsbangketqua.get(i).getQi());

	}

	/**
	 * Hàm tính Fx và Pi, Qi cho bảng kết quả
	 * 
	 * @param dsbangketqua
	 *            : danh sách lưu bảng kết quả của n thành phố
	 * @param dstdcactp
	 */
	public void tinhdothichnghi(ArrayList<BangKetQua> dsbangketqua,
			ArrayList<Point> dstdcactp) {
		double total_fx = 0;
		for (int i = 0; i < socathe; i++) {
			// Lấy cách di chuyển thành phố
			ArrayList<Integer> dscthanhpho = dsbangketqua.get(i).getCityList();
			// Tính hàm fx
			double fx = (1000000 - eval(dscthanhpho, dstdcactp));
			total_fx += fx;
			// Add vào bảng kết quả
			dsbangketqua.get(i).setFx(fx);
		}

		double qi = 0;
		for (int i = 0; i < socathe; i++) {
			double pi = dsbangketqua.get(i).getFx() / total_fx;
			qi += pi;
			dsbangketqua.get(i).setPi(pi);
			dsbangketqua.get(i).setQi(qi);
		}
	}

	/**
	 * hàm tính độ thích nghi của từng cá thể
	 * 
	 * @param dsthanhpho
	 *            : danh sách lưu bảng kết quả của n thành phố
	 * @param dstoadothanhpho
	 *            : danh sách lưu toạ độ của các thành phố
	 * @return độ thích nghi của một cá thể
	 */
	public double eval(ArrayList<Integer> dsthanhpho,
			ArrayList<Point> dstoadothanhpho) {
		double gx = 0;
		for (int i = 0; i < dsthanhpho.size() - 1; i++) {
			int i1 = dsthanhpho.get(i);
			int i2 = dsthanhpho.get(i + 1);
			gx += TinhKhoangCach(dstoadothanhpho.get(i1),
					dstoadothanhpho.get(i2));
		}
		gx += TinhKhoangCach(dstoadothanhpho.get(dsthanhpho.get(0)),
				dstoadothanhpho.get(dsthanhpho.get(dsthanhpho.size() - 1)));
		return gx;
	}

	/**
	 * hàm tính khoảng cách của hai điểm
	 * 
	 * @param p1
	 *            : điểm đầu
	 * @param p2
	 *            : điểm cuối
	 * @return khoảng cách giữa 2 điểm
	 */
	public double TinhKhoangCach(Point p1, Point p2) {
		double khoangcach = 0;
		double x = p1.getX() - p2.getX();
		double y = p1.getY() - p2.getY();
		khoangcach = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return khoangcach;
	}

	/**
	 * hàm chuyển các thành phố vào trong bảng kết quả
	 * 
	 * @param dsbangketqua
	 *            : danh sách lưu bảng kết quả
	 * @param quanthe
	 *            : mảng 2 chiều lưu danh sách các thành phố
	 * @param n
	 */
	public void taodl(ArrayList<BangKetQua> dsbangketqua, int[][] quanthe, int n) {
		for (int i = 0; i < socathe; i++) {
			BangKetQua bangketqua = new BangKetQua();
			ArrayList<Integer> dsthanhpho = new ArrayList<>();
			for (int j = 0; j < n; j++)
				dsthanhpho.add(quanthe[i][j]);
			bangketqua.setCityList(dsthanhpho);
			dsbangketqua.add(bangketqua);
		}
	}

	/**
	 * hàm khởi tạo quần thể
	 * 
	 * @param quanthe
	 *            : mảng 2 chiều lưu danh sách các thành phố
	 * @param n
	 *            : số thành phố
	 */
	public void khoitaoquanthe(int[][] quanthe, int n) {
		ArrayList<Integer> dsthanhpho = new ArrayList<>();
		for (int i = 0; i < n; i++)
			dsthanhpho.add(i);

		for (int j = 0; j < n; j++)
			quanthe[0][j] = dsthanhpho.get(j);

		for (int i = 1; i < socathe; i++) {
			Collections.shuffle(dsthanhpho);
			for (int j = 0; j < n; j++)
				quanthe[i][j] = dsthanhpho.get(j);
		}
	}
}
