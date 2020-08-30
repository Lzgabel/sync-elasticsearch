package cn.studacm.sync.model.response;

import cn.studacm.sync.util.JsonUtil;
import lombok.Data;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2020-08-19
 * @since 1.0.0
 */
@Data
public class Response<T> {
    private int code = 0;
    private String message = "SUCCESS";
    private T data;

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Response(T data) {
        this.data = data;
    }

    public static <T> Response<T> success(T result) {
        return new Response<>(result);
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
