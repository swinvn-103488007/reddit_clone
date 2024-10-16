package LDDT.projects.reddit_clone.controllers;

import LDDT.projects.reddit_clone.dtos.VoteDto;
import LDDT.projects.reddit_clone.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
        return ResponseEntity.status(HttpStatus.OK).body("Your vote is recorded");
    }
}
