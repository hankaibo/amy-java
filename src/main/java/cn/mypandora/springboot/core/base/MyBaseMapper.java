package cn.mypandora.springboot.core.base;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * MyBaseMapper
 * <p>
 * 该接口不能被扫描到，否则会出错🤔
 *
 * @author hankaibo
 * @date 2019/6/19
 */
public interface MyBaseMapper<T> extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
