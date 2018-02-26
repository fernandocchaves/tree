package com.github.fernandocchaves.tree.domain.shared;

import lombok.Data;
import lombok.experimental.Tolerate;

@Data
public abstract class ErrorCode {
    private String code;
    private String message;

    @Tolerate
    protected ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
