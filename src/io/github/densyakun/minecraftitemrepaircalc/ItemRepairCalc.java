package io.github.densyakun.minecraftitemrepaircalc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ItemRepairCalc {
	public static ItemRepairResult repair(int max_durability, List<Integer> durabilities, ItemRepairCalculationType type) {
		Map<Integer, List<Integer>> pattern = new HashMap<Integer, List<Integer>>();
		List<Integer> a = new ArrayList<Integer>(durabilities);
		Collections.sort(a, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		for (int b = 0; b < a.size();) {
			if (1 < a.size()) {
				boolean c = true;
				for (int d = a.size() - 1; 0 <= d;) {
					int e = a.get(b);
					int f = a.get(d);
					int durability = repair(max_durability, e, f);
					if (durability <= max_durability) {
						List<Integer> values = pattern.get(e);
						if (values == null) {
							values = new ArrayList<Integer>();
						}
						values.add(f);
						pattern.put(e, values);
						a.remove((Object) e);
						a.remove((Object) f);
						c = false;
						break;
					}
					d--;
				}
				if (c) {
					b++;
				}
			} else {
				break;
			}
		}
		if (type == ItemRepairCalculationType.minimum_amount) {
			while (1 < a.size()) {
				for (int b = 0; b < a.size(); b++) {
					if (1 < a.size()) {
						int d = a.get(b);
						int e = a.get(a.size() - 1);
						List<Integer> values = pattern.get(d);
						if (values == null) {
							values = new ArrayList<Integer>();
						}
						values.add(e);
						pattern.put(d, values);
						a.remove((Object) d);
						a.remove((Object) e);
					} else {
						break;
					}
				}
			}
		}
		return new ItemRepairResult(max_durability, durabilities, type, pattern, a.size());
	}
	public static int repair(int max_durability, int durability_a, int durability_b) {
		return durability_a + durability_b + (int) Math.floor(max_durability / 20.0);
	}
}
