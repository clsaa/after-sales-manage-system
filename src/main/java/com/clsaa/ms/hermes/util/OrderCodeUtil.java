package com.clsaa.ms.hermes.util;

import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * @author 任贵杰
 */
public class OrderCodeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final Random LOCAL_RANDOM = new Random();

    /**
     * 创建订单编号，订单编号规则: <br/>
     * <p>
     * 8位年月日 +
     * 8位当日不重复随机码
     * </p>
     *
     * @return 15位订单编码
     */
    public static String createOrderCode() {
        String orderId = "";
        orderId += getDate();
        orderId += getRandomCode();
        return orderId;
    }

    private static String getDate() {
        LocalDate localDate = LocalDate.now();
        return localDate.format(FORMATTER);
    }

    private static String getRandomCode() {
        LocalDate localDate = LocalDate.now();
        int i = ((Long) (localDate.atStartOfDay().until(LocalDateTime.now(), ChronoUnit.MILLIS))).intValue();
        i += LOCAL_RANDOM.nextInt(9999999);
        return String.format("%8d",i);
    }

}
