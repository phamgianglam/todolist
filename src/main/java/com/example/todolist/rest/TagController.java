package com.example.todolist.rest;

import com.example.todolist.dto.tag.TagRequest;
import com.example.todolist.dto.tag.TagResponse;
import com.example.todolist.service.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
@SecurityRequirement(name = "BearerAuth")
public class TagController {
  private TagService tagService;

  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @PostMapping("/")
  public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest tagRequest) {
    TagResponse tag = this.tagService.createTag(tagRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(tag);
  }

  @GetMapping("/")
  public ResponseEntity<List<TagResponse>> findAll() {
    List<TagResponse> tagResponseList = this.tagService.findAll();

    return ResponseEntity.ok(tagResponseList);
  }
}
