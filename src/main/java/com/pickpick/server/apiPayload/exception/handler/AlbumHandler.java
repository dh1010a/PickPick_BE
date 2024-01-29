package com.pickpick.server.apiPayload.exception.handler;

import com.pickpick.server.apiPayload.code.BaseErrorCode;
import com.pickpick.server.apiPayload.exception.GeneralException;

public class AlbumHandler extends GeneralException {

    public AlbumHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
