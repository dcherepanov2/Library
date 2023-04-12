package com.example.MyBookShopApp.service.userServices;

import com.example.MyBookShopApp.data.user.Profile;
import com.example.MyBookShopApp.data.user.User;
import com.example.MyBookShopApp.dto.ProfileResponse;
import com.example.MyBookShopApp.repo.userrepos.ProfileRepo;
import com.example.MyBookShopApp.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
@Service
public class ProfileService {
    private final ProfileRepo profileRepo;

    private final UserServiceImpl userService;

    @Autowired
    public ProfileService(ProfileRepo profileRepo, UserServiceImpl userService) {
        this.profileRepo = profileRepo;
        this.userService = userService;
    }

    public Profile findProfileByUser(User user){
        return profileRepo.findProfileByUser(user);
    }

    public ProfileResponse makeProfileResponseToMainPage(String token) {
        User userByToken = userService.findUserByToken(token);
        Profile profileByUser = this.findProfileByUser(userByToken);
        ProfileResponse profileResponse;
        if (profileByUser == null)
            return new ProfileResponse("Test name", 0.00);
        profileResponse = new ProfileResponse(profileByUser.getName(), 0.00);
        return profileResponse;
    }

    public ProfileResponse makeProfileResponseToMainPage(JwtUser jwtUser) {
        User userByToken = userService.findByHash(jwtUser.getHash());
        Profile profileByUser = this.findProfileByUser(userByToken);
        ProfileResponse profileResponse;
        if (profileByUser == null)
            return new ProfileResponse("Test name", 0.00);
        profileResponse = new ProfileResponse(profileByUser.getName(), 0.00);
        return profileResponse;
    }
}
