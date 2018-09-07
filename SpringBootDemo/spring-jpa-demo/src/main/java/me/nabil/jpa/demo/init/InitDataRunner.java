package me.nabil.jpa.demo.init;

import me.nabil.jpa.demo.bo.User;
import me.nabil.jpa.demo.bo.UserProfile;
import me.nabil.jpa.demo.repository.UserProfileRepository;
import me.nabil.jpa.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化数据
 *
 * @author zhangbi617
 * @date 2018-09-07
 */
@Component
public class InitDataRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("test" + i);
            UserProfile userProfile = new UserProfile();
            userProfile.setAddress("test_add" + i);
            userProfileRepository.save(userProfile);
            user.setUserProfileId(userProfile.getId());

            userRepository.saveAndFlush(user);
        }
    }
}
