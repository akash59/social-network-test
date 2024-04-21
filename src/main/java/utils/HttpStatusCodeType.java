package utils;

/**
 * Enumeration representing different types of HTTP status codes.
 */
public enum HttpStatusCodeType {
    /**
     * Represents a successful HTTP status code (2xx).
     */
    SUCCESSFUL,

    /**
     * Represents a created HTTP status code (201).
     */
    CREATED,

    /**
     * Represents a redirection HTTP status code (3xx).
     */
    REDIRECTION,

    /**
     * Represents a client error HTTP status code (4xx).
     */
    CLIENT_ERROR,

    /**
     * Represents a server error HTTP status code (5xx).
     */
    SERVER_ERROR,

    /**
     * Represents another HTTP status code that does not fall into the above categories.
     */
    OTHER

}
