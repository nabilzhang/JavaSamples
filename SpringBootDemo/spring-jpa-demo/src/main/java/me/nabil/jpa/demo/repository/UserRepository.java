package me.nabil.jpa.demo.repository;

import me.nabil.jpa.demo.bo.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户增删查改
 *
 * @author zhangbi617
 * @date 2018-09-07
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("select u from User u")
    @EntityGraph(value = "User.userProfile", type = EntityGraph.EntityGraphType.FETCH)
    List<User> getAllUser();

    @Query("select u from User u join fetch u.userProfile")
    List<User> getAllUserJoinFetch();
}
