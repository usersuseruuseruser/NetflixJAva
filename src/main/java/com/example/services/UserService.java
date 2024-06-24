package com.example.services;

import com.example.dto.PersonalInfoDto;
import com.example.dto.UserReviewDto;
import com.example.models.User;
import com.example.repository.UserRepository;
import com.example.services.Abstractions.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    private MinioService minioService;
    private static final int REVIEWS_PER_PAGE = 5;

    public UserService(UserRepository userRepository,
                       MinioService minioService) {
        this.userRepository = userRepository;
        this.minioService = minioService;
    }

    @Override
    public PersonalInfoDto getPersonalInfo(long userId) {
        User user = userRepository.findById(userId).get();

        PersonalInfoDto personalInfoDto = new PersonalInfoDto();
        personalInfoDto.nickname = user.getName();
        personalInfoDto.profilePictureUrl = minioService.getObjectUrl(user.profilePictureUrl);
        personalInfoDto.email = user.getEmail();

        return personalInfoDto;
    }

    @Override
    public PersonalInfoDto changeProfilePicture(long userId, MultipartFile image) {
        User user = userRepository.findById(userId).get();

        minioService.createBucket("avatars");
        String newProfilePictureUrl = minioService.uploadFileAndGetObjectUrl(image);
        user.setProfilePictureUrl(newProfilePictureUrl);
        userRepository.save(user);

        PersonalInfoDto personalInfoDto = new PersonalInfoDto();
        personalInfoDto.nickname = user.getName();
        personalInfoDto.profilePictureUrl = user.getProfilePictureUrl();
        personalInfoDto.email = user.getEmail();

        return personalInfoDto;
    }

    @Override
    public int getReviewsPagesCount(long userId) {
        int reviewsCount = userRepository.findById(userId).get().reviews.size();

        return (int) Math.ceil((double) reviewsCount / REVIEWS_PER_PAGE);
    }

    @Override
    public List<UserReviewDto> getReviews(long userId, int page, String search) {
        User user = userRepository.findById(userId).get();

        List<UserReviewDto> userReviewDtos;

        if (search == null || search.isEmpty()) {
            userReviewDtos = user.reviews.stream()
                    .skip((long) (page - 1) * REVIEWS_PER_PAGE)
                    .limit(REVIEWS_PER_PAGE)
                    .map(review -> {
                        UserReviewDto userReviewDto = new UserReviewDto();
                        userReviewDto.id = review.userId;
                        userReviewDto.text = review.text;
                        userReviewDto.score = review.score;
                        userReviewDto.writtenAt = review.writtenAt.toLocalDateTime();
                        userReviewDto.isPositive = review.isPositive;
                        userReviewDto.contentName = review.movie.name;
                        return userReviewDto;
                    })
                    .toList();
        } else {
            userReviewDtos = user.reviews.stream()
                    .filter(review -> review.movie.name.contains(search))
                    .skip((long) (page - 1) * REVIEWS_PER_PAGE)
                    .limit(REVIEWS_PER_PAGE)
                    .map(review -> {
                        UserReviewDto userReviewDto = new UserReviewDto();
                        userReviewDto.id = review.userId;
                        userReviewDto.text = review.text;
                        userReviewDto.score = review.score;
                        userReviewDto.writtenAt = review.writtenAt.toLocalDateTime();
                        userReviewDto.isPositive = review.isPositive;
                        userReviewDto.contentName = review.movie.name;
                        return userReviewDto;
                    })
                    .toList();
        }

        return userReviewDtos;
    }
}
