package com.example;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.*;
import java.util.Map;

public class SplitBolt extends BaseRichBolt {
    private OutputCollector collector;

    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        try {
            // Kafka default field name is "value"
            String sentence = input.getStringByField("value");

            if (sentence != null) {
                for (String word : sentence.split(" ")) {
                    // Anchoring the new word tuple to the input tuple
                    collector.emit(input, new Values(word));
                }
            }
            // Acknowledge the tuple is processed
            collector.ack(input);
        } catch (Exception e) {
            // Fail the tuple so Kafka can replay it
            collector.fail(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}