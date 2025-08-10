package service;

import com.atcorp.manageusers.dao.UserManagementDao;
import com.atcorp.manageusers.entity.UserEntity;
import com.atcorp.manageusers.model.User;
import com.atcorp.manageusers.service.UserManagementServiceImpl;
import com.atcorp.manageusers.utils.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {

    @Mock
    UserManagementDao userManagementDao;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    UserManagementServiceImpl userManagementServiceImpl;

    @Test
    public void testEnrollmentSuccessful () throws ParseException {
        User user = new User("tanksale.ajnkya@gmail.com", "Ajinkya@1012", "10/12/1998", "Male", "9762289985");

        String formatPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date parsedDate = dateFormat.parse(user.getDob() + " 00:00:00");
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        UserEntity savedUser = new UserEntity.UserEntityBuilder().setUserName("tanksale.ajnkya@gmail.com")
                .setPassword("Ajinkya@1012")
                .setDob(timestamp)
                .setGender("Male")
                .setPhoneNumber("9762289985").build();


        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPass");
        when(userManagementDao.save(any(UserEntity.class))).thenReturn(savedUser);
        when(userManagementDao.findByUserName(any(String.class))).thenReturn(null);

        String result = userManagementServiceImpl.createCustomer(user);

        assertNotNull(result);
        assertEquals("User Created Successfully", result);
        verify(userManagementDao).save(any(UserEntity.class));
    }

    @Test
    public void testEnrollmentFailure1 () throws ParseException {
        User user = new User("tanksale.ajnkya@gmail.com", "Ajinkya@1012", "10/12/1998", "Male", "9762289985");

        String formatPattern = "dd/MM/yyyy HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
        Date parsedDate = dateFormat.parse(user.getDob() + " 00:00:00");
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        UserEntity savedUser = new UserEntity.UserEntityBuilder().setUserName("tanksale.ajnkya@gmail.com")
                .setPassword("Ajinkya@1012")
                .setDob(timestamp)
                .setGender("Male")
                .setPhoneNumber("9762289985").build();

        when(userManagementDao.findByUserName(any(String.class))).thenReturn(savedUser);

        assertThrows(UserAlreadyExistsException.class, () -> {
            userManagementServiceImpl.createCustomer(user);
        });
    }

    @Test
    public void testEnrollmentFailure2 () {
        User user = new User("tanksale.ajnkya@gmail.com", "Ajinkya@1012", "10-12-1998", "Male", "9762289985");

        when(userManagementDao.findByUserName(any(String.class))).thenReturn(null);

        assertThrows(ParseException.class, () -> {
            userManagementServiceImpl.createCustomer(user);
        });
    }

}
