package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {// long for passwords?

    // I find acounts by either BOTH username and password
    Account findByUsernameAndPassword(String username, String password);
    
}

