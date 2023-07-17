package ir.tinyLink.repository;

import ir.tinyLink.model.entity.LinkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Transactional
public interface LinkRepository extends JpaRepository<LinkEntity, Integer> {
    Page<LinkEntity> findByUserId(long userId,final Pageable pageable);

    Optional<LinkEntity> findByIdAndUserId(long id, long userId);

    Optional<LinkEntity> findById(long id);

    long countLinkEntitiesByUserId(long userId);

    @Modifying
    @Query(value = "DELETE FROM LINKS WHERE updatedat < NOW() - INTERVAL '365 days' ", nativeQuery = true)
    void deleteLinkEntitiesByUpdatedAtIsExpired();



}
