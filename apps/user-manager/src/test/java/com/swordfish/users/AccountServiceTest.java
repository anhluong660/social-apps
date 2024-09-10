package com.swordfish.users;

import com.swordfish.users.dto.request.RequestRegister;
import com.swordfish.users.repository.AccountRepository;
import com.swordfish.users.repository.UserRepository;
import com.swordfish.users.service.AccountService;
import com.swordfish.users.utils.JwtUtil;
import com.swordfish.users.utils.UserIdGenerator;
import com.swordfish.utils.enums.ErrorCode;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserIdGenerator userIdGenerator;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    public void register_existAccount() {
        final String username = "username";

        RequestRegister request = new RequestRegister();
        request.setUsername(username);

        when(accountRepository.existsByUsername(username))
                .thenReturn(true);

        ErrorCode error = accountService.register(request);

        Assert.assertEquals(ErrorCode.ACCOUNT_EXIST, error);
    }

    @Test
    public void register_success() {
        final String username = "username";

        RequestRegister request = new RequestRegister();
        request.setUsername(username);
        request.setSex("Male");
        request.setPassword("123456");
        request.setFullName("Nguyen Van A");
        request.setDateOfBirth("1999-01-28T00:00:00Z");

        when(accountRepository.existsByUsername(username))
                .thenReturn(false);

        when(userIdGenerator.getNewId()).thenReturn(20242023L);

        ErrorCode error = accountService.register(request);
        Assert.assertEquals(ErrorCode.SUCCESS, error);
    }
}
