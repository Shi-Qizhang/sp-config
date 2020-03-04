package cn.demo;


import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

public class Demo3 {
    public static void main(String[] args) throws Exception {


        //        1.获取执行环境
        StreamExecutionEnvironment env
                = StreamExecutionEnvironment.getExecutionEnvironment();
        //        2.加载创建初始数据     1|2
//        这里模拟数据流输入
        ArrayList<String> list = new ArrayList<>();
        list.add("红|2");
        list.add("黑|3");
        list.add("红|2");
        list.add("红|5");
        list.add("黑|3");
        DataStreamSource<String> source = env.fromCollection(list).setParallelism(1);
//        也可以这样,可以有两种source,一种处理离线数据source2,一种处理在线source数据,然后最后同时参与计算,得出结果
//        这里仅仅是做一个示范,具体可以去官网查找jion方法
//        ArrayList<String> list2 = new ArrayList<>();
//        list.add("红|2");
//        list.add("黑|3");
//        list.add("红|2");
//        list.add("红|5");
//        list.add("黑|3");
//        DataStreamSource<String> source2 = env.fromCollection(list).setParallelism(1);
        source.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                String[] split = value.split("\\|");
                return new Tuple2<>(split[0], Integer.parseInt(split[1]));
            }
//            调用keby分区求和
        }).keyBy(0).sum(1).print();
        env.execute("Demo3");
    }
}
