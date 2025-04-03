package com.example.todolist.service;

import java.util.List;
import com.example.todolist.converter.tag.TagRequestToTagConverter;
import com.example.todolist.converter.tag.TagToTagResponseConverter;
import com.example.todolist.dto.tag.TagRequest;
import com.example.todolist.dto.tag.TagResponse;
import com.example.todolist.model.Tag;
import com.example.todolist.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private TagRepository tagRepository;
    private TagRequestToTagConverter tagRequestToTagConverter;
    private TagToTagResponseConverter tagToTagResponseConverter;

    public TagService(TagRepository tagRepository, TagRequestToTagConverter tagRequestToTagConverter, TagToTagResponseConverter tagToTagResponseConverter) {
        this.tagRepository = tagRepository;
        this.tagRequestToTagConverter = tagRequestToTagConverter;
        this.tagToTagResponseConverter = tagToTagResponseConverter;
    }

    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = tagRequestToTagConverter.convert(tagRequest);
        tag = tagRepository.save(tag);
        return tagToTagResponseConverter.convert(tag);
    }

    public List<TagResponse> findAll() {
        List<Tag> tagList = tagRepository.findAll();
        return tagList.stream().map(tagToTagResponseConverter::convert).toList();
    }

}
