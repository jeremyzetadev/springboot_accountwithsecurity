package com.vikingbank.entities;


import javax.persistence.*;

import java.util.Collection;

import java.util.List;


@Entity

@Table(name = "tb_user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))

public class User {


    private final boolean emailConfirmed = false;

    private final boolean isActive = true;

    private final boolean enabled = true;

    @OneToMany(mappedBy = "user")

    private List<Account> accounts;

    private String salt;


    private String publicKey;

    private String profilePicture = "default.jpg";

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "first_name")

    private String firstName;

    @Column(name = "last_name")

    private String lastName;

    private String email;

    private String password;


    private boolean isUsing2FA = false;

    private String secret;


    private String fontSize;

    private String theme;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    @JoinTable(name = "tb_users_roles",

            joinColumns = @JoinColumn(

                    name = "user_id", referencedColumnName = "id"),

            inverseJoinColumns = @JoinColumn(

                    name = "roler_id", referencedColumnName = "id")

    )

    private Collection<Role> roles;


    public User(String firstName, String lastName, String email, String password, Collection<Role> roles) {

        super();

        this.firstName = firstName;

        this.lastName = lastName;

        this.email = email.trim().toLowerCase();

        this.password = password;

        this.roles = roles;


    }


    public User(String firstName, String lastName, String email) {

        super();

        this.firstName = firstName;

        this.lastName = lastName;

        this.email = email.trim().toLowerCase();

    }


    public User() {

    }


    /**

     * A method to generate a secret for users that already existed before 2fa was added to the application.

     *

     * @param secret

     */

    public void setGeneratedSecret(String secret) {

        this.secret = secret;

    }


    public void update2FAUsage() {

        this.isUsing2FA = true;

    }


    public void updatePassword(String newPassword) {

        this.password = newPassword;

    }


    public void updateProfilePicture(String profilePicture) {

        this.profilePicture = profilePicture;

    }


    public String getSecret() {

        return secret;

    }


    private void setSecret(String secret) {

        this.secret = secret;

    }


    public boolean isUsing2FA() {

        return isUsing2FA;

    }


    private void setUsing2FA(boolean using2FA) {

        isUsing2FA = using2FA;

    }


    public Long getId() {

        return id;

    }


    private void setId(Long id) {

        this.id = id;

    }


    public String getFirstName() {

        return firstName;

    }


    private void setFirstName(String firstName) {

        this.firstName = firstName;

    }


    public String getLastName() {

        return lastName;

    }


    private void setLastName(String lastName) {

        this.lastName = lastName;

    }


    public String getEmail() {

        return email;

    }


    private void setEmail(String email) {

        this.email = email;

    }


    public String getPassword() {

        return password;

    }


    private void setPassword(String password) {

        this.password = password;

    }


    public Collection<Role> getRoles() {

        return roles;

    }


    private void setRoles(Collection<Role> roles) {

        this.roles = roles;

    }


    public List<Account> getAccounts() {

        return accounts;

    }


    private void setAccounts(List<Account> accounts) {

        this.accounts = accounts;

    }


    public boolean isEmailConfirmed() {

        return emailConfirmed;

    }


    public boolean isActive() {

        return isActive;

    }


    public String getSalt() {

        return salt;

    }


    private void setSalt(String salt) {

        this.salt = salt;

    }


    public boolean isEnabled() {

        return enabled;

    }


    public String getProfilePicture() {

        return profilePicture;

    }


    private void setProfilePicture(String profilePicture) {

        this.profilePicture = profilePicture;

    }


    public String getFontSize() {

        return fontSize;

    }


    public void setFontSize(String fontSize) {

        this.fontSize = fontSize;

    }


    public String getTheme() {

        return theme;

    }


    public void setTheme(String theme) {

        this.theme = theme;

    }


    public String getPublicKey() {

        return publicKey;

    }


    public void setOnce(String privateKeyEncryption) {

        if (this.publicKey == null) {

            this.publicKey = privateKeyEncryption;

        }

    }

}
