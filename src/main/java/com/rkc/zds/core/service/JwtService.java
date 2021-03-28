package com.rkc.zds.core.service;

import org.springframework.stereotype.Service;

import com.rkc.zds.entity.UserEntity;

import java.util.Optional;

@Service
public interface JwtService {
    String toToken(UserEntity user);

    Optional<String> getSubFromToken(String token);
}
