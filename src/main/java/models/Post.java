package models;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@NonNull
@NoArgsConstructor
public class Post {

    private int id; // Server-generated ID
    @NonNull
    private int userId;
    @NonNull
    private String title;
    @NonNull
    private String body;
    private List<Comment> comments;

}
