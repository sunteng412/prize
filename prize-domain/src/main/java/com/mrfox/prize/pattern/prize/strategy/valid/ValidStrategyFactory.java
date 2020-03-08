package com.mrfox.prize.pattern.prize.strategy.valid;

import com.mrfox.prize.dto.PrizeDrawDto;
import com.mrfox.prize.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/*****
 * 触发类工厂
 * @author     : MrFox
 * @date       : 2020-01-27 15:56
 * @description:
 * @version    :
 ****/
@Slf4j
@Component
public class ValidStrategyFactory {

    /**
     * 存储所有的策略
     */
    private static HashMap<String, Class> validStrategyHashMap = new HashMap<>();

    /**
     * 按顺序存储策略名
     */
    private static List<ValidStrategySort> validStrategySorts = new ArrayList<>();

    @PostConstruct
    public void init(){
        //1、扫描实现类 ,Reflections 依赖 Google 的 Guava 库和 Javassist 库
        Reflections reflections = new Reflections("com.mrfox.prize.pattern.prize.strategy.valid.impl");
        //2、获取该路径下所有包含Strategy注解的类
        Set<Class<?>> classList = reflections.getTypesAnnotatedWith(Strategy.class);
        for (Class clazz : classList) {
            Strategy strategy = (Strategy) clazz.getAnnotation(Strategy.class);
            //3、存储所有的策略
                validStrategyHashMap.put(strategy.modelName(),clazz);
                validStrategySorts.add(new ValidStrategySort(strategy.sort(), strategy.modelName()));

        }

        validStrategySorts.sort(Comparator.comparingInt(o -> o.sort));
    }

    @PreDestroy
    public void destroy(){
            //3、存储所有的策略
            validStrategyHashMap.clear();
            validStrategySorts.clear();
    }


    /*****
     * 构建检验参数
     * @param
     * @return
     * @description:
     ****/
    public PrizeDrawRequest buildValid(PrizeDrawDto prizeDrawDto) {

        log.info("[ValidStrategyFactory.buildValidMap]prizeDrawDto:{}", prizeDrawDto);
        PrizeDrawRequest prizeDrawRequest = new PrizeDrawRequest();
        for (ValidStrategySort e : validStrategySorts) {
            ValidStrategy validStrategy = (ValidStrategy) SpringContextHolder.getBean(validStrategyHashMap.get(e.getValidStrategyName()));
            validStrategy.buildValid(prizeDrawDto, prizeDrawRequest);
            if (Objects.nonNull(prizeDrawRequest.getError())) {
                break;
            }
        }
        return prizeDrawRequest;
    }

}
