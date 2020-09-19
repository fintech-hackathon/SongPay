package com.example.myapplication.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;



public class DateUtils {
        //	문자열 (날짜)				=> SimpleDateFormat
        //	yyyy-MM-dd HH:mm:ss			=> yyyy-MM-dd HH:mm:ss
        //	yyyy-MM-dd HH:mm:ss.SSS			=> yyyy-MM-dd HH:mm:ss.SSS
        //
        //	yyyy-MM-dd HH:mm:ssZ			=> yyyy-MM-dd HH:mm:ssX
        //	yyyy-MM-dd HH:mm:ss+09			=> yyyy-MM-dd HH:mm:ssX
        //	yyyy-MM-dd HH:mm:ss+0900		=> yyyy-MM-dd HH:mm:ssX
        //	yyyy-MM-dd HH:mm:ss+09:00		=> yyyy-MM-dd HH:mm:ssXXX
        //	yyyy-MM-dd HH:mm:ssKST			=> yyyy-MM-dd HH:mm:ssZ
        //
        //	yyyy-MM-dd HH:mm:ss.SSSZ		=> yyyy-MM-dd HH:mm:ss.SSSX
        //	yyyy-MM-dd HH:mm:ss.SSS+09		=> yyyy-MM-dd HH:mm:ss.SSSX
        //	yyyy-MM-dd HH:mm:ss.SSS+0900	        => yyyy-MM-dd HH:mm:ss.SSSX
        //	yyyy-MM-dd HH:mm:ss.SSS+09:00	        => yyyy-MM-dd HH:mm:ss.SSSXXX
        //	yyyy-MM-dd HH:mm:ss.SSSKST		=> yyyy-MM-dd HH:mm:ss.SSSZ
        //
        //	yyyy-MM-ddTHH:mm:ssZ			=> yyyy-MM-dd'T'HH:mm:ssX
        //	yyyy-MM-ddTHH:mm:ss+09			=> yyyy-MM-dd'T'HH:mm:ssX
        //	yyyy-MM-ddTHH:mm:ss+0900		=> yyyy-MM-dd'T'HH:mm:ssX
        //	yyyy-MM-ddTHH:mm:ss+09:00		=> yyyy-MM-dd'T'HH:mm:ssX
        //	yyyy-MM-ddTHH:mm:ssKST			=> yyyy-MM-dd'T'HH:mm:ssZ
        //
        //	yyyy-MM-ddTHH:mm:ss.SSSZ		=> yyyy-MM-dd'T'HH:mm:ss.SSSX
        //	yyyy-MM-ddTHH:mm:ss.SSS+09		=> yyyy-MM-dd'T'HH:mm:ss.SSSX
        //	yyyy-MM-ddTHH:mm:ss.SSS+0900	        => yyyy-MM-dd'T'HH:mm:ss.SSSX
        //	yyyy-MM-ddTHH:mm:ss.SSS+09:00	        => yyyy-MM-dd'T'HH:mm:ss.SSSXXX
        //	yyyy-MM-ddTHH:mm:ss.SSSKST		=> yyyy-MM-dd'T'HH:mm:ss.SSSZ
        public static Date parse(String strDate) throws ParseException {
            if (strDate == null || strDate.isEmpty()) {
                throw new ParseException("Empty string", 0);
            }

            StringBuilder sdfSb = new StringBuilder("yyyy-MM-dd");

            if (strDate.length() < 19) { // "yyyy-MM-dd HH:mm:ss".length == 19
                throw new ParseException("Time is needed.", 11);
            }

            if (strDate.charAt(10) == 'T') {
                sdfSb.append("'T'HH:mm:ss");
            } else if (strDate.charAt(10) == ' ') {
                sdfSb.append(" HH:mm:ss");
            } else {
                throw new ParseException("Wrong separator", 10);
            }

            int timezoneIndex;
            // .SSS는 있을 수도 있고 없을 수도 있음, 없는 경우에는 19번째부터 timezone이고

// 있는 경우는 23번째부터 timezone

            if (strDate.substring(19).length() >= 4

                    && Pattern.matches("[.]\\d{3}", strDate.substring(19, 23))) {

                sdfSb.append(".SSS");
                timezoneIndex = 23;
            } else {
                timezoneIndex = 19;
            }

            // Timezone을 요약해보면 Z, +09, +0900은 X로, +09:00은 XXX로, KST는 Z로
            String timezone = strDate.substring(timezoneIndex);
            if (timezone.equals("")) {
                ;
            } else if (timezone.equals("Z")) {
                sdfSb.append("X");
            } else if (Pattern.matches("[+|-]\\d{2}", timezone)) {
                sdfSb.append("X");
            } else if (Pattern.matches("[+|-]\\d{4}", timezone)) {
                sdfSb.append("X");
            } else if (Pattern.matches("[+|-]\\d{2}[:]\\d{2}", timezone)) {
                sdfSb.append("XXX");
            } else if (Pattern.matches("[A-Z]{3}", timezone)) {
                sdfSb.append("Z");
            } else {
                throw new ParseException("Wrong timezone", timezoneIndex);
            }

            SimpleDateFormat sdf = new SimpleDateFormat(sdfSb.toString());

            return sdf.parse(strDate);
        }
    }

