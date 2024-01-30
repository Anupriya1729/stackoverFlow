package com.project.stackoverflow.controller;

import com.project.stackoverflow.dto.PostDTO;
import com.project.stackoverflow.dto.QuestionsDTO;
import com.project.stackoverflow.dto.SearchDTO;
import com.project.stackoverflow.entity.Post;
import com.project.stackoverflow.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPostsByText(@RequestBody SearchDTO searchQuery) {
        List<PostDTO> questions = searchService.searchPostsByString(searchQuery.getQuery());
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
