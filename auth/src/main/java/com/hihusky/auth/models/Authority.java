package com.hihusky.auth.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Id
    @Column(name = "authority")
    private String authority;
    
}
