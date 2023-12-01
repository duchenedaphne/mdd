package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    private List<Long> users;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
