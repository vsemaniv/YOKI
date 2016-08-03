package com.cusbee.yoki.service;

import com.cusbee.yoki.dao.AccountDao;
import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.service.serviceimpl.AccountServiceImpl;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class AccountServiceParseRequestTest {

    @Mock
    private ValidatorService validatorService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountServiceImpl service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Account account = new Account();
    AccountModel request = new AccountModel();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request.setEmail("normal@email.com");
        request.setUsername("normal username");
        request.setNewPassword("normal password");
        request.setAuthority("normal authority");
        request.setFirstname("normal first name");
        request.setLastname("normal last name");
        account.setUsername("test account1");
    }

    @Test
    public void parseCreateAccountRequestTest() {
        String encodedPassword = "ENCODED";
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(accountDao.save(any(Account.class))).thenAnswer(new Answer<Account>() {
            @Override
            public Account answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Account) args[0];
            }
        });
        Account account = service.saveAccount(request, CrudOperation.CREATE);
        verify(validatorService, times(1)).validateAccountSaveRequest(request, CrudOperation.CREATE);
        verify(passwordEncoder, times(1)).encode(request.getNewPassword());
        verifyNoMoreInteractions(validatorService, passwordEncoder);

        assertEquals(account.getEmail(), request.getEmail());
        assertEquals(account.getUsername(), request.getUsername());
        assertEquals(account.getFirstname(), request.getFirstname());
        assertEquals(account.getLastname(), request.getLastname());
        assertEquals(account.getPassword(), encodedPassword);
        assertEquals(account.getAuthority(), request.getAuthority());
        assertTrue(account.getEnabled());
    }

    @Test
    public void parseUpdateAccountRequestWithoutPasswordTest() {
        request.setId(18L);
        request.setNewPassword("");
        when(accountDao.get(anyLong())).thenReturn(account);
        when(accountDao.save(account)).thenReturn(account);
        Account account = service.saveAccount(request, CrudOperation.UPDATE);
        verify(validatorService, times(1)).validateAccountSaveRequest(request, CrudOperation.UPDATE);
        verify(validatorService, times(1)).validateRequestIdNotNull(request.getId());
        verify(validatorService, times(1)).validateEntityNotNull(account, Account.class);
        verifyNoMoreInteractions(validatorService);

        assertEquals(account.getEmail(), request.getEmail());
        assertEquals(account.getUsername(), request.getUsername());
        assertEquals(account.getFirstname(), request.getFirstname());
        assertEquals(account.getLastname(), request.getLastname());
        assertEquals(account.getAuthority(), request.getAuthority());
        assertNull(account.getPassword());
    }

    @Test
    @Parameters(method = "getNormalPasswords")
    public void updateAccountPasswordTest(String oldPassword, String newPassword) {
        request.setNewPassword(newPassword);
        request.setOldPassword(oldPassword);
        account.setPassword(oldPassword);
        when(accountDao.get(anyLong())).thenReturn(account);
        when(passwordEncoder.encode(anyString())).thenReturn(oldPassword).thenReturn("ENCODED");

        when(accountDao.save(any(Account.class))).thenAnswer(new Answer<Account>() {
            @Override
            public Account answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Account) args[0];
            }
        });
        Account result = service.saveAccount(request, CrudOperation.UPDATE);

        verify(validatorService, times(1)).validateAccountSaveRequest(request, CrudOperation.UPDATE);
        assertNotNull(result.getPassword());
    }

    @Test
    public void oldPasswordDoesntMatchTest() {
        String oldPassword = "oldpasswordFromDb123";
        request.setNewPassword("NEW");
        account.setPassword(oldPassword);
        when(accountDao.get(anyLong())).thenReturn(account);
        //password encoder tries to encode old password coming from request in order to compare encoded value with value from db
        when(passwordEncoder.encode(anyString())).thenReturn("does not match");
        thrown.expect(ApplicationException.class);

        service.saveAccount(request, CrudOperation.UPDATE);
    }

    private Object[] getNormalPasswords() {
        return $(
                $("normalLength3","normalTREND"),
                $("5%trasdft", "gto%12rs"),
                $("ssssssssssssssss", "1DRAWERRRRR")
        );
    }
}
