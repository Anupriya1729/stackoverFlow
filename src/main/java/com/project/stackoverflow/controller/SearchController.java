package com.project.stackoverflow.controller;

import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.dto.SearchDTO;
import com.project.stackoverflow.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<Set<PostDTO>> searchPostsByText(@RequestBody SearchDTO searchQuery) {
        Set<PostDTO> questions = searchService.searchPostsByString(searchQuery.getQuery());
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/search/{tagName}")
    public ResponseEntity<List<QuestionsDTO>> searchQuestionsByTag(@PathVariable(name = "tagName") String tagName) {
        String tagNameToSearch = tagName.replace(" ", "");
        log.info(tagNameToSearch);
        List<QuestionsDTO> posts = searchService.getAllQuestionsByTagName(tagNameToSearch);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
