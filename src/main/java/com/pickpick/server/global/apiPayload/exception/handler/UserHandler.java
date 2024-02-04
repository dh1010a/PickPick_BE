package com.pickpick.server.global.apiPayload.exception.handler;

import com.pickpick.server.global.apiPayload.code.BaseErrorCode;
import com.pickpick.server.global.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {

    public UserHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
