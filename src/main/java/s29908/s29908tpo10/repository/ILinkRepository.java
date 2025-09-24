package s29908.s29908tpo10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import s29908.s29908tpo10.model.Link;

import java.util.Optional;

@Repository
public interface ILinkRepository extends JpaRepository<Link, String> {

    Optional<Link> findByTargetURL(String url);

    Optional<Link> findByName(String name);


    @Query("SELECT l.id FROM Link l WHERE l.targetURL = :redirectUrl")
    Optional<String> findLinkIdByRedirectUrl(@Param("redirectUrl") String redirectUrl);

    @Modifying
    @Transactional
    @Query("UPDATE Link l SET l.count = l.count + 1 WHERE l.id = :id")
    void incrementVisits(@Param("id") String id);


}

