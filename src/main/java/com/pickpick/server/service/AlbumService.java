package com.pickpick.server.service;


import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.AlbumHandler;
import com.pickpick.server.apiPayload.exception.handler.UserHandler;
import com.pickpick.server.converter.AlbumConverter;
import com.pickpick.server.domain.Album;
import com.pickpick.server.domain.Feed;
import com.pickpick.server.domain.SharedAlbum;
import com.pickpick.server.domain.Users;
import com.pickpick.server.dto.AlbumRequest;
import com.pickpick.server.dto.AlbumResponse;
import com.pickpick.server.dto.FeedRequest;
import com.pickpick.server.repository.AlbumRepository;
import com.pickpick.server.repository.SharedAlbumRepository;
import com.pickpick.server.repository.UsersRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Error;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UsersRepository usersRepository;
    private final SharedAlbumRepository sharedAlbumRepository;

    public Album create(AlbumRequest.CreateDTO request) {

        //앨범 생성
        Album album = AlbumConverter.toAlbum(request);

        request.getUserId().forEach(userId -> {
            Users user = usersRepository.findById(userId).get();

            //sharedAlbum 생성
            SharedAlbum sharedAlbum = SharedAlbum.builder()
                .user(user)
                .album(album)
                .build();

            sharedAlbumRepository.save(sharedAlbum);
        });

        return albumRepository.save(album);

    }

    public List<List<Album>> findByEmail(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new UserHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        List<SharedAlbum> sharedAlbumList = user.get().getSharedAlbums();

        if (!sharedAlbumList.isEmpty()) {

            List<Album> shareAlbum = new ArrayList<>();
            List<Album> nonShareAlbum = new ArrayList<>();
            for (SharedAlbum sharedAlbum : sharedAlbumList) {
                if (Objects.equals(sharedAlbum.getAlbum().getShareStatus().toString(), "SHAREABLE")) {
                    shareAlbum.add(sharedAlbum.getAlbum());
                } else {
                  nonShareAlbum.add(sharedAlbum.getAlbum());
                }
            }
            List<List<Album>> albums = new ArrayList<>();
            albums.add(shareAlbum);
            albums.add(nonShareAlbum);
            return albums;

        } else {
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
    }

    public void deleteAlbum(AlbumRequest.DeleteAlbumDTO request){
        Album album = albumRepository.findById(request.getAlbumId()).orElseThrow();
        albumRepository.delete(album);
    }

    public Long updateAlbum(AlbumRequest.UpdateAlbumDTO request){
        Album album = albumRepository.findById(request.getAlbumId()).orElseThrow();
        album.setName(request.getTitle());
        album.setTitleImgUrl(request.getImgUrl());
        List<SharedAlbum> sharedAlbumList = sharedAlbumRepository.findByAlbum(album);
        sharedAlbumRepository.deleteAll(sharedAlbumList);
        for(Long userId : request.getUserId()){
            Users user = usersRepository.findById(userId).orElseThrow();
            SharedAlbum sharedAlbum = SharedAlbum.builder()
                    .album(album)
                        .user(user)
                            .build();
            sharedAlbumRepository.save(sharedAlbum);
        }
        return request.getAlbumId();
    }
}
