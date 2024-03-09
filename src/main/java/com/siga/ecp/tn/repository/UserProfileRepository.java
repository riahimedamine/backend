package com.siga.ecp.tn.repository;

import com.siga.ecp.tn.domain.UserProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the UserProfile entity.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findOneByUserLogin(String login);

    Optional<UserProfile> findOneByUserId(Long id);

    @EntityGraph(attributePaths = "user")
    Optional<UserProfile> findOneWithUserByUserLogin(String login);

    @EntityGraph(attributePaths = "user")
    Optional<UserProfile> findOneWithUserByUserId(Long id);

    List<UserProfile> findAllByUserIsNull();

    List<UserProfile> findAllByUserIsNotNull();

    List<UserProfile> findAllByUserIsNotNullAndUserActivatedIsTrue();
}
