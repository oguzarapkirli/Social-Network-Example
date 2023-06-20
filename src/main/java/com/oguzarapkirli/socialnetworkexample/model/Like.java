package com.oguzarapkirli.socialnetworkexample.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "likes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser creator;

}