package com.example;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SentenceSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    String[] sentences = {
            "hello storm hello",
            "apache storm tutorial",
            "storm is a distributed realtime computation system",
            "storm is simple and can be used with any programming language"
    };

    int index = 0;

    public void open(Map conf, TopologyContext context,
                     SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {

        if(index < sentences.length){
            collector.emit(new Values(sentences[index]));
            index++;
        }

        try{
            Thread.sleep(1000);
        }catch(Exception e){}
    }

    public void declareOutputFields(
            OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("sentence"));
    }
}
