package cn.mypandora.springboot.core.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

/**
 * @author hankaibo
 * @date 2020/8/5
 */
public class String2LocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String s) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.parse(s, fmt);
    }
}