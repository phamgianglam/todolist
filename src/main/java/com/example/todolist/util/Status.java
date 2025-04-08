package com.example.todolist.util;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Status {
  PENDING("Pending"),
  DONE("Done");

  private String value;

  Status(String value) {
    this.value = value;
  }
}
