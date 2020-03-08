package com.mrfox.prize.bean2bean;

import org.mapstruct.factory.Mappers;

/*****
 * 映射转换实例
 * @author     : MrFox
 * @date       : 2019-12-18 16:04
 * @description:
 * @version    :
 ****/
public class MappingInstance {
    private MappingInstance() {
    }

    /**
     * 基础配置
     * */
    public static final BaseMapping BASE_MAPPING = Mappers.getMapper(BaseMapping.class);

}
