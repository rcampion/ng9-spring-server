package com.rkc.zds.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.dto.LoginDto;
import com.rkc.zds.entity.AuthorityEntity;
import com.rkc.zds.entity.EMailEntity;
import com.rkc.zds.entity.UserEntity;
import com.rkc.zds.error.UserAlreadyExistException;

public interface UserService {
    Page<UserEntity> findUsers(Pageable pageable);
    
//    @Transactional
//	Optional<UserDto> findByUserName(String userName);
    
	UserEntity findByUserName(String userName);
    
	UserEntity findById(Integer id);

	List<UserEntity> getUsers();
	
    UserEntity getUser(int id);  
	
    // @Transactional    
    public void updateUser(UserEntity user);
    
    // @Transactional  
	void deleteUser(int id);
    
    // @Transactional    
    public void saveUser(UserEntity user);

	UserEntity registerNewUserAccount(UserEntity accountDto) throws UserAlreadyExistException;

	Page<UserEntity> searchUsers(Pageable pageable, Specification<UserEntity> spec);

	UserEntity changePassword(LoginDto loginDTO, HttpServletRequest request, HttpServletResponse response);

	Page<AuthorityEntity> findAuthorities(Pageable pageable, String userName);

	AuthorityEntity getAuthority(int id);
	
    public void saveAuthority(AuthorityEntity role);
    
    public void updateAuthority(AuthorityEntity authority);

	void deleteAuthority(int id);

}