package com.example.nzgeneration.domain.user;

import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserEditingPageDetailInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserMyPageDetailInfo;
import com.example.nzgeneration.domain.user.dto.UserResponseDto.UserSigningSimpleInfo;
import com.example.nzgeneration.global.common.response.code.status.ErrorStatus;
import com.example.nzgeneration.global.common.response.exception.GeneralException;
import com.example.nzgeneration.global.s3.S3Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void addStamp(User user) {
        //TODO - 현재 멤버 불러오기
//        User user = userRepository.findById(1L)
//            .orElseThrow(() -> new GeneralException(ErrorStatus._EMPTY_USER));

        //TODO - 포인트 상수, 기준 정하기
        user.stamp(20);
    }

    @Transactional
    public boolean checkNickNameDuplicate(String name){
        if(userRepository.findByNickname(name).isPresent()){
            return false;
        }
        return true;
    }

    @Transactional
    public void updateNickName(User user, String name){
        if(!checkNickNameDuplicate(name)){
            throw new GeneralException(ErrorStatus._DUPLICATE_NICKNAME);
        }
        user.updateNickName(name);
    }

    @Transactional
    public void updateWalletAddress(User user, String address){
        user.updateWalletAddress(address);
    }

    @Transactional
    public void updateUserProfileImage(User user, MultipartFile image){
        String profileImgUrl = s3Service.uploadFile(image).getImgUrl();
        user.updateProfileImg(profileImgUrl);
    }

    public UserSigningSimpleInfo getDaysSigning(User user){
        LocalDateTime createdAt= user.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(createdAt, now)+1;
        return UserSigningSimpleInfo.toDTO(user, daysBetween);
    }

    public UserMyPageDetailInfo getMyPageInfo(User user){
        return UserMyPageDetailInfo.toDTO(user);
    }

    public UserEditingPageDetailInfo getEditPageInfo(User user){
        return UserEditingPageDetailInfo.toDTO(user);
    }



}
