package com.example.todolist.converter.tag;

import com.example.todolist.dto.tag.TagRequest;
import com.example.todolist.model.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagRequestToTagConverter implements Converter<TagRequest, Tag> {

  @Override
  public Tag convert(TagRequest source) {
    Tag tag = new Tag();
    tag.setName(source.name());
    return tag;
  }
}
