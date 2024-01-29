package com.pickpick.server.service;

import com.pickpick.server.domain.Album;
import com.pickpick.server.domain.Feed;
import com.pickpick.server.domain.Photo;
import com.pickpick.server.dto.FeedRequest;
import com.pickpick.server.repository.AlbumRepository;
import com.pickpick.server.repository.FeedRepository;
import com.pickpick.server.repository.PhotoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PhotoRepository photoRepository;
    private final FeedRepository feedRepository;
    private final AlbumRepository albumRepository;

    public Feed create(FeedRequest.CreateDTO request){
        Album album = albumRepository.findById(request.getAlbumId()).orElseThrow();

        List<Photo> photoList = new ArrayList<>();

        Feed feed = Feed.builder()
            .content(request.getContent())
            .createdAt(LocalDate.now())
            .build();

        for(Long photoId : request.getPhotoIdList()){
            Photo photo = photoRepository.findById(photoId).orElseThrow();
            photo.setFeed(feed);
            photoList.add(photo);
        }

        feed.setPhoto(photoList);
        feed.setAlbum(album);
        return feedRepository.save(feed);
    }
}
