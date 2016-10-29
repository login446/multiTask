package com.alex.multitask;

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
    private Date date_of_creation;
    @Column(name = "access_level", nullable = false, unique = false)
    @Enumerated(EnumType.STRING)
    private AccessLevel access_level;
    private boolean is_delete;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.date_of_creation = new Date();
        this.access_level = AccessLevel.USER;
        this.is_delete = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    public AccessLevel getAccess_level() {
        return access_level;
    }

    public boolean is_delete() {
        return is_delete;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }

    public void setAccess_level(AccessLevel access_level) {
        this.access_level = access_level;
    }
}
