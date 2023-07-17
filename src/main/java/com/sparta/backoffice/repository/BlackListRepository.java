package com.sparta.backoffice.repository;

import com.sparta.backoffice.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
    Optional<BlackList> findByToken(String tokenValue);
}
