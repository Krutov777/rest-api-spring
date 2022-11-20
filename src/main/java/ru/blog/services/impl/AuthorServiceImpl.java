package ru.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.blog.dto.request.AddAuthorRequest;
import ru.blog.dto.request.AddPostToFavoritesRequest;
import ru.blog.dto.request.DeletePostFromFavoritesRequest;
import ru.blog.dto.request.UpdateAuthorRequest;
import ru.blog.dto.response.AuthorPageResponse;
import ru.blog.dto.response.AuthorResponse;
import ru.blog.exceptions.AccessException;
import ru.blog.exceptions.AccountNotFoundException;
import ru.blog.exceptions.AuthorNotFoundException;
import ru.blog.exceptions.PostNotFoundException;
import ru.blog.models.Account;
import ru.blog.models.Author;
import ru.blog.models.Post;
import ru.blog.repositories.AccountRepository;
import ru.blog.repositories.AuthorRepository;
import ru.blog.repositories.PostRepository;
import ru.blog.security.details.AccountUserDetails;
import ru.blog.services.AuthorService;
import ru.blog.utils.constants.GlobalConstants;

import java.text.MessageFormat;
import java.util.function.Supplier;

import static ru.blog.dto.response.AuthorResponse.from;


@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorsRepository;

    private final PostRepository postsRepository;

    private final AccountRepository accountRepository;

    @Value("${blog.default-page-size}")
    private int defaultPageSize;

    @Override
    public AuthorPageResponse getAuthors(int page, Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        if (account.getRole().equals(Account.Role.USER)) {
            throw new AccessException(GlobalConstants.ACCESS_EXCEPTION);
        } else {
            if (!account.getRole().equals(Account.Role.ADMIN)) {
                throw new AccessException(GlobalConstants.ERROR);
            }
            PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
            Page<Author> authorPage = authorsRepository.findAll(pageRequest);
            return AuthorPageResponse.builder()
                    .authors(from(authorPage.getContent()))
                    .totalPages(authorPage.getTotalPages())
                    .build();
        }
    }

    @Override
    public AuthorResponse addAuthor(AddAuthorRequest addAuthorRequest, Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        if (account.getRole().equals(Account.Role.USER)) {
            throw new AccessException(GlobalConstants.ACCESS_EXCEPTION);
        } else {
            if (!account.getRole().equals(Account.Role.ADMIN)) {
                throw new AccessException(GlobalConstants.ERROR);
            }
            return from(authorsRepository.save(
                    Author.builder()
                            .firstName(addAuthorRequest.getFirstName())
                            .lastName(addAuthorRequest.getLastName())
                            .build()));
        }
    }

    @Transactional
    @Override
    public AuthorResponse updateAuthor(UpdateAuthorRequest updateAuthorRequest, Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        if (account.getRole().equals(Account.Role.USER)) {
            throw new AccessException(GlobalConstants.ACCESS_EXCEPTION);
        } else {
            if (!account.getRole().equals(Account.Role.ADMIN)) {
                throw new AccessException(GlobalConstants.ERROR);
            }
            Author author = authorsRepository.findById(updateAuthorRequest.getId()).orElseThrow(() -> new AccountNotFoundException(MessageFormat.format("Автор с id = {0} не найден", updateAuthorRequest.getId())));

            author.setFirstName(updateAuthorRequest.getFirstName());
            author.setLastName(updateAuthorRequest.getLastName());

            return from(authorsRepository.save(author));
        }
    }

    @Transactional
    @Override
    public void addPostToFavorites(AddPostToFavoritesRequest addPostToFavoritesRequest, Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        if (account.getRole().equals(Account.Role.USER)) {
            throw new AccessException(GlobalConstants.ACCESS_EXCEPTION);
        } else {
            if (!account.getRole().equals(Account.Role.ADMIN)) {
                throw new AccessException(GlobalConstants.ERROR);
            }
            Long authorId = addPostToFavoritesRequest.getAuthorId();
            Author author = authorsRepository
                    .findById(authorId)
                    .orElseThrow((Supplier<RuntimeException>) ()
                            -> new AuthorNotFoundException(authorId));

            Long postId = addPostToFavoritesRequest.getPostId();
            Post favoritePost = postsRepository
                    .findById(postId)
                    .orElseThrow((Supplier<RuntimeException>) ()
                            -> new PostNotFoundException(postId));

            author.getFavorites().add(favoritePost);

            authorsRepository.save(author);
        }
    }

    @Transactional
    @Override
    public void deletePostFromFavorites(DeletePostFromFavoritesRequest deletePostFromFavoritesRequest, Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        if (account.getRole().equals(Account.Role.USER)) {
            throw new AccessException(GlobalConstants.ACCESS_EXCEPTION);
        } else {
            if (!account.getRole().equals(Account.Role.ADMIN)) {
                throw new AccessException(GlobalConstants.ERROR);
            }
            Long authorId = deletePostFromFavoritesRequest.getAuthorId();
            Author author = authorsRepository
                    .findById(authorId)
                    .orElseThrow((Supplier<RuntimeException>) ()
                            -> new AuthorNotFoundException(authorId));

            Long postId = deletePostFromFavoritesRequest.getPostId();
            Post favoritePost = postsRepository
                    .findById(postId)
                    .orElseThrow((Supplier<RuntimeException>) ()
                            -> new PostNotFoundException(postId));

            author.getFavorites().remove(favoritePost);

            authorsRepository.save(author);
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