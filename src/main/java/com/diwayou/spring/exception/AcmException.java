package com.diwayou.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public abstract class AcmException extends RuntimeException {

    /**
     * Error errorData.
     */
    private Object errorData;

    public AcmException(String message) {
        super(message);
    }

    public AcmException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public AcmException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
