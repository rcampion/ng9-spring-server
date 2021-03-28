package com.rkc.zds.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rkc.zds.error.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rkc.zds.config.security.hmac.HmacException;
import com.rkc.zds.dto.LoginDto;
import com.rkc.zds.entity.AuthorityEntity;
import com.rkc.zds.entity.ContactEntity;
import com.rkc.zds.entity.EMailEntity;
import com.rkc.zds.entity.UserEntity;
import com.rkc.zds.repository.UserRepository;
import com.rkc.zds.repository.AuthorityRepository;
import com.rkc.zds.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserEntity findByUserName(String userName) {
		Optional<UserEntity> userDto = userRepository.findByUserName(userName);

		UserEntity user = null;

		if (userDto.isPresent()) {
			user = userDto.get();

		}

		return user;
	}

	@Override
	public Page<UserEntity> findUsers(Pageable pageable) {

		return userRepository.findAll(pageable);
	}

	@Override
	public UserEntity findById(Integer id) {
		return userRepository.getOne(id);
	}

	@Override
	public List<UserEntity> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity getUser(int id) {

		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}

	@Override
	public void updateUser(UserEntity user) {

		userRepository.saveAndFlush(user);

	}

	@Override
	public void saveUser(UserEntity user) {

		userRepository.save(user);

	}

	// @Transactional

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUser(int id) {

		UserEntity user = null;
		/*
		 * // delete authorities for this user Optional<UserDto> userOptional =
		 * userRepository.findById(id);
		 * 
		 * if (userOptional.isPresent()) { user = userOptional.get(); }
		 * 
		 * if (user != null) { Set<AuthorityDto> userAuthorities =
		 * user.getAuthorities();
		 * 
		 * for (Iterator<AuthorityDto> iterator = userAuthorities.iterator();
		 * iterator.hasNext();) { AuthorityDto authority = iterator.next();
		 * authorityRepository.deleteById(authority.getId()); }
		 * 
		 * userRepository.deleteById(id); }
		 */
		userRepository.deleteById(id);
	}

	@Override
	public UserEntity registerNewUserAccount(final UserEntity accountDto) {
		if (loginExist(accountDto.getLogin())) {
			throw new UserAlreadyExistException("There is an account with that userName: " + accountDto.getLogin());
		}

		accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		accountDto.setEnabled(1);
		UserEntity user = userRepository.save(accountDto);

		AuthorityEntity role = new AuthorityEntity();
		role.setUserName(accountDto.getLogin());
		role.setAuthority("ROLE_USER");

		authorityRepository.save(role);

		return user;

	}

	private boolean loginExist(final String login) {

		UserEntity user = userRepository.findByLogin(login);
		if (user != null) {

			return true;
		}

		return false;
	}

	@Override
	public Page<UserEntity> searchUsers(Pageable pageable, Specification<UserEntity> spec) {
		return userRepository.findAll(spec, pageable);
	}

	public UserEntity changePassword(LoginDto loginDTO, HttpServletRequest request, HttpServletResponse response) {
		Optional<UserEntity> user = userRepository.findByUserName(loginDTO.getLogin());
		
		UserEntity userDto = null;
		
		if (user.isPresent()) {
			userDto = user.get();

			userDto.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
			userDto.setEnabled(1);

			userDto = userRepository.save(userDto);
		}
		return userDto;
	}

	@Override
	public Page<AuthorityEntity> findAuthorities(Pageable pageable, String userName) {

		Page<AuthorityEntity> authority = authorityRepository.findByUserName(pageable, userName);

		return authority;
	}

	@Override
	public AuthorityEntity getAuthority(int id) {
		Optional<AuthorityEntity> authority = authorityRepository.findById(id);
		if (authority.isPresent())
			return authority.get();
		else
			return null;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void updateAuthority(AuthorityEntity authority) {

		authorityRepository.saveAndFlush(authority);

	}

	@Override
	public void saveAuthority(AuthorityEntity role) {

		authorityRepository.save(role);

	}

	@Override
	public void deleteAuthority(int id) {

		authorityRepository.deleteById(id);

	}
}
