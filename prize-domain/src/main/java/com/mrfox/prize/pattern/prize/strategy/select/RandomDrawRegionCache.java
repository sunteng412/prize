package com.mrfox.prize.pattern.prize.strategy.select;

import lombok.Data;

import java.util.List;

/*****
 * 随机概率实体
 * @author     : MrFox
 * @date       : 2020-01-29 21:30
 * @description:
 * @version    :
 ****/
@Data
public class RandomDrawRegionCache {
    private List<RandomDrawRegion> randomDrawRegionList;
    private Integer notWin;
}
