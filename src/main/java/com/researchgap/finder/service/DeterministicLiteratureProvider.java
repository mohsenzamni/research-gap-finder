package com.researchgap.finder.service;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class DeterministicLiteratureProvider implements LiteratureProvider {
 public List<String> search(String query){ return query.toLowerCase().contains("general") ? List.of() : List.of("Local literature index: no directly matching study found"); }
}
