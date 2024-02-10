package com.pickpick.server.notification.controller;

import com.pickpick.server.notification.dto.RequestDTO;
import com.pickpick.server.notification.service.FirebaseCloudMessageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/fcm")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException {
        System.out.println(requestDTO.getTargetToken() + " " + requestDTO.getTitle() + " " + requestDTO.getBody());

        firebaseCloudMessageService.sendMessageTo(requestDTO.getTargetToken(), requestDTO.getTitle(), requestDTO.getBody());
        return ResponseEntity.ok().build();
    }
}
