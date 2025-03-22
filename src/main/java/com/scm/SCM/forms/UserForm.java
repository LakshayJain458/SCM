package com.scm.SCM.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, message = "Username must be at least 3 characters long.")
    private String username;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
    )
    private String password;

    @NotBlank(message = "Please tell us something about yourself.")
    private String about;

    @NotBlank(message = "Phone number is required.")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain only digits.")
    private String phoneNumber;

}