package com.perficient.hr.partitioner;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;


public class RangePartitioner implements Partitioner {

	@Override
	public  Map<String,ExecutionContext> partition(int gridSize) {
		int range = 10;
		int fromId = 1;
		int toId = 100;
		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
		for (int i = 1; i <= gridSize; i++) {
			ExecutionContext value = new ExecutionContext();
			value.putInt("fromId", fromId);
			value.putInt("toId", toId);
			value.putString("name", "Thread" + i);
			result.put("partition" + i, value);
			fromId = toId + 1;
			toId += range;
		}
		return result;
	}

}