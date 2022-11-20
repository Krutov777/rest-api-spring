package ru.blog.exceptions;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(Throwable cause) {
        super(cause);
    }

    public PostNotFoundException(Long postId) {
        super("Post with id <" + postId + "> is not exists");
    }
}
