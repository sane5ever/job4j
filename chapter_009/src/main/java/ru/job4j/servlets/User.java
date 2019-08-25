package ru.job4j.servlets;

/**
 * User
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-08-25
 */
public class User {
    private Integer id;
    private String email;
    private String password;
    private Gender gender;
    private String description;

    public User() {
    }

    public User(Integer id, String email, String password, Gender gender, String description) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.description = description;
    }

    public User(String email, String password, Gender gender, String description) {
        this(null, email, password, gender, description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }
}
