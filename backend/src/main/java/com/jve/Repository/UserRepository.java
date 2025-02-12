package com.jve.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jve.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
