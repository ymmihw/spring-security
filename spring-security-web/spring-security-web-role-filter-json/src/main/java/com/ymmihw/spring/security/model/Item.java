package com.ymmihw.spring.security.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ymmihw.spring.security.controller.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class Item {

  @JsonView(View.User.class)
  private int id;
  @JsonView(View.User.class)
  private String name;
  @JsonView(View.Admin.class)
  private String ownerName;

}
