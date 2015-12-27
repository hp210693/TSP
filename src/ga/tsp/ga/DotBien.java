package ga.tsp.ga;

import java.util.ArrayList;
import java.util.Random;


public class DotBien {
	private ArrayList<BangKetQua> dsKetQuaMoi;

	public ArrayList<BangKetQua> getKetQuaMoi() {
		return dsKetQuaMoi;
	}

	/**	hàm constructor DotBien
	 * @param ds_KetQuaMoi: danh sách lưu kết quả mới
	 * @param socathe: số cá thể trong quần thể
	 */
	public DotBien(ArrayList<BangKetQua> ds_KetQuaMoi, int socathe) {
		dsKetQuaMoi = ds_KetQuaMoi;
		// số thành phố
		int n = ds_KetQuaMoi.get(0).getCityList().size();
		// danh sách các điểm đột biến - lưu lại các điểm đột biến có pm<0.01
		ArrayList<Integer> dsDiemDotBien = new ArrayList<>();
		Random rnd = new Random();
		for (int i = 0; i < socathe * n; i++)
			if (rnd.nextDouble() < 0.01)
				dsDiemDotBien.add(i);

		if (dsDiemDotBien.size() > 0) {
			for (int i = 0; i < dsDiemDotBien.size(); i++) {
				// lấy ra điểm lai trong quần thể
				int p = dsDiemDotBien.get(i);
				// Hàng
				int h = p / n;
				// Vị trí đột biến đầu tiên
				int vitri1 = p % n;
				// Vị trí đột biến thứ hai
				int vitri2 = getVitri(vitri1, n);

				ArrayList<Integer> dsVitri1 = ds_KetQuaMoi.get(h)
						.getCityList();
				ArrayList<Integer> dsVitri2 = dsKetQuaMoi.get(h)
						.getCityList();

				int vtri1 = dsVitri1.get(vitri1);
				int vtri2 = dsVitri1.get(vitri2);

				dsVitri2.set(vitri1, vtri2);
				dsVitri2.set(vitri2, vtri1);
				dsKetQuaMoi.get(h).setCityList(dsVitri2);
			}
		}
	}

	/** hàm tìm vị trí đột biến thứ 2 
	 * @param vitri: vị trí đột biến đầu tiên
	 * @param n: số thành phố trong danh sách
	 * @return
	 */
	public int getVitri(int vitri, int n) {
		Random rnd = new Random();
		ArrayList<Integer> ds = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			int m=rnd.nextInt(n);
			if (m != vitri)
				ds.add(m);
		}
		return ds.get(10);//trả về giá trị tại vị trí số 10
	}

}
