package cn.mypandora.springboot.core.base;

import cn.mypandora.springboot.core.enums.ResultEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private int code;
    private boolean success;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 判断是否是成功结果。
     *
     * @return 判断结果
     */
    public boolean isSuccess() {
        return ResultEnum.SUCCESS.getCode() == this.code;
    }
}
