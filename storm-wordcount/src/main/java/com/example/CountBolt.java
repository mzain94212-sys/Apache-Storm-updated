package com.example;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class CountBolt extends BaseRichBolt {

    private OutputCollector collector;
    Map<String,Integer> counts = new HashMap<>();

    @Override
    public void prepare(
            Map conf,
            TopologyContext context,
            OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String word = input.getStringByField("word");

        counts.put(
                word,
                counts.getOrDefault(word, 0) + 1
        );

        System.out.println(
                word + " = " + counts.get(word)
        );
        collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // No output fields
    }
}
