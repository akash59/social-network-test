package models;

import lombok.*;

/**
 * Represents an Address entity.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NonNull
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;
}
