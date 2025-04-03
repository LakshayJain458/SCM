package com.scm.SCM.repo;

import com.scm.SCM.model.Contacts;
import com.scm.SCM.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contacts, String> {

    Page<Contacts> findByUser(User user, Pageable pageable);

    @Query("SELECT c from Contacts c where c.user.userId = :userId")
    void findByUserId(@Param("userId") String userId);

    Page<Contacts> findByUserAndContactNameContaining(User user, String contactName, Pageable pageable);

    Page<Contacts> findByUserAndContactEmailContaining(User user, String contactEmail, Pageable pageable);

    Page<Contacts> findByUserAndContactPhoneContaining(User user, String contactPhone, Pageable pageable);

}
