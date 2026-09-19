package com.gulimall.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Map;

public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    @TableField(exist = false)
    private String key;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}