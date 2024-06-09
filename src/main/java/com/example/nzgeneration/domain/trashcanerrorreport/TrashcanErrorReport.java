package com.example.nzgeneration.domain.trashcanerrorreport;

import com.example.nzgeneration.domain.trashcan.Trashcan;
import com.example.nzgeneration.domain.user.User;
import com.example.nzgeneration.global.utils.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TrashcanErrorReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @Nullable
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trashcan trashcan;

}