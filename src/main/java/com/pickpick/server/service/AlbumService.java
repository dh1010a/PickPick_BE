package com.pickpick.server.service;


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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UsersRepository usersRepository;
    private final SharedAlbumRepository sharedAlbumRepository;
    public Album create(AlbumRequest.CreateDTO request){

        //앨범 생성
        Album album = AlbumConverter.toAlbum(request);

        request.getUserId().forEach(userId -> {
            Users user = usersRepository.findById(userId).get();

            //sharedAlbum 생성
            SharedAlbum sharedAlbum = SharedAlbum.builder().build();

            sharedAlbum.setUser(user);
            sharedAlbum.setAlbum(album);
            sharedAlbumRepository.save(sharedAlbum);
        });

        return albumRepository.save(album);

    }

}
