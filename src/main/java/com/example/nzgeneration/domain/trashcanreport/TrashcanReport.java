package com.example.nzgeneration.domain.trashcanreport;

import com.example.nzgeneration.domain.trashcan.TrashCategory;
import com.example.nzgeneration.global.utils.BaseTimeEntity;
import com.example.nzgeneration.domain.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.Cascade;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class TrashcanReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @Nullable
    private User trashcanReportUser;

    @Enumerated(EnumType.STRING)
    private TrashCategory trashCategory;

    private String trashcanReportImageUrl;


}