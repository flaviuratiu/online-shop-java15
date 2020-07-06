package org.fasttrackit.onlineshop.transfer.user;

import org.fasttrackit.onlineshop.domain.UserRole;

import javax.validation.constraints.NotNull;

public class CreateUserRequest {

    @NotNull
    private UserRole role;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
