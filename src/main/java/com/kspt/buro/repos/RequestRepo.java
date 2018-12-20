package com.kspt.buro.repos;

import com.kspt.buro.domain.Request;
import com.kspt.buro.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepo extends CrudRepository<Request, Long> {

    List<Request> findByPtype(String ptype);
    List<Request> findByAuthor(User user);
//    List<Request> findPtype(List<Request> requests, String ptype);
}
