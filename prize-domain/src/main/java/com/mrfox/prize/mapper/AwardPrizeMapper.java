package com.mrfox.prize.mapper;

import com.mrfox.prize.model.AwardPrize;
import org.apache.ibatis.annotations.Param;

public interface AwardPrizeMapper {
    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    AwardPrize selectByPrimaryKey(Long id);

   Integer updatePrizeRemainNum(@Param("awardId") Long awardId);
}