package com.microservice.demo.requestIdGenerator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class RequestIDGenerator {
    //保存该类的唯一实例
    private final static RequestIDGenerator instance = new RequestIDGenerator();
    private final static short SEQ_UPPER_LIMIT = 999;
    private short sequence = -1;


    //生成循环递增序列号
    public short nextSequence() {
        if (sequence >= SEQ_UPPER_LIMIT) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }

    //生成一个新的request  ID
    public String nextID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String timeStamp = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("000");//整数型不够三位，前部0补足；
        //生成请求序列号
        short sequenceNo = nextSequence();
        return "0049" + timeStamp + df.format(sequenceNo);
    }

    public static RequestIDGenerator getInstance() {
        return instance;
    }
}
