package com.researchgap.finder.service;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class DeterministicLiteratureProvider implements LiteratureProvider {
 public List<String> search(String query) {
  if (query.toLowerCase().contains("general")) {
   return List.of();
  }
  return List.of("Local literature index: no directly matching study found");
 }
}
