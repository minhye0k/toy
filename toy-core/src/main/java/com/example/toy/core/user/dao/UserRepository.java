package com.example.toy.core.user.dao;

import com.example.toy.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.seq from User u where u.isProposable = :isProposable")
    List<Long> findSeqsByProposableIs(boolean isProposable);
}
