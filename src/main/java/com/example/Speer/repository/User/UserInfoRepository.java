package com.example.Speer.repository.User;

import com.example.Speer.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    Optional<UserInfo> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
