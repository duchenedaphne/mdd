package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    
    @NotBlank
    @Size(max = 20)
    private String title;

    @NotBlank
    private String content;

    private Long topic_id;

    private Long user_id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
