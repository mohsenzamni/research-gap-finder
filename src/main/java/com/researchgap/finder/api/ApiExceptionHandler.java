package com.researchgap.finder.api;
import com.researchgap.finder.service.ProjectService.NotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(NotFoundException.class)
 ResponseEntity<Map<String, String>> notFound(NotFoundException exception) {
  return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(Map.of("error", exception.getMessage()));
 }
 @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
 ResponseEntity<Map<String, String>> bad(RuntimeException exception) {
  return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
 }
}
