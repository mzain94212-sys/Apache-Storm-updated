package com.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;

public class WordCountTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        // Kafka Configuration: listening to 'localhost:9092' and topic 'sentences'
        KafkaSpoutConfig<String, String> kafkaConfig = 
                KafkaSpoutConfig.builder("localhost:9092", "sentences")
                        .setProp(ConsumerConfig.GROUP_ID_CONFIG, "storm-group")
                        .build();

        // 1. Kafka Spout
        builder.setSpout("kafka-spout", new KafkaSpout<>(kafkaConfig));

        // 2. Split Bolt (Shuffle Grouping)
        builder.setBolt("split-bolt", new SplitBolt())
                .shuffleGrouping("kafka-spout");

        // 3. Count Bolt (Fields Grouping on "word")
        builder.setBolt("count-bolt", new CountBolt())
                .fieldsGrouping("split-bolt", new Fields("word"));

        Config config = new Config();
        config.setNumWorkers(2);

        // Local Cluster for testing on your machine
        try (LocalCluster cluster = new LocalCluster()) {
            cluster.submitTopology("kafka-word-count", config, builder.createTopology());
            
            // Run for 60 seconds then shut down
            Thread.sleep(60000);
        }
    }
}