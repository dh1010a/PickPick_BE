package com.pickpick.server.global.apiPayload.exception.handler;

import com.pickpick.server.global.apiPayload.code.BaseErrorCode;
import com.pickpick.server.global.apiPayload.exception.GeneralException;

public class FeedHandler extends GeneralException {
    public FeedHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
