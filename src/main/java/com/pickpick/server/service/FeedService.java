package com.pickpick.server.service;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.AlbumHandler;
import com.pickpick.server.apiPayload.exception.handler.FeedHandler;
import com.pickpick.server.apiPayload.exception.handler.PhotoHandler;
import com.pickpick.server.apiPayload.exception.handler.UserHandler;
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
@Transactional
public class FeedService {

    private final PhotoRepository photoRepository;
    private final FeedRepository feedRepository;
    private final AlbumRepository albumRepository;
    private final UsersRepository usersRepository;

    public Feed create(FeedRequest.CreateDTO request){
        Optional<Album> album = albumRepository.findById(request.getAlbumId());
        if(album.isEmpty()){
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
        Optional<Users> user = usersRepository.findById(request.getUserId());
        if(user.isEmpty()){
            throw new UserHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        List<Photo> photoList = new ArrayList<>();

        Feed feed = Feed.builder()
            .user(user.get())
            .album(album.get())
            .content(request.getContent())
            .createdAt(LocalDate.now())
            .build();

        for(Long photoId : request.getPhotoIdList()){
            Optional<Photo> photo = photoRepository.findById(photoId);
            if(photo.isEmpty()){
                throw new PhotoHandler(ErrorStatus.PHOTO_NOT_FOUND);
            }
            photo.get().setFeed(feed);
            photoList.add(photo.get());
        }

        feed.setPhoto(photoList);
        return feedRepository.save(feed);
    }

    //줘야 하는 값 고민
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
        Optional<Feed> feed = feedRepository.findById(request.getFeedId());
        if(feed.isEmpty()){
            throw new FeedHandler(ErrorStatus.FEED_NOT_FOUND);
        }

        feed.get().setContent(request.getContent());
        return feedRepository.save(feed.get());
    }

    public void deleteFeed(FeedRequest.DeleteFeedDTO request){
        Optional<Feed> feed = feedRepository.findById(request.getFeedId());
        if(feed.isEmpty()){
            throw new FeedHandler(ErrorStatus.FEED_NOT_FOUND);
        }
        feedRepository.delete(feed.get());
    }
}
