package com.bentorangel.smartinventory.dtos;

import com.bentorangel.smartinventory.domain.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {}