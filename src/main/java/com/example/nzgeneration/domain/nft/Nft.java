package com.example.nzgeneration.domain.nft;

import com.example.nzgeneration.domain.board.Board;
import com.example.nzgeneration.global.utils.BaseTimeEntity;
import com.example.nzgeneration.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Nft extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Getter
    private String imageUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Board board;

    public static Nft toEntity(User user, String imageUrl){
        return Nft.builder()
            .user(user)
            .imageUrl(imageUrl)
            .build();
    }
}