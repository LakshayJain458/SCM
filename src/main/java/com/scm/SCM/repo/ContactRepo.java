package com.scm.SCM.repo;

import com.scm.SCM.model.Contacts;
import com.scm.SCM.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contacts,String> {

    List<Contacts> findByUser(User user);

    @Query("SELECT c from Contacts c where c.user.userId = :userId")
    List<Contacts> findByUserId(@Param("userId") String userId);

}
