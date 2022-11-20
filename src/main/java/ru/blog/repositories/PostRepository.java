package ru.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.blog.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
