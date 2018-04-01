package com.jldata.smartframe.core.mapper;

import java.util.List;

public class BeanMapperKit {

    private static BeanMapper beanMapper;
    static {
        beanMapper = new BeanMapper();
    }
    private BeanMapperKit(){

    }
    /**
     * 把src中的值复制到dest中.
     */
    public static void copy(Object src, Object dest) {
        beanMapper.copy(src, dest);
    }

    /**
     * 指定复制的src和target的class.
     */
    public static <S, D> void copy(S src, D target, Class<S> srcClass,
                            Class<D> targetClass) {
        beanMapper.copy(src, target,srcClass, targetClass );
    }

    /**
     * 复制list.
     */
    public static <S, D> List<D> copyList(List<S> src, Class<D> clz) {
        return beanMapper.copyList(src, clz);
    }

}
