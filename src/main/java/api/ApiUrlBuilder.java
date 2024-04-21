package api;

public class ApiUrlBuilder {

    public static String buildPostUrl() {
        return ApiEndpoints.POSTS;
    }

    public static String buildPostUrl(int postId) {
        return ApiEndpoints.POSTS + "/" + postId;
    }

    public static String buildCommentsUrl(int postId) {
        return ApiEndpoints.POSTS + "/" + postId + ApiEndpoints.COMMENTS;
    }

    // Add other methods for building URLs as needed
}
