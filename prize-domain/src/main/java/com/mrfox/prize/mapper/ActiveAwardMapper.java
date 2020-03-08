package com.mrfox.prize.mapper;

import com.mrfox.prize.model.ActiveAward;
import org.apache.ibatis.annotations.Param;

public interface ActiveAwardMapper {
    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ActiveAward selectByPrimaryKey(Integer id);

    Integer updateAwardRealNum(@Param("awardId")Long awardId,@Param("activeId")Long activeId);
}