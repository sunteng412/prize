package com.mrfox.prize.mapper;

import com.mrfox.prize.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectById(@Param("id")Long id);

    Integer updateRealPointById(@Param("id")Long id,@Param("point") Integer point);

    Integer updatePointById(@Param("id")Long id,@Param("point") Integer point);
}