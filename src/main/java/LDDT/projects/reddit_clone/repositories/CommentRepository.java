package LDDT.projects.reddit_clone.repositories;

import LDDT.projects.reddit_clone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
