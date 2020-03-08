package com.mrfox.prize.pattern.prize.strategy.select;

import com.mrfox.prize.utils.SpringContextHolder;
import com.mrfox.prize.vo.ActivePrizeDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
public class SelectHandle {

    private String defaultBeanName;

    @PostConstruct
    public void init(){
        //1、扫描实现类 ,Reflections 依赖 Google 的 Guava 库和 Javassist 库
        Reflections reflections = new Reflections("com.mrfox.prize.pattern.prize.strategy.select");
        //2、获取该路径下所有包含Strategy注解的类
        Set<Class<?>> classList = reflections.getTypesAnnotatedWith(SPI.class);
        for (Class clazz : classList) {
            if(clazz.isInterface()){
                SPI spi = (SPI) clazz.getAnnotation(SPI.class);
                defaultBeanName = spi.value();
            }
        }
    }

    /*****
     * 抽奖
     * @param
     * @return
     * @description:
     ****/
    public SelectResult select(String extension, List<ActivePrizeDetailVo> activePrizeDetailVoList){
            return ((DrawSelect) SpringContextHolder.getBean(StringUtils.isBlank(extension)?defaultBeanName:extension)).select(activePrizeDetailVoList);
    }
}
