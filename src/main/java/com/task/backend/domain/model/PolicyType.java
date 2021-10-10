package com.task.backend.domain.model;

import java.util.HashMap;
import java.util.Map;

public enum PolicyType {
  KOMUNIKACYJNA("Komunikacyjna"),
  OSOBOWA("Osobowa"),
  MAJATKOWA("Majątkowa");

  private static final Map<String, PolicyType> map = new HashMap<>(values().length, 1);

  static {
    for (PolicyType c : values()) map.put(c.type, c);
  }

  private final String type;

  private PolicyType(String type) {
    this.type = type;
  }

  public static PolicyType of(String name) {
    PolicyType result = map.get(name);
    if (result == null) {
      throw new IllegalArgumentException("Invalid policy type name: " + name);
    }
    return result;
  }

  public String getType() {
    return type;
  }
}
