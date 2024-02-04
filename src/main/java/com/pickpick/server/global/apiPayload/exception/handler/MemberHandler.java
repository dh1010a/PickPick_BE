package com.pickpick.server.global.apiPayload.exception.handler;

import com.pickpick.server.global.apiPayload.code.BaseErrorCode;
import com.pickpick.server.global.apiPayload.exception.GeneralException;

public class MemberHandler extends GeneralException {

    public MemberHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
