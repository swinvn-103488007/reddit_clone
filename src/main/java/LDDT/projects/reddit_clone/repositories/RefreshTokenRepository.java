package LDDT.projects.reddit_clone.repositories;

import LDDT.projects.reddit_clone.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}
