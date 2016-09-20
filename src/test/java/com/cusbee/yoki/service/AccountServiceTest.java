package com.cusbee.yoki.service;

import com.cusbee.yoki.dao.AccountDao;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.serviceimpl.AccountServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountDao dao;

    @Mock
    private AccountRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ValidatorService validatorService;

    @InjectMocks
    private AccountServiceImpl service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String TEST = "test";
    private static final String TEST2 = "TEST2";

    private Account account = new Account();

    @Test
    public void getUserTest() {
        account.setEmail(TEST +"@gmail.com");
        account.setUsername(TEST);
        when(dao.get(1L)).thenReturn(account);
        Account result = service.get(1L);
        verify((dao), times(1)).get(1L);
        verifyNoMoreInteractions(dao);

        assertEquals(TEST +"@gmail.com", result.getEmail());
        assertEquals(TEST, result.getUsername());
    }

    @Test
    public void getAllUsersTest() {
        List<Account> accountList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            accountList.add(new Account());
        }
        accountList.add(1, account);
        when(repository.findAll()).thenReturn(accountList);

        List<Account> result = service.getAll();
        verify(repository,times(1)).findAll();
        verifyNoMoreInteractions(repository);

        assertEquals(result.size(), 4);
        assertEquals(result.get(1), account);
    }

    @Test
    public void isAccountEnabledTest() {
        when(repository.availability(TEST)).thenReturn(account);
        service.validateUserEnabled(TEST);
        verify((repository), times(1)).availability(TEST);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void isAccountEnabledExceptionTest() {
        when(repository.availability(TEST)).thenReturn(null);
        thrown.expect(ApplicationException.class);
        service.validateUserEnabled(TEST);
    }

    /*
    @Test
    public void passwordEncoderTest() {
        when(passwordEncoder.encode(TEST)).thenReturn(TEST2);
        String result = service.encryptPassword(TEST);
        verify(passwordEncoder, times(1)).encode(TEST);
        verifyNoMoreInteractions(passwordEncoder);

        assertEquals(TEST2, result);
    }*/
}
