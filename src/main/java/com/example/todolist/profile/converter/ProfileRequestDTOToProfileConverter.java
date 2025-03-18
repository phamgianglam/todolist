package com.example.todolist.profile.converter;

import com.example.todolist.profile.Profile;
import com.example.todolist.profile.dto.ProfileRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileRequestDTOToProfileConverter implements Converter<ProfileRequestDTO, Profile> {
    @Override
    public Profile convert(ProfileRequestDTO source) {
        Profile profile = new Profile();
        profile.setUsername(source.username());
        profile.setPassword(source.password());
        profile.setEmail(source.email());

        return  profile;
    }

}
