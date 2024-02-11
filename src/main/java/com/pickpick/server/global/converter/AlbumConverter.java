package com.pickpick.server.global.converter;

import com.pickpick.server.album.domain.Album;
import com.pickpick.server.album.domain.SharedAlbum;
import com.pickpick.server.album.dto.AlbumRequest;
import com.pickpick.server.album.dto.AlbumResponse;
import com.pickpick.server.album.dto.AlbumResponse.AlbumDto;
import com.pickpick.server.feed.domain.Feed;
import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.AlbumHandler;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static AlbumResponse.GetAlbumDTO toGetAlbumDTO(List<List<Album>> album){
        if (album == null) {
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
        return AlbumResponse.GetAlbumDTO.builder()
            .shareAlbum(toAlbumDtoList(album.get(0)))
            .nonShareAlbum(toAlbumDtoList(album.get(1)))
            .build();
    }

    public static List<AlbumResponse.AlbumDto> toAlbumDtoList(List<Album> albums) {
        List<AlbumResponse.AlbumDto> list = new ArrayList<>();
        for (Album a : albums) {
            list.add(AlbumDto.builder()
                            .id(a.getId())
                            .name(a.getName())
                            .titleImgUrl(a.getTitleImgUrl())
                            .sharedAlbumIds(a.getSharedAlbums().stream().map(SharedAlbum::getId).toList())
                            .feedIds(a.getFeed().stream().map(Feed::getId).toList())
                            .build());
        }
        return list;
    }

    public static AlbumResponse.DeleteAlbumDTO toDeleteAlbumDTO(){
        return AlbumResponse.DeleteAlbumDTO.builder()
            .result("success")
            .build();
    }

    public static AlbumResponse.UpdateAlbumDTO toUpdateAlbumDTO(Long albumId){
        return AlbumResponse.UpdateAlbumDTO.builder()
            .albumId(albumId)
            .build();
    }
}
