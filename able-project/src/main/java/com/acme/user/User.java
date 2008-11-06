package com.acme.user;

import com.wideplay.warp.persist.Transactional;
import com.acme.account.Account;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@NamedQueries({
    @NamedQuery(name = User.Q_BY_USER_ID, query = "select u from User u where id = :userId"),
    @NamedQuery(name = User.Q_BY_EMAIL, query = "select u from User u where email = :email"),
    @NamedQuery(name = User.Q_BY_EMAIL_AND_PASSWORD, query = "select u from User u where email = :email and password = :password")
        })
public class User {
    public static final String Q_BY_USER_ID = "User.byUserId";
    public static final String Q_BY_EMAIL = "User.byEmail";
    public static final String Q_BY_EMAIL_AND_PASSWORD = "User.byEmailAndPassword";

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Basic
    @NotNull
    @Length(min = 1, max = 64)
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @NotNull
    @Length(min = 1, max = 64)
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @Basic
    @NotNull
    @Length(max = 64)
    @Column(name = "password")
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Embedded
    private Address address;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Transactional
    public void foo() {

    }
}
