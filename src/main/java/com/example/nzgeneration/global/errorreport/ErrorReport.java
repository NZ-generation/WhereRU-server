package com.example.nzgeneration.global.errorreport;

import com.example.nzgeneration.global.utils.BaseTimeEntity;
import com.example.nzgeneration.domain.trashcan.Trashcan;
import com.example.nzgeneration.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User errorReportUser;

    private String content;

    @ManyToOne
    private Trashcan trashcan;
}