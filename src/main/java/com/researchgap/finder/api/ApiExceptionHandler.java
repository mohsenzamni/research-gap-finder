package com.researchgap.finder.api;
import com.researchgap.finder.service.ProjectService.NotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(NotFoundException.class) ResponseEntity<Map<String,String>> notFound(NotFoundException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));}
 @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) ResponseEntity<Map<String,String>> bad(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
}
