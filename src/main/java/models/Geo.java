package models;

import lombok.*;

/**
 * Represents a Geo entity.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NonNull
public class Geo {
    private String lat;
    private String lng;
}

