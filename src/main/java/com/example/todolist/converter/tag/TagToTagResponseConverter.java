package com.example.todolist.converter.tag;

import com.example.todolist.dto.tag.TagResponse;
import com.example.todolist.model.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagToTagResponseConverter implements Converter<Tag, TagResponse> {
  @Override
  public TagResponse convert(Tag source) {
    return new TagResponse(source.getId(), source.getName());
  }
}
