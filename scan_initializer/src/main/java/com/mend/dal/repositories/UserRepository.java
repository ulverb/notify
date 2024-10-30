package com.mend.dal.repositories;

import com.mend.dal.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query(nativeQuery = true, value = """
        SELECT u.*
            FROM users u
            JOIN scans s ON u.id = s.user_id
            WHERE u.user_type = 'REGULAR'
            GROUP BY u.id
            ORDER BY COUNT(s.id) DESC
            LIMIT 10
    """)
    List<UserModel> findTop10MostActiveRegularUsers();

    Optional<UserModel> findByUsername(String username);
}
