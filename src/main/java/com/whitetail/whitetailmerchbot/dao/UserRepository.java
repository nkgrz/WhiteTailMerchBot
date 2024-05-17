package com.whitetail.whitetailmerchbot.dao;

import com.whitetail.whitetailmerchbot.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
