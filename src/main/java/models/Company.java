package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a Company entity.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NonNull
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}
