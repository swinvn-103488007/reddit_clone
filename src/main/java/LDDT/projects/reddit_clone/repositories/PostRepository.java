package LDDT.projects.reddit_clone.repositories;

import LDDT.projects.reddit_clone.model.Post;
import LDDT.projects.reddit_clone.model.Subreddit;
import LDDT.projects.reddit_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
