package cn.demo;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Demo1 {
//     flink处理数据的基础5步
    public static void main(String[] args) throws Exception {
//        1.获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        2.加载创建初始数据
        DataStreamSource source = env.socketTextStream("localhost", 9999);
//        3.指定数据的转换操作
        //TODO
//        4.指定计算结果存放的位置
        source.print().setParallelism(1);
//        5.触发程序执行
        env.execute("Demo1");
    }
}
