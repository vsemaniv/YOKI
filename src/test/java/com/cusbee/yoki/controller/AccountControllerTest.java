package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.AccountService;
import com.cusbee.yoki.service.NullPointerService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
    @Mock
    private AccountService accountService;

    @Mock
    private NullPointerService npService;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    private AccountController controller;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AccountModel request = new AccountModel();
    private Account account = new Account();

    @Test
    public void createAccountTest() throws BaseException {
        account.setUsername("username");
        when(accountService.parseRequest(request, CrudOperation.CREATE)).thenReturn(account);
        YokiResult<Account> result = controller.create(request);
        verify(accountService, times(1)).parseRequest(request, CrudOperation.CREATE);
        verify(accountService, times(1)).add(account);
        verifyNoMoreInteractions(accountService, npService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), account);
    }

    @Test
    public void updateAccountTest() throws BaseException {
        account.setUsername("greyjoy");
        when(accountService.parseRequest(request, CrudOperation.UPDATE)).thenReturn(account);
        YokiResult<Account> result = controller.update(request);
        verify(accountService, times(1)).parseRequest(request, CrudOperation.UPDATE);
        verify(accountService, times(1)).add(account);
        verifyNoMoreInteractions(accountService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), account);
    }

    @Test
    public void blockAccountTest() throws BaseException {
        Long id = 22L;
        YokiResult<Account> result = controller.block(id);
        verify(npService, times(1)).isNull(id);
        verify(accountService, times(1)).activation(id, CrudOperation.BLOCK);
        verifyNoMoreInteractions(accountService, npService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
    }

    @Test
    public void unblockAccountTest() throws BaseException {
        Long id = 367L;
        YokiResult<Account> result = controller.unblock(id);
        verify(npService, times(1)).isNull(id);
        verify(accountService, times(1)).activation(id, CrudOperation.UNBLOCK);
        verifyNoMoreInteractions(accountService, npService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
    }

    @Test
    public void getAccountByIdTest() throws BaseException {
        Long id = 373367L;
        account.setUsername("No_way");
        account.setEmail("way@hard.com");
        when(accountService.get(id)).thenReturn(account);

        YokiResult<Account> result = controller.get(id);
        verify(npService, times(1)).isNull(id);
        verify(accountService, times(1)).get(id);
        verifyNoMoreInteractions(accountService, npService);
        assertEquals(result.getStatus(), YokiResult.Status.SUCCESS);
        assertEquals(result.getData(), account);
    }

    @Test
    public void getAllAccountsTest() throws BaseException {
        controller.getAll();
        verify(accountRepository, times(1)).findAll();
        verifyNoMoreInteractions(accountService);
    }
}
