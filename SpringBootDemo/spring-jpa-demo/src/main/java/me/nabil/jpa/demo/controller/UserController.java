package me.nabil.jpa.demo.controller;

import me.nabil.jpa.demo.bo.User;
import me.nabil.jpa.demo.repository.UserRepository;
import me.nabil.jpa.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * user controller
 *
 * @author zhangbi617
 * @date 2018-09-07
 */
@RestController
@RequestMapping("/test/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserVo> getUsers() {
        return transfer(userRepository.findAll());
    }

    @GetMapping("hql")
    public List<UserVo> getUsersHql() {
        return transfer(userRepository.getAllUser());
    }

    @GetMapping("hqljoinfetch")
    public List<UserVo> getUsersJoinFetch() {
        return transfer(userRepository.getAllUserJoinFetch());
    }

    @GetMapping("spec")
    public List<UserVo> getUsersSpec() {
        List<User> users = userRepository.findAll(listQuerySpec());
        return transfer(users);
    }

    private Specification<User> listQuerySpec() {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @GetMapping("specfetch")
    public List<UserVo> getUsersSpecFetch() {
        List<User> users = userRepository.findAll(listQuerySpecFetch());
        return transfer(users);
    }

    private Specification<User> listQuerySpecFetch() {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            root.fetch("userProfile");
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    private List<UserVo> transfer(List<User> users) {
        List<UserVo> userVos = new ArrayList<>();
        for (User user : users) {
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setName(user.getName());
            userVo.setAddress(user.getUserProfile().getAddress());
            userVos.add(userVo);
        }
        return userVos;
    }
}
