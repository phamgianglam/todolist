package com.example.todolist.profile.converter;

import com.example.todolist.profile.Profile;
import com.example.todolist.profile.dto.ProfileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter implements Converter<Profile, ProfileDTO> {

    @Override
    public ProfileDTO convert(Profile source) {
        ProfileDTO profileDTO = new ProfileDTO(source.getId(),
                                               source.getUsername(),
                                               source.getEmail(),
                                               source.getTasks().size());
        return profileDTO;
    }

}
