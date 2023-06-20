package com.oguzarapkirli.socialnetworkexample.model;

import com.oguzarapkirli.socialnetworkexample.util.enums.PostType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "post")
@Builder
@NoArgsConstructor
public class Post extends BaseEntity {
    private String content;

    private String description;

    @Enumerated(EnumType.STRING)
    private PostType type;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser creator;

    public Post(String content, String description, PostType type, AppUser creator) {
        this.content = content;
        this.description = description;
        this.type = type;
        this.creator = creator;
        if (content != null && type == PostType.TEXT) {
            this.description = null;
        }
    }

    public void setDescription(String description) {
        if (content != null && type == PostType.TEXT) {
            this.description = null;
        } else {
            this.description = description;
        }
    }
}