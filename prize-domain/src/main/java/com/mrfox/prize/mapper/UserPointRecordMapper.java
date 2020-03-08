package com.mrfox.prize.mapper;

import com.mrfox.prize.model.UserPointRecord;
import org.apache.ibatis.annotations.Param;

public interface UserPointRecordMapper {

    Integer insertRecord(@Param("userPointRecord") UserPointRecord userPointRecord);

}