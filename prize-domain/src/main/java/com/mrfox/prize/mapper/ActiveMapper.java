package com.mrfox.prize.mapper;

import com.mrfox.prize.model.Active;import com.mrfox.prize.vo.PrizeDrawDetailVo;
import org.apache.ibatis.annotations.Param;

public interface ActiveMapper {
    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Active selectByPrimaryKey(Long id);

    /*****
     * 获取抽奖活动详情
     * @param
     * @return
     * @description:
     ****/
    PrizeDrawDetailVo selectPrizeDrawDetail(@Param("activeId") Long activeId);
}