package LDDT.projects.reddit_clone.repositories;

import LDDT.projects.reddit_clone.model.Post;
import LDDT.projects.reddit_clone.model.User;
import LDDT.projects.reddit_clone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
