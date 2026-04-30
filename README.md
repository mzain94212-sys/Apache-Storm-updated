# Apache Storm Word Count Topology

## Overview
A simple real-time word count project built using Apache Storm to demonstrate stream processing fundamentals.

### Data Flow

SentenceSpout -> SplitBolt -> CountBolt

### Example

Input:


hello storm hello


Output:


hello = 2
storm = 1




## Features

* Custom Spout for sentence generation
* Bolt for splitting words
* Bolt for counting words
* Shuffle and Fields grouping
* Local Storm topology execution


## Project Structure

```text
src/main/java/com/example/
├── SentenceSpout.java
├── SplitBolt.java
├── CountBolt.java
└── WordCountTopology.java
```



## File Purpose

**SentenceSpout**
Emits sentences into the stream.

**SplitBolt**
Splits sentences into individual words.

**CountBolt**
Counts occurrences of each word.

**WordCountTopology**
Connects and runs the topology.

## Tech Stack

* Java
* Apache Storm
* Maven
* Ubuntu Linux


## Run

```bash
mvn clean package
mvn exec:java -Dexec.mainClass="com.example.WordCountTopology"
```

## Concepts Practiced

* Spouts
* Bolts
* Tuples
* Collectors
* Stream Groupings
* Real-time Processing

# Update
Use Kafka to send data.

## Flow:
Kafka Topic (Sentences) $\rightarrow$ KafkaSpout (Read) $\rightarrow$ SplitBolt (Split into Words) $\rightarrow$ CountBolt (Update Count) $\rightarrow$ Console (Display)



