package ga.tsp.ga;

import java.util.ArrayList;
import java.util.Random;


public class ChonLocCaThe {
	private ArrayList<BangKetQua> dsketqua;

	public ArrayList<BangKetQua> getdsMoiThanhPho() {
		return dsketqua;
	}
	
	/** hàm constructor ChonLocQuanThe
	 * @param dsketquamoi: danh sách lưu kết quả của quá trình chọn lọc
	 * @param socathe: số cá thể trong quần thể
	 */
	public ChonLocCaThe(ArrayList<BangKetQua> dsketquamoi, int socathe) {
		// danh sách lưu lại thông tin chọn lọc trong quá trình quay rulet
		dsketqua = new ArrayList<>();
		// lưu giá trị các lần quay rulet
		double[] arr_rulet = new double[socathe];
		rulet(arr_rulet);
		//kiểm tra vị
		for (int i = 0; i < arr_rulet.length; i++)
			dsketqua.add(ChonLoc(arr_rulet[i], dsketquamoi, socathe));

	}

	/** hàm chọn lọc cá thể có xác suất tích luỹ pi
	 * @param d: cá thể trong quần thể
	 * @param dsketquamoi: danh sách lưu kết quả của quá trình chọn lọc
	 * @param socathe: số cá thể trong quần thể
	 * @return: đối tượng bảng kết quả
	 */
	public BangKetQua ChonLoc(double d, ArrayList<BangKetQua> dsketquamoi,
			int socathe) {
		BangKetQua tp = dsketquamoi.get(0);
		for (int i = 0; i < socathe - 1; i++) {
			double l1 = dsketquamoi.get(i).getQi();
			double l2 = dsketquamoi.get(i + 1).getQi();
			if (l1 < d && d <= l2)
				tp = dsketquamoi.get(i + 1);
		}
		return tp;
	}

	/** hàm quay rulet
	 * @param arr_rulet: mảng lưu giá trị các lần quay rulet
	 */
	public void rulet(double[] arr_rulet) {
		Random rnd = new Random();
		for (int i = 0; i < arr_rulet.length; i++)
			arr_rulet[i] = rnd.nextDouble();
	}

}
