package com.csair.testlog.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Properties工具类
 *
 * @author LW_Fung
 */
public class PropertiesUtils {

    private PropertiesUtils(){}
    private static final Logger LOGGER = Logger.getLogger(PropertiesUtils.class);

    /**
     * 读取指定配置文件
     *
     * @param filePath：文件路径
     * @param key：键
     * @return 值
     */
    public static String getProperty(String filePath, String key){
        try {
            Properties properties = new Properties();
            properties.load(PropertiesUtils.class.getResourceAsStream(filePath));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return null;
    }

}
