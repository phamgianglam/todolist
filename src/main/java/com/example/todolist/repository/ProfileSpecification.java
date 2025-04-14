package com.example.todolist.repository;

import com.example.todolist.model.Profile;
import org.springframework.data.jpa.domain.Specification;

public class ProfileSpecification {
  public static Specification<Profile> isAdminUser(Boolean isAdmin) {
    return ((root, query, criteriaBuilder) -> {
      if (isAdmin == null) {
        return null;
      } else if (isAdmin == Boolean.FALSE) {
        return criteriaBuilder.equal(root.get("role"), "USER");
      } else {
        return criteriaBuilder.equal(root.get("role"), "ADMIN");
      }
    });
  }
}
