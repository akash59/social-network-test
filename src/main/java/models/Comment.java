package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a comment entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;

}
