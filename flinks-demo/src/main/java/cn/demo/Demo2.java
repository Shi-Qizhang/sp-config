package cn.demo;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;

import java.util.ArrayList;
import java.util.List;

public class Demo2 {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        List<String> list = new ArrayList<>();
        list.add("hadoop");
        list.add("flume");
        DataSource<String> source = env.fromCollection(list);
        source.print();
    }
}
