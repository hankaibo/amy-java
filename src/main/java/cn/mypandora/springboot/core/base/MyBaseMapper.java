package cn.mypandora.springboot.core.base;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;

/**
 * MyBaseMapper
 * <p>
 * 该接口不能被扫描到，否则会出错🤔
 *
 * @author hankaibo
 * @date 2019/6/19
 * @see <a href="https://mapperhelper.github.io/all/"> more</a>
 */
public interface MyBaseMapper<T> extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T> {
}
