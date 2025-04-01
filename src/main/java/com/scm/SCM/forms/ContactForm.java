package com.scm.SCM.forms;

import com.scm.SCM.model.SocialHandles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContactForm {

    @NotBlank(message = "Username is required.")
    @Size(min = 3, message = "Username must be at least 3 characters long.")
    private String ContactName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String ContactEmail;

    @NotBlank(message = "Phone number is required.")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain only digits.")
    private String ContactPhone;

    @NotBlank(message = "Type the address of the person , if none type null.")
    private String ContactAddress;

    private MultipartFile picture;

    private String description;

    private boolean favorite = false;
    private List<SocialHandles> links;
}
