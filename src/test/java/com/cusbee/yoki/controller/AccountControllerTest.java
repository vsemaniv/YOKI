package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.repositories.AccountRepository;
import com.cusbee.yoki.service.AccountService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
    @Mock
    private AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    private AccountController controller;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AccountModel request = new AccountModel();
    private Account account = new Account();

    @Test
    public void createAccountTest() {
        account.setUsername("username");
        when(accountService.saveAccount(request, CrudOperation.CREATE)).thenReturn(account);
        YokiResult<Account> result = controller.create(request);
        verify(accountService, times(1)).saveAccount(request, CrudOperation.CREATE);
        verifyNoMoreInteractions(accountService);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getData(), account);
    }

    @Test
    public void updateAccountTest() {
        account.setUsername("greyjoy");
        when(accountService.saveAccount(request, CrudOperation.UPDATE)).thenReturn(account);
        YokiResult<Account> result = controller.update(request);
        verify(accountService, times(1)).saveAccount(request, CrudOperation.UPDATE);
        verifyNoMoreInteractions(accountService);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getData(), account);
    }

    @Test
    public void blockAccountTest() {
        Long id = 22L;
        when(accountService.processActivation(id, false)).thenReturn(account);
        YokiResult<Account> result = controller.block(id);
        verify(accountService, times(1)).processActivation(id, false);
        verifyNoMoreInteractions(accountService);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(account, result.getData());
    }

    @Test
    public void unblockAccountTest() {
        Long id = 367L;
        when(accountService.processActivation(id, true)).thenReturn(account);
        YokiResult<Account> result = controller.unblock(id);
        verify(accountService, times(1)).processActivation(id, true);
        verifyNoMoreInteractions(accountService);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(account, result.getData());
    }

    @Test
    public void getAccountByIdTest() {
        Long id = 373367L;
        IdModel idModel = new IdModel();
        idModel.setId(id);
        account.setUsername("No_way");
        account.setEmail("way@hard.com");
        when(accountService.get(id)).thenReturn(account);

        YokiResult<Account> result = controller.get(idModel);
        verify(accountService, times(1)).get(id);
        verifyNoMoreInteractions(accountService);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getData(), account);
    }

    @Test
    public void getAllAccountsTest() {
        controller.getAll();
        verify(accountService, times(1)).getAll();
        verifyNoMoreInteractions(accountService);
    }
}
