package com.pickpick.server.service;


import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.AlbumHandler;
import com.pickpick.server.apiPayload.exception.handler.UserHandler;
import com.pickpick.server.converter.AlbumConverter;
import com.pickpick.server.domain.Album;
import com.pickpick.server.domain.SharedAlbum;
import com.pickpick.server.domain.Users;
import com.pickpick.server.dto.AlbumRequest;
import com.pickpick.server.dto.AlbumResponse;
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
@Transactional(readOnly = true)
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

    public List<List<Long>> findById(Long userId) {
        Optional<Users> user = usersRepository.findById(userId);

        if(user.isEmpty()){
            throw new UserHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        List<SharedAlbum> sharedAlbumList = user.get().getSharedAlbums();

        if (!sharedAlbumList.isEmpty()) {

            List<Long> shareAlbumId = new ArrayList<>();
            List<Long> nonShareAlbumId = new ArrayList<>();
            for (SharedAlbum sharedAlbum : sharedAlbumList) {
                if (Objects.equals(sharedAlbum.getAlbum().getShareStatus().toString(), "SHAREABLE")) {
                    shareAlbumId.add(sharedAlbum.getAlbum().getId());
                } else {
                  nonShareAlbumId.add(sharedAlbum.getAlbum().getId());
                }
            }
            List<List<Long>> albumId = new ArrayList<>();
            albumId.add(shareAlbumId);
            albumId.add(nonShareAlbumId);
            return albumId;

        } else {
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
    }

}
