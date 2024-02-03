package com.pickpick.server.apiPayload.exception.handler;

import com.pickpick.server.apiPayload.code.BaseErrorCode;
import com.pickpick.server.apiPayload.exception.GeneralException;

public class PhotoHandler extends GeneralException {
    public PhotoHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
