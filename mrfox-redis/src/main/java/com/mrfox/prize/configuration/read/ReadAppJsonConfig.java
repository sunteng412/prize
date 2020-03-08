package com.mrfox.prize.configuration.read;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class ReadAppJsonConfig {

    /*****
     * 读取缓存配置
     * @param
     * @return
     * @description:
     ****/
    public List<CacheInitConfigEntity> readJsonToMap(){
        InputStream is = this.getClass().getResourceAsStream("/cache/config.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s="";
        StringBuilder configContentStr = new StringBuilder();
        try {
            while((s=br.readLine())!=null) {
                configContentStr .append(s);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        return JSON.parseArray(configContentStr.toString(),CacheInitConfigEntity.class);
    }
}
