package cn.demo;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class LogStream {
//     这里需要结合一个名为 netcat的插件模拟流数据 帮助测试
    public static void main(String[] args) throws Exception {
//        1.获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        2.加载创建初始数据     1|2|3|4
        DataStreamSource<String> source = env.socketTextStream("localhost", 9999);
        source.flatMap(new FlatMapFunction<String, Integer>() {

            @Override
            public void flatMap(String s, Collector<Integer> collector) throws Exception {
                String[] split=s.split("\\|");
                for (String str : split) {
                    collector.collect(Integer.parseInt(str));
                }

            }
        }).map(new MapFunction<Integer, Integer>() {

            @Override
            public Integer map(Integer integer) throws Exception {
                return integer * 10;
            }
        }).filter(new FilterFunction<Integer>() {
            @Override
            public boolean filter(Integer value) throws Exception {
                return value != 1;
            }
        })
                .print();
        env.execute("LogStream");
//        3.指定数据的转换操作
        //TODO
//        4.指定计算结果存放的位置
//        5.触发程序执行
    }
}
