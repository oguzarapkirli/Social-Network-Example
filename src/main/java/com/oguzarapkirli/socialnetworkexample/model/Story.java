package com.oguzarapkirli.socialnetworkexample.model;

import com.oguzarapkirli.socialnetworkexample.util.enums.StoryType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "story")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Story extends BaseEntity {
    @Enumerated(EnumType.STRING)
    StoryType type;

    private String content;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser creator;

}