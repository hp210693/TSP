package ga.tsp.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//import javax.swing.JOptionPane;

/* Hàm Lai ghép các cá thể của quần thể
 Dùng phép lai OX để lai 2 cá thể */


public class LaiGhep {
	private ArrayList<BangKetQua> dsKetQuaMoi;

	public LaiGhep(ArrayList<BangKetQua> ds_KetQuaMoi, int socathe) {
		dsKetQuaMoi = ds_KetQuaMoi;

		// lưu các vị trí cá thể trong quần thể được lai ghép
		ArrayList<Integer> dsViTri = new ArrayList<>();
		vitricacgen(dsViTri, socathe);
		System.out
				.println("Vị trí cá thể trong quần được lai ghép: " + dsViTri);
		for (int i = 0; i < dsViTri.size() / 2; i++)
			laighep(dsViTri.get(i * 2), dsViTri.get(i * 2 + 1));
	}

	/** hàm thực hiện lai ghép giữa các thành phố
	 * @param vitri1: vị trí lai ghép thứ nhất
	 * @param vitri2: vị trí lai ghép thứ hai
	 */
	public void laighep(int vitri1, int vitri2) {
		// Danh sách bố mẹ
		ArrayList<Integer> p1 = dsKetQuaMoi.get(vitri1).getCityList();
		ArrayList<Integer> p2 = dsKetQuaMoi.get(vitri2).getCityList();

		// Danh sách con
		ArrayList<Integer> c1 = new ArrayList<>();
		ArrayList<Integer> c2 = new ArrayList<>();

		c1 = p1;
		c2 = p2;

		// Chọn ngẫu nhiên 2 vị trí lai ghép(điểm cắt)
		ArrayList<Integer> vitrilaighep = new ArrayList<>();
		vitri_laighep(vitrilaighep, p1.size());
		Collections.sort(vitrilaighep);

		// Tạo 2 danh sách phụ l1, l2
		ArrayList<Integer> l1 = new ArrayList<>();
		ArrayList<Integer> l2 = new ArrayList<>();
		// Add các phần tử trong 2 danh sách p1, p2 lần lượt vào l1, l2 - bắt
		// đầu từ vị trí lai ghép ngẫu nhiên thứ hai
		for (int i = vitrilaighep.get(1); i < p1.size(); i++) {
			l1.add(p1.get(i));
			l2.add(p2.get(i));
		}
		// Add các phần tử trong 2 danh sách p1, p2 lần lượt vào l1, l2 - bắt
		// đầu từ vị trí lai ghép ngẫu nhiên đầu tiên
		for (int i = 0; i < vitrilaighep.get(1); i++) {
			l1.add(p1.get(i));
			l2.add(p2.get(i));
		}

		/*
		 * Xoá các phần tử (đoạn gen) từ vị trí lai ghép đầu tiên đến vị trí lai
		 * ghép ngẫu nhiên thứ hai trong danh sách phụ l1, l2
		 */
		for (int i = vitrilaighep.get(0); i < vitrilaighep.get(1); i++) {
			l1.remove(p2.get(i));
			l2.remove(p1.get(i));
		}

		/*
		 * Set giá trị cho các phần tử trong danh sách con c1, c2 từ vị trí lai
		 * ghép ngẫu nhiên thứ 2 đến phần tử cuối cùng - Xoá các phần tử trong
		 * danh sách l2, l1 từ vị trí lai ghép ngẫu nhiên thứ hai đến phần tử
		 * cuối cùng
		 */
		for (int i = vitrilaighep.get(1); i < c1.size(); i++) {
			c1.set(i, l2.get(0));
			l2.remove(0);
			c2.set(i, l1.get(0));
			l1.remove(0);
		}

		/*
		 * Set giá trị cho các phần tử trong danh sách con c1, c2 từ 0 đến vị
		 * trí lai ghép ngẫu nhiên đầu tiên
		 */
		for (int i = 0; i < vitrilaighep.get(0); i++) {
			c1.set(i, l2.get(i));
			c2.set(i, l1.get(i));
		}

		// Tạo bảng kết quả tp1, tp2 - Thêm vào danh sách kết quả mới
		BangKetQua tp1 = new BangKetQua();
		tp1.setCityList(c1);
		dsKetQuaMoi.add(tp1);
		BangKetQua tp2 = new BangKetQua();
		tp2.setCityList(c2);
		dsKetQuaMoi.add(tp2);

	}

	/**
	 * hàm tạo hai vị trí lai ghép ngẫu nhiên(điểm cắt)
	 * 
	 * @param vitrilaighep
	 *            : danh sách để lưu hàm tạo hai vị trí lai ghép ngẫu nhiên(điểm
	 *            cắt)
	 * @param kichthuoc
	 *            : kích thước của p1 hoặc p2
	 */
	public void vitri_laighep(ArrayList<Integer> vitrilaighep, int kichthuoc) {
		ArrayList<Integer> random = new ArrayList<>();
		for (int i = 1; i < kichthuoc - 1; i++)
			random.add(i);
		Collections.shuffle(random);
		vitrilaighep.add(random.get(0));
		vitrilaighep.add(random.get(1));
		/*
		 * JOptionPane.showMessageDialog(null, "Số điểm phải lớn hơn 3!",
		 * "Thông báo", JOptionPane.ERROR_MESSAGE);
		 */
	}

	/**
	 * hàm tạo các vị trí của các cá thể trong quần thể
	 * 
	 * @param dsViTri
	 *            : danh sách lưu vị trí các cá thể trong quần thể
	 * @param socathe
	 *            : số cá thể trong quần thể
	 */
	public void vitricacgen(ArrayList<Integer> dsViTri, int socathe) {
		Random rnd = new Random();
		while (dsViTri.size() < 2) {
			dsViTri.clear();
			for (int i = 0; i < socathe; i++)
				if (rnd.nextDouble() < 0.25)// 0.25 là xác suất của phép lai
					dsViTri.add(i);
		}

		if (dsViTri.size() % 2 != 0)
			dsViTri.remove(dsViTri.size() - 1);
	}

	public ArrayList<BangKetQua> getKetQuaMoi() {
		return dsKetQuaMoi;
	}

}
