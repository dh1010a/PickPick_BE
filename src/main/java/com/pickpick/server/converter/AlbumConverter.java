package com.pickpick.server.converter;

import com.pickpick.server.domain.Album;
import com.pickpick.server.domain.SharedAlbum;
import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.AlbumRequest;
import com.pickpick.server.dto.AlbumResponse;
import com.pickpick.server.dto.FeedResponse;
import java.time.LocalDate;
import java.util.List;

public class AlbumConverter {

    public static Album toAlbum(AlbumRequest.CreateDTO request) {
        return Album.builder()
            .name(request.getName())
            .titleImgUrl(request.getTitleImgUrl())
            .shareStatus(request.getShareStatus())
            .createdAt(LocalDate.now())
            .build();
    }
    public static AlbumResponse.CreateDTO toCreateDTO(Album album){
        return AlbumResponse.CreateDTO.builder()
            .albumId(album.getId())
            .createdAt(album.getCreatedAt())
            .build();
    }

    public static AlbumResponse.GetAlbumDTO toGetAlbumDTO(List<List<Long>> albumId){
        return AlbumResponse.GetAlbumDTO.builder()
            .shareAlbumId(albumId.get(0))
            .nonShareAlbumId(albumId.get(1))
            .build();
    }

    public static AlbumResponse.DeleteAlbumDTO toDeleteAlbumDTO(){
        return AlbumResponse.DeleteAlbumDTO.builder()
            .result("success")
            .build();
    }
}
