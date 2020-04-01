package cn.mypandora.springboot.core.base;

import java.util.Collection;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageSerializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PageInfo
 * <p>
 * 对原<a href="com.github.pagehelper.PageInfo">PageInfo</a>文件进行简化。
 *
 * @author hankaibo
 * @date 2019/8/17
 */
@ApiModel(value = "数据对象")
@Data
@NoArgsConstructor
public class PageInfo<T> extends PageSerializable<T> {

    private static final long serialVersionUID = 8455756259444588945L;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private int pageNum;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量")
    private int pageSize;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private long total;

    public PageInfo(List<T> list) {
        super(list);
        if (list instanceof Page) {
            Page page = (Page)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.total = list.size();
        }
    }

    public static <T> PageInfo<T> of(List<T> list) {
        return new PageInfo<>(list);
    }

}
