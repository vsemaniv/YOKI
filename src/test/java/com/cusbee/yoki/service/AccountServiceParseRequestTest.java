package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.service.serviceimpl.AccountServiceImpl;
import com.cusbee.yoki.utils.Validator;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class AccountServiceParseRequestTest {

    @Mock
    private Validator validator;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    }

    @Test
    public void parseCreateAccountRequestTest() throws BaseException {
        String encodedPassword = "ENCODED";
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        Account account = service.parseRequest(request, CrudOperation.CREATE);
        verify(validator, times(1)).validateRequestNotNull(request);
        verify(validator, times(1)).validateAccountCreationRequest(request);
        verify(passwordEncoder, times(1)).encode(request.getNewPassword());
        verifyNoMoreInteractions(validator);

        assertEquals(account.getEmail(), request.getEmail());
        assertEquals(account.getUsername(), request.getUsername());
        assertEquals(account.getFirstname(), request.getFirstname());
        assertEquals(account.getLastname(), request.getLastname());
        assertEquals(account.getPassword(), encodedPassword);
        assertEquals(account.getAuthority(), request.getAuthority());
        assertTrue(account.getEnabled());
    }

    @Test
    public void parseUpdateAccountRequestWithoutPasswordTest() throws BaseException {
        request.setNewPassword("");
        when(validator.validateAccountUpdateRequest(request)).thenReturn(account);
        Account account = service.parseRequest(request, CrudOperation.UPDATE);
        verify(validator, times(1)).validateRequestNotNull(request);
        verify(validator, times(1)).validateAccountUpdateRequest(request);
        verifyNoMoreInteractions(validator);

        assertEquals(account.getEmail(), request.getEmail());
        assertEquals(account.getUsername(), request.getUsername());
        assertEquals(account.getFirstname(), request.getFirstname());
        assertEquals(account.getLastname(), request.getLastname());
        assertEquals(account.getAuthority(), request.getAuthority());
        assertNull(account.getPassword());
    }

    @Test
    @Parameters(method = "getNormalPasswords")
    public void updateAccountPasswordTest(String oldPassword, String newPassword) throws BaseException {
        request.setNewPassword(newPassword);
        request.setOldPassword(oldPassword);
        account.setPassword(oldPassword);
        when(validator.validateAccountUpdateRequest(request)).thenReturn(account);
        when(passwordEncoder.encode(anyString())).thenReturn(oldPassword).thenReturn("ENCODED");
        Account result = service.parseRequest(request, CrudOperation.UPDATE);

        assertNotNull(result.getPassword());
    }

    @Test
    public void oldPasswordDoesntMatchTest() throws BaseException {
        String oldPassword = "oldpasswordFromDb123";
        request.setNewPassword("NEW");
        account.setPassword(oldPassword);
        when(validator.validateAccountUpdateRequest(request)).thenReturn(account);
        //password encoder tries to encode old password coming from request in order to compare encoded value with value from db
        when(passwordEncoder.encode(anyString())).thenReturn("does not match");
        thrown.expect(ApplicationException.class);

        service.parseRequest(request, CrudOperation.UPDATE);
    }

    @Test
    public void accountUpdateInvalidPasswordTest(String oldPassword, String newPassword) throws BaseException {
        request.setNewPassword(newPassword);
        request.setOldPassword(oldPassword);
        account.setPassword(oldPassword);
        thrown.expect(ApplicationException.class);
        when(validator.validateAccountUpdateRequest(request)).thenReturn(account);
        when(passwordEncoder.encode(anyString())).thenReturn(oldPassword);


    }

    private Object[] getNormalPasswords() {
        return $(
                $("normalLength3","normalTREND"),
                $("5%trasdft", "gto%12rs"),
                $("ssssssssssssssss", "1DRAWERRRRR")
        );
    }
}