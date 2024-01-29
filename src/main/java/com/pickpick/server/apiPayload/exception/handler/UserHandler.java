package com.pickpick.server.apiPayload.exception.handler;

import com.pickpick.server.apiPayload.code.BaseErrorCode;
import com.pickpick.server.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {

    public UserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
