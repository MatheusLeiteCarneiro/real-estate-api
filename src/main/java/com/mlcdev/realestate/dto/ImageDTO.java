package com.mlcdev.realestate.dto;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
     
    private UUID id;

    private String fileIdentifier;

    private String url;

    private Boolean isPrimary;
}