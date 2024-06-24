package com.example.services.Abstractions;

import com.example.dto.PersonalInfoDto;
import com.example.dto.UserReviewDto;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface IUserService {
    PersonalInfoDto getPersonalInfo(long userId);

    PersonalInfoDto changeProfilePicture(long userId, MultipartFile image);

    int getReviewsPagesCount(long userId);

    List<UserReviewDto> getReviews(long userId, int page, String search);


}
