package com.example.todolist.service;

import com.example.todolist.converter.tag.TagRequestToTagConverter;
import com.example.todolist.converter.tag.TagToTagResponseConverter;
import com.example.todolist.dto.tag.TagRequest;
import com.example.todolist.dto.tag.TagResponse;
import com.example.todolist.model.Tag;
import com.example.todolist.repository.TagRepository;
import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagService {
  private final TagRepository tagRepository;
  private final TagRequestToTagConverter tagRequestToTagConverter;
  private final TagToTagResponseConverter tagToTagResponseConverter;

  public TagService(
      TagRepository tagRepository,
      TagRequestToTagConverter tagRequestToTagConverter,
      TagToTagResponseConverter tagToTagResponseConverter) {
    this.tagRepository = tagRepository;
    this.tagRequestToTagConverter = tagRequestToTagConverter;
    this.tagToTagResponseConverter = tagToTagResponseConverter;
  }

  public TagResponse createTag(@Nonnull TagRequest tagRequest) {
    Tag tag = tagRequestToTagConverter.convert(tagRequest);
    if (tag != null) {
      tag = tagRepository.save(tag);
      return tagToTagResponseConverter.convert(tag);
    } else {
      throw new IllegalArgumentException("Tag cannot be null");
    }
  }

  public List<TagResponse> findAll() {
    List<Tag> tagList = tagRepository.findAll();
    return tagList.stream().map(tagToTagResponseConverter::convert).toList();
  }
}
