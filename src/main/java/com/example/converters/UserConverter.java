package com.example.converters;

import com.example.dto.UserDto;
import com.example.models.User;

public class UserConverter {
    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.id = user.id;
        userDto.avatar = user.profilePictureUrl;
        userDto.name = user.getName();
        return userDto;
    }

    public static User convertToEntity(UserDto userDto) {
        User user = new User();
        user.id = userDto.id;
        user.profilePictureUrl = userDto.avatar;
        user.setName(userDto.name);
        return user;
    }
}
