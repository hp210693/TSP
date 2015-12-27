package ga.tsp.view;

//import ga.tsp.ga.BangKetQua;
import ga.tsp.ga.TSP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;


public class GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField tflanlap, tfquanthe;
	private JTextArea jtapoints, jtaduongdi;
	private JButton btnTienHoa, btnLamLai;
	private JLabel lbllanlap, lblquanthe, lblduongditn;
	private JPanel panelmain, panelEast, panelWest, panelWestCenter,
			panelWestSouth;
	private ArrayList<Point> dspoints;
	private String str = "";
	private Graphics gr;
	private int tp = 1;
 
	public GUI() {
		setTitle("Traveling Salesperson Problem");
		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		dspoints = new ArrayList<>();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				GUI.class.getResource("/Images/images.jpg")));

		createGUI();
	}

	public void createGUI() {
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		Box b6 = Box.createHorizontalBox();
		
		panelmain = new JPanel(new BorderLayout());
		panelmain.setLayout(new BorderLayout());
		panelmain.setBorder(new TitledBorder(BorderFactory
		 .createLineBorder(Color.BLUE), "Giải thuật di truyền - Genetic Algorithm"));
		
		panelWest = new JPanel(new BorderLayout());
		panelWest.setBorder(new TitledBorder(BorderFactory
				.createLineBorder(Color.BLUE), "Click chuột để vẽ điểm"));
		panelWest.setPreferredSize(new Dimension(530, 400));
		panelmain.add(panelWest, BorderLayout.WEST);
		
		panelEast = new JPanel(new BorderLayout());
		panelEast.setBorder(new TitledBorder(BorderFactory
				.createLineBorder(Color.BLUE), "Quần thể"));
		panelEast.setPreferredSize(new Dimension(250, 400));
		panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));
		panelmain.add(panelEast, BorderLayout.EAST);

		panelWestCenter = new JPanel();
		panelWest.add(panelWestCenter, BorderLayout.CENTER);

		panelWestSouth = new JPanel();
		panelWest.add(panelWestSouth, BorderLayout.SOUTH);
		panelWestSouth
				.setLayout(new BoxLayout(panelWestSouth, BoxLayout.Y_AXIS));

		lbllanlap = new JLabel("Lần lặp");
		tflanlap = new JTextField();
		tflanlap.setText("250");
		b4.add(Box.createHorizontalStrut(10));
		b4.add(lbllanlap);
		b4.add(Box.createHorizontalStrut(10));
		b4.add(tflanlap);
		b4.add(Box.createHorizontalStrut(10));

		lblquanthe = new JLabel("Quần thể");
		tfquanthe = new JTextField();
		tfquanthe.setText("500");
		b5.add(Box.createHorizontalStrut(10));
		b5.add(lblquanthe);
		b5.add(Box.createHorizontalStrut(10));
		b5.add(tfquanthe);
		b5.add(Box.createHorizontalStrut(10));

		jtapoints = new JTextArea(30, 15);
		b6.add(new JScrollPane(jtapoints));

		btnTienHoa = new JButton("Tiến Hoá");
		btnLamLai = new JButton("Làm lại");

		b1.add(btnTienHoa);
		b1.add(Box.createHorizontalStrut(15));
		b1.add(btnLamLai);

		btnTienHoa.addActionListener(this);
		btnLamLai.addActionListener(this);

		lblduongditn = new JLabel("Đường đi tốt nhất qua các thành phố");
		b2.add(lblduongditn);

		jtaduongdi = new JTextArea(3, 3);
		b3.add(new JScrollPane(jtaduongdi));

		panelWestCenter.add(Box.createVerticalStrut(10));
		panelWestSouth.add(b1);
		panelWestSouth.add(b2);
		panelWestSouth.add(b3);

		panelEast.add(Box.createVerticalStrut(20));
		panelEast.add(b4);
		panelEast.add(Box.createVerticalStrut(20));
		panelEast.add(b5);
		panelEast.add(Box.createVerticalStrut(20));
		panelEast.add(b6);
		panelEast.add(Box.createVerticalStrut(10));
		
		panelWestCenter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gr = panelWestCenter.getGraphics();
				panelWestCenter.update(gr);
				
				Point click = e.getPoint();
				dspoints.add(click);
				vediem(dspoints);
				str += "Thành phố " + tp + ": (" + (int) click.getX() + ", "
						+ (int) click.getY() + ")\n";
				jtapoints.setText(str);
				tp++;
			
			}
		});
		panelWestCenter.setBackground(new Color(0, 206, 209));
		panelWestCenter.setForeground(new Color(175, 238, 238));
		panelWestCenter.setBorder(UIManager.getBorder("TitledBorder.border"));
		jtapoints.setEditable(false);
		jtaduongdi.setEditable(false);
		lbllanlap.setPreferredSize(lblquanthe.getPreferredSize());
		this.add(panelmain);
	}
	
	/**
	 * @param dspoints:ArrayList chứa các điểm cần vẽ
	 */
	public void vediem(ArrayList<Point> dspoints) {
		int count = 0;
		for (Point click : dspoints) {
			
			count++;
			gr.setColor(Color.YELLOW);
			gr.setFont(new Font("Times New Roman", Font.BOLD, 50));
			gr.drawString(count + "", (int) click.getX(), (int) click.getY());
			gr.setColor(Color.RED);
			gr.fillOval((int) click.getX(), (int) click.getY(), 9, 9);
			gr.setColor(Color.BLACK);
			gr.finalize();
		}
	}

	public static void main(String[] args) throws Exception {
		//UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
		new GUI().setVisible(true);
	}

	// Lấy thành phố tốt nhất rồi nối lại thành đường thẳng
	public void NoiDuongDI(ArrayList<Integer> dsthanhphototnhat) {
		for (int i = 0; i < dsthanhphototnhat.size() - 1; i++) {
			int x1 = (int) dspoints.get(dsthanhphototnhat.get(i)).getX();
			int y1 = (int) dspoints.get(dsthanhphototnhat.get(i)).getY();
			int x2 = (int) dspoints.get(dsthanhphototnhat.get(i + 1)).getX();
			int y2 = (int) dspoints.get(dsthanhphototnhat.get(i + 1)).getY();
			gr.drawLine(x1, y1, x2, y2);
		}

		int x11 = (int) dspoints.get(dsthanhphototnhat.get(0)).getX();
		int y11 = (int) dspoints.get(dsthanhphototnhat.get(0)).getY();
		int x21 = (int) dspoints.get(
				dsthanhphototnhat.get(dsthanhphototnhat.size() - 1)).getX();
		int y21 = (int) dspoints.get(
				dsthanhphototnhat.get(dsthanhphototnhat.size() - 1)).getY();
		gr.drawLine(x11, y11, x21, y21);
	}

	public void chuyengd1() {
		tflanlap.setEditable(false);
		tfquanthe.setEditable(false);
		btnTienHoa.setEnabled(false);
		btnLamLai.setEnabled(false);
	}

	public void chuyengd2() {
		tflanlap.setEditable(true);
		tfquanthe.setEditable(true);
		btnTienHoa.setEnabled(true);
		btnLamLai.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		JButton btn = (JButton) o;
		String path = "";

		if (btn.equals(btnTienHoa)) {
			// Gọi Class TSP
			jtaduongdi.setText("");
			int soquanthe = Integer.parseInt(tfquanthe.getText().trim());
			int solanlap = Integer.parseInt(tflanlap.getText().trim());
			TSP tsp = new TSP(dspoints, soquanthe, solanlap);
			ArrayList<Integer> dsduongditotnhat = tsp.getDSDDTotNhat();
//			ArrayList<BangKetQua> dsbangketqua = tsp.getDSBKetQua();
//			System.out.println(dsbangketqua.size());
			NoiDuongDI(dsduongditotnhat);

			for (int i = 0; i < dsduongditotnhat.size(); i++) {
				chuyengd1();
				if (i != dsduongditotnhat.size() - 1)
					path += (dsduongditotnhat.get(i) + 1) + " -> ";
				else {
					path += (dsduongditotnhat.get(i) + 1);
				}
			}
			chuyengd2();
			jtaduongdi.setText(path);
		} else {
			panelWestCenter.repaint();
			dspoints.clear();
			jtapoints.setText("");
			jtaduongdi.setText("");
			str = "";
			tp = 1;
		}
	}

}
