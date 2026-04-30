package com.example;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import java.util.HashMap;
import java.util.Map;

public class CountBolt extends BaseRichBolt {
    private OutputCollector collector;
    private Map<String, Integer> counts;

    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        this.counts = new HashMap<>();
    }

    @Override
    public void execute(Tuple input) {
        try {
            String word = input.getStringByField("word");
            
            // Logic to increment local count
            int count = counts.getOrDefault(word, 0) + 1;
            counts.put(word, count);

            // Displaying output to console
            System.out.println("RESULT: " + word + " = " + count);

            collector.ack(input);
        } catch (Exception e) {
            collector.fail(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // No further bolts in the chain
    }
}