package com.alex.multitask;

import javax.persistence.*;

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

//    @Column(name = "level", nullable = false, unique = false)
//    @Enumerated(EnumType.STRING)
    private String level;
    private int delete;

    public User() {
    }

    public User(String name) {
        this.name = name;

        this.level = "user";
        this.delete = 0;
    }

    public boolean isDelete() {
        return delete == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}
