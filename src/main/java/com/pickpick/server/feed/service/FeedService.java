package com.pickpick.server.feed.service;

import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.AlbumHandler;
import com.pickpick.server.global.apiPayload.exception.handler.FeedHandler;
import com.pickpick.server.global.apiPayload.exception.handler.PhotoHandler;
import com.pickpick.server.global.apiPayload.exception.handler.MemberHandler;
import com.pickpick.server.album.domain.Album;
import com.pickpick.server.feed.domain.Feed;
import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.member.domain.Member;
import com.pickpick.server.feed.dto.FeedRequest;
import com.pickpick.server.album.repository.AlbumRepository;
import com.pickpick.server.feed.repository.FeedRepository;
import com.pickpick.server.photo.repository.PhotoRepository;
import com.pickpick.server.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public Feed create(FeedRequest.CreateDTO request){
        Optional<Album> album = albumRepository.findById(request.getAlbumId());
        if(album.isEmpty()){
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
        Optional<Member> user = memberRepository.findById(request.getUserId());
        if(user.isEmpty()){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
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
