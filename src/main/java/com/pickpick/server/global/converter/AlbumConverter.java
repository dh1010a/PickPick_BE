package com.pickpick.server.global.converter;

import com.pickpick.server.album.domain.Album;
import com.pickpick.server.album.domain.SharedAlbum;
import com.pickpick.server.album.dto.AlbumRequest;
import com.pickpick.server.album.dto.AlbumResponse;
import com.pickpick.server.album.dto.AlbumResponse.AlbumDto;
import com.pickpick.server.album.dto.AlbumResponse.GetSharedAlbumDTO;
import com.pickpick.server.feed.domain.Feed;
import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.AlbumHandler;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public static AlbumResponse.GetSharedAlbumDTO toGetSharedAlbumDTO(List<Album> album, List<Long> memberIdList){
        if (album == null) {
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
        return GetSharedAlbumDTO.builder()
                .album(toAlbumDtoList(album, memberIdList))
                .build();
    }

    public static AlbumResponse.GetNonSharedAlbumDTO toGetNonSharedAlbumDTO(List<Album> album){
        if (album == null) {
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }

        return AlbumResponse.GetNonSharedAlbumDTO.builder()
                .album(toAlbumDtoList(album))
                .build();
    }

    public static List<AlbumResponse.AlbumDto> toAlbumDtoList(List<Album> albums, List<Long> memberIdList) {
        List<AlbumResponse.AlbumDto> list = new ArrayList<>();
        for (Album a : albums) {
            list.add(AlbumDto.builder()
                    .id(a.getId())
                    .name(a.getName())
                    .titleImgUrl(a.getTitleImgUrl())
                    .sharedAlbumIds(a.getSharedAlbums().stream().map(SharedAlbum::getId).toList())
                    .feedIds(a.getFeed().stream().map(Feed::getId).toList())
                    .memberIds(memberIdList)
                    .build());
        }
        return list;
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
