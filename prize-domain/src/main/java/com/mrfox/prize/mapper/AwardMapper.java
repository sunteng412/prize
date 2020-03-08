package com.mrfox.prize.mapper;

import com.mrfox.prize.model.Award;
import org.apache.ibatis.annotations.Param;

public interface AwardMapper {
    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Award selectByPrimaryKey(Long id);

    Integer updateIssueNum(@Param("id") Long id);
}