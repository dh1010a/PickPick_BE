package com.pickpick.server.global.apiPayload.exception.handler;

import com.pickpick.server.global.apiPayload.code.BaseErrorCode;
import com.pickpick.server.global.apiPayload.exception.GeneralException;

public class AlbumHandler extends GeneralException {

    public AlbumHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
