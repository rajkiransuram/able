package com.acme.account;

import com.acme.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<User>();
        }
        user.setAccount(this);
        this.users.add(user);
    }
}
