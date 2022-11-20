package ru.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.blog.dto.request.SignUpForm;
import ru.blog.dto.response.SignUpResponse;
import ru.blog.exceptions.AccountNotFoundException;
import ru.blog.exceptions.BadPasswordException;
import ru.blog.exceptions.OccupiedEmailException;
import ru.blog.models.Account;
import ru.blog.repositories.AccountRepository;
import ru.blog.security.details.AccountUserDetails;
import ru.blog.services.SignUpService;
import ru.blog.utils.constants.GlobalConstants;

@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final AccountRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Override
    public SignUpResponse signUp(SignUpForm form) {
        Account account = Account.from(form);
        account.setPassword(passwordEncoder.encode(form.getPassword()));
        account.setState(Account.State.CONFIRMED);

        if (accountsRepository.findByEmail(account.getEmail()).isPresent()) {
            String OCCUPIED_EMAIL = "Пользователь с таким email уже существует";
            throw new OccupiedEmailException(OCCUPIED_EMAIL);
        }
        if (!form.getPassword().equals(form.getRepeatPassword())) {
            String PASSWORDS_DONT_MATCH = "Пароли не совпадают";
            throw new BadPasswordException(PASSWORDS_DONT_MATCH);
        }
        accountsRepository.save(account);

        return SignUpResponse.from(account);
    }

    @Override
    public void signOut(Authentication authentication) {
        Account accountRequest = getAccountFromAuthentication(authentication);
        Account account = getAccount(accountRequest);
        accountsRepository.delete(account);
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
