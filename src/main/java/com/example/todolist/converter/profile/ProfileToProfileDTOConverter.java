package com.example.todolist.converter.profile;

import com.example.todolist.model.Profile;
import com.example.todolist.dto.profile.ProfileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToProfileDTOConverter implements Converter<Profile, ProfileDTO> {

    @Override
    public ProfileDTO convert(Profile source) {
        ProfileDTO profileDTO = new ProfileDTO(source.getId(), source.getUsername(),
                source.getEmail(), source.getTasks().size());
        return profileDTO;
    }

}
