package com.example.cloneslack.repository;


import com.example.cloneslack.domain.EnterRoom;
import com.example.cloneslack.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnterRepository extends JpaRepository<EnterRoom, Long> {
    List<EnterRoom> findByUserOrderByIdDesc(User user);
}
