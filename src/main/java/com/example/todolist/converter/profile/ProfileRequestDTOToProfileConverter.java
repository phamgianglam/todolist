package com.example.todolist.converter.profile;

import com.example.todolist.model.Profile;
import com.example.todolist.dto.profile.ProfileRequestDTO;
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

        return profile;
    }

}
