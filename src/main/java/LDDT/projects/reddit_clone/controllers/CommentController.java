package LDDT.projects.reddit_clone.controllers;

import LDDT.projects.reddit_clone.dtos.CommentsDto;
import LDDT.projects.reddit_clone.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentsDto commentsDto) {
        commentService.save(commentsDto);
        return ResponseEntity.status(CREATED).body("Successfully commented");
    }

    @GetMapping
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping(params = "userName")
    public ResponseEntity<List<CommentsDto>> getAllCommentsFromUser(@RequestParam String userName){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }

}
