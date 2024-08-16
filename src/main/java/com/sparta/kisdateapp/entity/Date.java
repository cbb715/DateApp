package com.sparta.kisdateapp.entity;


import com.sparta.kisdateapp.dto.DateRequesDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Date {
    private Long id;
    private String username;
    private String contents;

    public Date(DateRequesDto requesDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

    public void update(DateRequesDto requesDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
}
