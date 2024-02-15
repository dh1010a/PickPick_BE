package com.pickpick.server.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class FirebaseConfig {
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException{
//        FileInputStream serviceAccountFile = new FileInputStream("src/main/resources/picpick-1947c-firebase-adminsdk-oa23k-d93284724e.json");
//        FirebaseOptions options = FirebaseOptions.builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccountFile))
//            .build();
//        return FirebaseApp.initializeApp(options);
//    }
//
//    @Bean
//    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp){
//        return FirebaseMessaging.getInstance(firebaseApp);
//    }
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException{
        //ClassPathResource resource = new ClassPathResource("/src/main/resources/picpick-1947c-firebase-adminsdk-oa23k-d93284724e.json");
        //InputStream refreshToken = resource.getInputStream();
        String localPath = "src\\main\\resources\\picpick-1947c-firebase-adminsdk-oa23k-d93284724e.json";
        String serverPath = "/home/ubuntu/json/picpick-1947c-firebase-adminsdk-oa23k-d93284724e.json";
//        FileInputStream refreshToken = new FileInputStream(localPath);
        InputStream refreshToken = loadFirebaseJsonAsInputStream();
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        if(firebaseAppList != null && !firebaseAppList.isEmpty()){
            for(FirebaseApp app : firebaseAppList){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                    firebaseApp = app;
                }
            }
        } else {
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    private InputStream loadFirebaseJsonAsInputStream() throws IOException {
        // 'src/main/resources' 디렉토리를 기준으로 경로 지정
        String firebaseJsonPath = "picpick-1947c-firebase-adminsdk-oa23k-d93284724e.json";

        // 리소스를 InputStream으로로드
        Resource resource = new ClassPathResource(firebaseJsonPath);
        return resource.getInputStream();
    }
}
