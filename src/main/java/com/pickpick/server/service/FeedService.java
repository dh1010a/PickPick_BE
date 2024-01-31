package com.pickpick.server.service;

import com.pickpick.server.apiPayload.ApiResponse;
import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.AlbumHandler;
import com.pickpick.server.converter.FeedConverter;
import com.pickpick.server.domain.Album;
import com.pickpick.server.domain.Feed;
import com.pickpick.server.domain.Photo;
import com.pickpick.server.domain.Users;
import com.pickpick.server.dto.FeedRequest;
import com.pickpick.server.repository.AlbumRepository;
import com.pickpick.server.repository.FeedRepository;
import com.pickpick.server.repository.PhotoRepository;
import com.pickpick.server.repository.UsersRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

    private final PhotoRepository photoRepository;
    private final FeedRepository feedRepository;
    private final AlbumRepository albumRepository;
    private final UsersRepository usersRepository;

    public Feed create(FeedRequest.CreateDTO request){
        Album album = albumRepository.findById(request.getAlbumId()).orElseThrow();
        Users user = usersRepository.findById(request.getUserId()).orElseThrow();

        List<Photo> photoList = new ArrayList<>();

        Feed feed = Feed.builder()
            .user(user)
            .album(album)
            .content(request.getContent())
            .createdAt(LocalDate.now())
            .build();

        for(Long photoId : request.getPhotoIdList()){
            Photo photo = photoRepository.findById(photoId).orElseThrow();
            photo.setFeed(feed);
            photoList.add(photo);
        }

        feed.setPhoto(photoList);
        return feedRepository.save(feed);
    }

    public List<Long> getFeed(Long albumId){
        Optional<Album> album = albumRepository.findById(albumId);
        if(album.isEmpty()){
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
        List<Long> feedIdList = new ArrayList<>();

        List<Feed> feedList = album.get().getFeed();
        for(Feed feed : feedList){
            feedIdList.add(feed.getId());
        }
        return feedIdList;
    }

    public Feed updateFeed(FeedRequest.UpdateFeedDTO request){
        Feed feed = feedRepository.findById(request.getFeedId()).orElseThrow();
        feed.setContent(request.getContent());
        return feedRepository.save(feed);
    }

    public void deleteFeed(FeedRequest.DeleteFeedDTO request){
        Feed feed = feedRepository.findById(request.getFeedId()).orElseThrow();
        feedRepository.delete(feed);
    }
}
