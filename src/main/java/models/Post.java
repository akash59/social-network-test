package models;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@NonNull
@NoArgsConstructor

/**
 * Represents a Post entity.
 */
public class Post {

    private int id; // Server-generated ID
    @NonNull
    private int userId;
    @NonNull
    private String title;
    @NonNull
    private String body;
    @NonNull
    private List<Comment> comments;

}
