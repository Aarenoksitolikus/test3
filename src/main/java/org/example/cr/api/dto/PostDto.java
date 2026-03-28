package org.example.cr.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
