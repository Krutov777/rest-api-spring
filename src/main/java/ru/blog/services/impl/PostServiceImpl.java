package ru.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.blog.exceptions.PostNotFoundException;
import ru.blog.dto.request.AddPostRequest;
import ru.blog.dto.response.PostResponse;
import ru.blog.exceptions.AccessException;
import ru.blog.exceptions.AccountNotFoundException;
import ru.blog.exceptions.AuthorNotFoundException;
import ru.blog.models.Account;
import ru.blog.models.Author;
import ru.blog.models.Post;
import ru.blog.repositories.AccountRepository;
import ru.blog.repositories.AuthorRepository;
import ru.blog.repositories.PostRepository;
import ru.blog.security.details.AccountUserDetails;
import ru.blog.services.PostService;
import ru.blog.utils.constants.GlobalConstants;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import static ru.blog.dto.response.PostResponse.from;


@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postsRepository;

    private final AuthorRepository authorsRepository;

    private final AccountRepository accountRepository;

    @Override
    public PostResponse getPost(Long postId, Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        if (!account.getRole().equals(Account.Role.USER) && !account.getRole().equals(Account.Role.ADMIN)) {
            throw new AccessException(GlobalConstants.ACCESS_EXCEPTION);
        } else {
            return from(postsRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(MessageFormat.format("Пост не с id = {0} не найден", postId))));
        }
    }

    @Override
    public PostResponse addPost(AddPostRequest addPostRequest, Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        if (!account.getRole().equals(Account.Role.USER) && !account.getRole().equals(Account.Role.ADMIN)) {
            throw new AccessException(GlobalConstants.ACCESS_EXCEPTION);
        } else {
            Author author = authorsRepository
                    .findById(addPostRequest.getAuthorId())
                    .orElseThrow((Supplier<RuntimeException>) ()
                            -> new AuthorNotFoundException(addPostRequest.getAuthorId()));

            Post newPost = Post.builder()
                    .createdAt(LocalDateTime.now())
                    .state(Post.State.PUBLISHED)
                    .text(addPostRequest.getText())
                    .title(addPostRequest.getTitle())
                    .author(author)
                    .build();

            return from(postsRepository.save(newPost));
        }
    }

    private Account getAccount(Account requestAccount) {
        return accountRepository.findByEmail(requestAccount.getEmail())
                .orElseThrow(() -> new AccountNotFoundException(GlobalConstants.ACCOUNT_NOT_FOUND));
    }

    private Account getAccountFromAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new AuthenticationException(GlobalConstants.UNAUTHORIZED) {
            };
        } else {
            AccountUserDetails userDetails = (AccountUserDetails) authentication.getPrincipal();
            return userDetails.getUser();
        }
    }
}
