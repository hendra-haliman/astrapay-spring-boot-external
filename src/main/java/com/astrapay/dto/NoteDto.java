package com.astrapay.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class NoteDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private Date createdAt;
}
