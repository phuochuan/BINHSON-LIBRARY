package com.library.binhson.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;

@Getter
public enum Role {
    ADMIN(), USER();
}
