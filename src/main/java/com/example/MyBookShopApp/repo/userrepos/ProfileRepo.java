package com.example.MyBookShopApp.repo.userrepos;

import com.example.MyBookShopApp.data.user.Profile;
import com.example.MyBookShopApp.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile,Long> {
    Profile findProfileByUser(User user);
}
