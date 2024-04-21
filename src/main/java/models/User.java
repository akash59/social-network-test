package models;

import lombok.*;

@Data
@RequiredArgsConstructor
@NonNull
@NoArgsConstructor

/**
 * Represents a User entity.
 */
public class User {

    private int id; // Server-generated ID
    @NonNull
    private String name;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private Address address;
    @NonNull
    private String phone;
    @NonNull
    private String website;
    @NonNull
    private Company company;

}

