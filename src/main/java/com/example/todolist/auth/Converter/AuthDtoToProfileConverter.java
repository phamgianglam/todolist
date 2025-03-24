package com.example.todolist.auth.Converter;


import com.example.todolist.auth.dto.AuthDTO;
import com.example.todolist.profile.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthDtoToProfileConverter implements Converter<AuthDTO, Profile> {
    @Override
    public Profile convert(AuthDTO source) {
        Profile profile = new Profile();
        profile.setUsername(source.username());
        profile.setPassword(source.password());

        return profile;
    }
}
