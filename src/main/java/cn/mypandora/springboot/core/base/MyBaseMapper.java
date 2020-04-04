package cn.mypandora.springboot.core.base;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * MyBaseMapper
 * <p>
 * 该接口不能被扫描到，否则会出错🤔
 *
 * @author hankaibo
 * @date 2019/6/19
 * @see <a href="https://mapperhelper.github.io/all/">Mapper接口</a>
 * @see <a href="https://www.cnblogs.com/mingyue1818/p/3714162.html">多参数传递</a>
 */
public interface MyBaseMapper<T> extends BaseMapper<T>, IdsMapper<T>, ExampleMapper<T>, MySqlMapper<T> {}
