package com.pickpick.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        FileInputStream refreshToken = new FileInputStream("/src/main/resources/picpick-1947c-firebase-adminsdk-oa23k-d93284724e.json");

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
}
