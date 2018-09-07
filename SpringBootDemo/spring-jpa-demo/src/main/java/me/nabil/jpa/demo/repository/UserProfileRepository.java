package me.nabil.jpa.demo.repository;

import me.nabil.jpa.demo.bo.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户增删查改
 *
 * @author zhangbi617
 * @date 2018-09-07
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
