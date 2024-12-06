package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository <Message, Integer>{

    // Find all messages posted by a specific user (based on their accountId).
    List<Message> findByPostedBy(Integer postedBy);

}
