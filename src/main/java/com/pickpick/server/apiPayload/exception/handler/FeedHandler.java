package com.pickpick.server.apiPayload.exception.handler;

import com.pickpick.server.apiPayload.code.BaseErrorCode;
import com.pickpick.server.apiPayload.exception.GeneralException;

public class FeedHandler extends GeneralException {
    public FeedHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
