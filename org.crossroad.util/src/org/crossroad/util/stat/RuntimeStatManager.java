/**
 * 
 */
package org.crossroad.util.stat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.crossroad.util.time.TimeUtil;

/**
 * @author e.soden
 *
 */
public final class RuntimeStatManager {
	private static final RuntimeStatManager instance = new RuntimeStatManager();

	private Map<String, List<RuntimeStat>> map = new TreeMap<String, List<RuntimeStat>>();
	private Map<String, String> keyMapper = new TreeMap<String, String>();

	private RuntimeStat overall = new RuntimeStat();

	private Properties properties = new Properties();

	private NumberFormat f = new DecimalFormat("0000");
	private int stepCount = 0;

	/**
	 * 
	 */
	private RuntimeStatManager() {

	}

	public void addCustomInfo(String key, String value) {
		this.properties.put(key, value);

	}

	public static RuntimeStatManager getInstance() {
		return instance;
	}

	public void startOverall() {
		overall.markStart();
	}

	public void stopOverall() {
		overall.markEnd();
	}

	public void addSubStep(String id, RuntimeStat stat) {

		if (keyMapper.containsKey(id)) {
			map.get(keyMapper.get(id)).add(stat);
		} else {
			List<RuntimeStat> list = new ArrayList<RuntimeStat>();
			list.add(stat);

			String k = f.format(stepCount) + '_' + id;
			map.put(k, list);
			keyMapper.put(id, k);
			stepCount++;
		}

		
	}

	public String displayStat() {

		NumberFormat f = new DecimalFormat("###,##0.0000");
		String lineSeparator = System.getProperty("line.separator");

		StringBuffer buffer = new StringBuffer();

		buffer.append(lineSeparator);
		

		for (String key : map.keySet()) {
			List<RuntimeStat> list = map.get(key);
			int statSize = list.size();
			long usedMemory = 0L;
			long spentTime = 0L;

			for (RuntimeStat stat : list) {
				usedMemory += stat.getConsumeMemory();
				spentTime += stat.getSpendTime();
			}

			buffer.append("Step ").append(key.replace("_", " - ")).append(lineSeparator);
			buffer.append("\t- Steps #").append(statSize).append(lineSeparator);
			buffer.append("\t- AVG Memory usage: " + f.format((double) (usedMemory) / (double) (1024 * 1024)) + "MB")
					.append(lineSeparator);
			buffer.append("\t- AVG Time spent: " + TimeUtil.convertMillis((spentTime / statSize)))
					.append(lineSeparator);

		}
		
		buffer.append(lineSeparator);
		buffer.append("Overall execution: ").append(lineSeparator);
		buffer.append(
				"\t- Memory usage: " + f.format((double) (overall.getConsumeMemory()) / (double) (1024 * 1024)) + "MB")
				.append(lineSeparator);
		buffer.append("\t- Time spent: " + TimeUtil.convertMillis(overall.getSpendTime())).append(lineSeparator);

		if (!this.properties.isEmpty()) {
			for (Object key : this.properties.keySet()) {
				buffer.append("\t- " + key + ": " + this.properties.getProperty(key.toString())).append(lineSeparator);

			}
		}

		return buffer.toString();
	}
}
