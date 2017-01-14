package io.github.densyakun.minecraftitemrepaircalc;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class ItemRepairResult {
	private int max_durability;
	private List<Integer> durabilities;
	private ItemRepairCalculationType type;
	private Map<Integer, List<Integer>> pattern;
	private int notrepaired;
	private int old_total_durability = -1;
	private int old_repair_total_durability = -1;
	private int repair_total_durability = -1;
	public ItemRepairResult(int max_durability, List<Integer> durabilities, ItemRepairCalculationType type, Map<Integer, List<Integer>> pattern, int notrepaired) {
		this.max_durability = max_durability;
		this.durabilities = durabilities;
		this.type = type;
		this.pattern = pattern;
		this.notrepaired = notrepaired;
	}
	public int getMax_durability() {
		return max_durability;
	}
	public List<Integer> getDurabilities() {
		return durabilities;
	}
	public ItemRepairCalculationType getType() {
		return type;
	}
	public Map<Integer, List<Integer>> getPattern() {
		return pattern;
	}
	public int getNotrepaired() {
		return notrepaired;
	}
	public int getOld_total_durability() {
		if (old_total_durability == -1) {
			old_total_durability = 0;
			Iterator<Integer> b = durabilities.iterator();
			while (b.hasNext()) {
				old_total_durability += b.next();
			}
		}
		return old_total_durability;
	}
	public int getOld_repair_total_durability() {
		if (old_repair_total_durability == -1) {
			old_repair_total_durability = 0;
			Iterator<Integer> keys = pattern.keySet().iterator();
			while (keys.hasNext()) {
				Integer key = keys.next();
				Iterator<Integer> values = pattern.get(key).iterator();
				while (values.hasNext()) {
					old_repair_total_durability += key + values.next();
				}
			}
		}
		return old_repair_total_durability;
	}
	public int getRepair_total_durability() {
		if (repair_total_durability == -1) {
			repair_total_durability = 0;
			Iterator<Integer> keys = pattern.keySet().iterator();
			while (keys.hasNext()) {
				Integer key = keys.next();
				Iterator<Integer> values = pattern.get(key).iterator();
				while (values.hasNext()) {
					repair_total_durability += Math.min(ItemRepairCalc.repair(max_durability, key, values.next()), max_durability);
				}
			}
		}
		return repair_total_durability;
	}
}
