package com.knifez.fridaybootcore.exception;

import com.knifez.fridaybootcore.enums.ResultStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang
 */
@Getter
@Setter
public class FridayResultException extends RuntimeException {

    /**
     * η»ζηΆζ
     */
    private final ResultStatus resultStatus;


    public FridayResultException(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }
}
