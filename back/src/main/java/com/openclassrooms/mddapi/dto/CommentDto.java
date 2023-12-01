package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    
    private Long id;
    
    @NotBlank
    private String content;

    private Long post_id;

    private Long user_id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
