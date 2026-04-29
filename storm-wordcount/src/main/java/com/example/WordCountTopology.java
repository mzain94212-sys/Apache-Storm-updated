package com.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class WordCountTopology {

public static void main(String[] args)
throws Exception {

TopologyBuilder builder = new TopologyBuilder();

builder.setSpout("sentence-spout", new SentenceSpout());

builder.setBolt("split-bolt", new SplitBolt()).shuffleGrouping("sentence-spout");

builder.setBolt("count-bolt", new CountBolt()).fieldsGrouping("split-bolt", new org.apache.storm.tuple.Fields("word"));

Config config=new Config();

LocalCluster cluster= new LocalCluster();

cluster.submitTopology("word-count", config, builder.createTopology());

Thread.sleep(10000);

cluster.shutdown();
}
}
