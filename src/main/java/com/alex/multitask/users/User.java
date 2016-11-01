package com.alex.multitask.users;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by alex on 29.10.2016.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(name = "date_of_creation", nullable = false, unique = false)
    private Date dateOfCreation;
    @Column(name = "access_level", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;
    @Column(name = "is_delete", nullable = false, unique = false)
    private boolean isDelete;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.dateOfCreation = new Date();
        this.accessLevel = AccessLevel.USER;
        this.isDelete = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
