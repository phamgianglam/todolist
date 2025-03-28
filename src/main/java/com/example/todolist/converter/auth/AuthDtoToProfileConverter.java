package com.example.todolist.converter.auth;


import com.example.todolist.dto.auth.AuthDTO;
import com.example.todolist.model.Profile;
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
