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

    public static String buildUserUrl() {
        return ApiEndpoints.USERS;
    }

    public static String buildUserUrl(int userId) {
        return ApiEndpoints.USERS + "/" + userId;
    }
}
