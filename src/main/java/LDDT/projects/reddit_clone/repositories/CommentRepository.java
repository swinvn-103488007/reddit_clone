package LDDT.projects.reddit_clone.repositories;

import LDDT.projects.reddit_clone.model.Comment;
import LDDT.projects.reddit_clone.model.Post;
import LDDT.projects.reddit_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
