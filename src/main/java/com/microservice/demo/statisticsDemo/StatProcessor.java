package com.microservice.demo.statisticsDemo;

import java.text.ParseException;
import java.util.Map;

public interface StatProcessor {
    void process(String record) throws ParseException;

    Map<Long, DelayItem> getResult();
}
