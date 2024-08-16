package com.sparta.kisdateapp.dto;


import lombok.Getter;

@Getter
public class DateResponseDto {
    private Long id;
    private String username;
    private String contents;

    public DateResponseDto(Date date) {
        this.id = date.getId();
        this.username = date.getUsername();
        this.contents = date.getContents();
    }

    public DateResponseDto(Long id, String username, String contents) {
        this.id = id;
        this.username = username;
        this.contents = contents;
    }
}
