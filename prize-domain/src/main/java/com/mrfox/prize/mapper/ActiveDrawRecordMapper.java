package com.mrfox.prize.mapper;

import com.mrfox.prize.model.ActiveDrawRecord;

public interface ActiveDrawRecordMapper {
    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(ActiveDrawRecord record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    ActiveDrawRecord selectByPrimaryKey(Integer id);
}