package com.mrfox.prize.mapper;

import com.mrfox.prize.model.Prize;

public interface PrizeMapper {
    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    Prize selectByPrimaryKey(Long id);
}