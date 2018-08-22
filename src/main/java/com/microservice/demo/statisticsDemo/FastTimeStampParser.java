package com.microservice.demo.statisticsDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

public class FastTimeStampParser {
    private final SimpleDateFormat sdf;
    private final Map<String, Long> cache = new HashMap<String, Long>();

    public FastTimeStampParser() {
        this("yyyy-MM-dd HH:mm:ss");
    }

    public FastTimeStampParser(String timeStampFormat) {
        SimpleTimeZone stz = new SimpleTimeZone(1, "UTC");
        sdf = new SimpleDateFormat(timeStampFormat);
        sdf.setTimeZone(stz);
    }

    public long parseTimeStamp(String timeStamp) throws ParseException {
        Long cacheValu = cache.get(timeStamp);
        if (null != cacheValu) {
            return cacheValu.longValue();
        }
        long result = 0;
        Date date = null;

        date = sdf.parse(timeStamp);
        result = date.getTime();
        cache.put(timeStamp, Long.valueOf(result));
        return result;
    }

}
