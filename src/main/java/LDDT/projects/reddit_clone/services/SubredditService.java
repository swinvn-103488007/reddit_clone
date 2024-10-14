package LDDT.projects.reddit_clone.services;

import LDDT.projects.reddit_clone.dtos.SubredditDto;
import LDDT.projects.reddit_clone.model.Subreddit;
import LDDT.projects.reddit_clone.repositories.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {

        Subreddit save = Subreddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
        subredditRepository.save(save);
        subredditDto.setId(save.getId());
        return subredditDto;
    }
}
