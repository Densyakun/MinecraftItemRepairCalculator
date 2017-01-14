package io.github.densyakun.minecraftitemrepaircalc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class Main/* implements Runnable, WindowListener*/ {
	//複数のアイテムを修理した後の総耐久度が最も高い修理パターンを計算するプログラム
	public static String ver = "1.0";
	public static void main(String[] args) {
		System.out.println("Minecraft Item Repair Calculator. ver" + ver);
		System.out.println("Created by Densyakun. \nCopyright (C) 2016 Densyakun All Rights Reserved.");
		/*boolean nogui = false;
		for (int a = 0; a < args.length; a++) {
			if (args[a].equalsIgnoreCase("nogui")) {
				nogui = true;
			}
		}
		new Main(nogui);
	}
	private int max_durability = 0;
	private List<Integer> durabilities = new ArrayList<Integer>();
	public Main(boolean nogui) {
		if (!nogui) {
			Frame frame = new Frame("Minecraft Item Repair Calculator. ver" + ver);
			frame.setResizable(false);
			Panel panel = new Panel(null);
			TextField add_textfield = new TextField();
			TextArea durabilities_textarea = new TextArea();
			TextArea result_textarea = new TextArea();
			Button add_button = new Button("追加");
			Button clear_button = new Button("初期化");
			Button calc_button = new Button("計算");
			add_button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						int value = Integer.valueOf(add_textfield.getText());
						if (max_durability == 0) {
							max_durability = value <= 0 ? 0 : value;
							System.out.println("max durability: " + max_durability);
						} else {
							durabilities.add(value);
							System.out.println("added item durability: " + value);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					add_textfield.setText("");
				}
			});
			durabilities_textarea.setEditable(false);
			result_textarea.setEditable(false);
			add_textfield.setBounds(560 - 96, 0, 96, 24);
			durabilities_textarea.setBounds(0, 24, 280, 480 - 24);
			result_textarea.setBounds(280, 24, 280, 480 - 24);
			add_button.setBounds(640 - 80, 0, 80, 24);
			clear_button.setBounds(640 - 80, 24, 80, 24);
			calc_button.setBounds(640 - 80, 48, 80, 24);
			panel.add(add_textfield);
			panel.add(durabilities_textarea);
			panel.add(result_textarea);
			panel.add(add_button);
			panel.add(clear_button);
			panel.add(calc_button);
			frame.add(panel);
			panel.setPreferredSize(new Dimension(640, 480));
			frame.pack();
			frame.addWindowListener(this);
			frame.setVisible(true);
		}
		new Thread(this).start();
	}
	public void setMax_durability(int max_durability) {
		this.max_durability = max_durability;
	}
	public int getMax_durability() {
		return max_durability;
	}
	public void setDurabilities(List<Integer> durabilities) {
		this.durabilities = durabilities;
	}
	public List<Integer> getDurabilities() {
		return durabilities;
	}
	@Override
	public void run() {*/

		int max_durability = 0;
		List<Integer> durabilities = new ArrayList<Integer>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String line = br.readLine();
				ItemRepairCalculationType type = null;
				if (line.equalsIgnoreCase("repair")) {
					type = ItemRepairCalculationType.total_durability;
				} else if (line.equalsIgnoreCase("repairall")) {
					type = ItemRepairCalculationType.minimum_amount;
				} else if (line.equalsIgnoreCase("clear")) {
					durabilities.clear();
					max_durability = 0;
					System.out.println("Item List Cleared.");
				} else if (line.equalsIgnoreCase("exit")) {
					System.exit(0);
				} else {
					try {
						int value = Integer.valueOf(line);
						if (max_durability == 0) {
							max_durability = value <= 0 ? 0 : value;
							System.out.println("max durability: " + max_durability);
						} else {
							durabilities.add(value);
							System.out.println("added item durability: " + value);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				if (type != null) {
					System.out.println("durabilities: " + durabilities.size());
					System.out.println("max durability: " + max_durability);
					System.out.println("calculating...");
					ItemRepairResult result = ItemRepairCalc.repair(max_durability, durabilities, type);
					System.out.println("calculated.");
					System.out.println("-- ---- ---- ---- --");
					System.out.println("result: ");
					System.out.println("");
					Map<Integer, List<Integer>> pattern = result.getPattern();
					Iterator<Integer> keys = pattern.keySet().iterator();
					while (keys.hasNext()) {
						Integer key = keys.next();
						Iterator<Integer> values = pattern.get(key).iterator();
						while (values.hasNext()) {
							Integer value = values.next();
							System.out.println("repair: " + key + " + " + value + " = " + ItemRepairCalc.repair(max_durability, key, value));
						}
					}
					System.out.println("");
					int old_repair_total_durability = result.getOld_repair_total_durability();
					int repair_total_durability = result.getRepair_total_durability();
					int notrepaired = result.getNotrepaired();
					int bonus = repair_total_durability - old_repair_total_durability;
					System.out.println("subtotal durability: " + repair_total_durability + "(" + (bonus < 0 ? -bonus + " down)" : bonus + " up)"));
					System.out.println("total durability: " + (result.getOld_total_durability() - old_repair_total_durability + repair_total_durability) + "/" + max_durability * durabilities.size());
					System.out.println("not repaired: " + notrepaired);
					System.out.println("-- ---- ---- ---- --");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/*@Override
	public void windowActivated(WindowEvent arg0) {
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		System.exit(0);
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		arg0.getWindow().dispose();
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
	}*/
}
