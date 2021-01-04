package com.parkvita.admin.springboot.web.dto;

import com.parkvita.admin.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsDto {
    private long id;
    private String content;
    private String title;
    private String author;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;



    @Builder
    public PostsDto(Long id, String title, String content, String author, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
