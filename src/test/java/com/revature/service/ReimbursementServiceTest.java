package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.exception.InvalidImageException;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import io.javalin.http.UploadedFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReimbursementServiceTest {

    private ReimbursementDao mockDao = mock(ReimbursementDao.class);
    private ReimbursementService reimbursementService = new ReimbursementService(mockDao);

    @Test
    public void test_getReimbursements_positive() throws SQLException {
        List<Reimbursement> reimbursements = new ArrayList<>();
        reimbursements.add(new Reimbursement(1, 2, "test1", "test2", "test3", "r",
                new User(1, "test4", "test", "test", "test", "test", "test"),
                new User(2, "a", "b", "c", "d", "e", "f"), "test5", "test6"));
        reimbursements.add(new Reimbursement(1, 2, "a", "b", "c", "r",
                new User(1, "test1", "test", "test", "test", "test", "test"),
                new User(2, "a", "b", "c", "d", "e", "f"), "d", "e"));

        when(mockDao.getReimbursements()).thenReturn(reimbursements);

        List<ResponseReimbursementDTO> expected = new ArrayList<>();
        expected.add(new ResponseReimbursementDTO(1, 2, "test1", "test2", "test3", "r", "test4", "a", "test5", "test6"));
        expected.add(new ResponseReimbursementDTO(1, 2, "a", "b", "c", "r", "test1", "a", "d", "e"));

        List<ResponseReimbursementDTO> actual = reimbursementService.getReimbursements();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test_getReimbursementsById_positive() throws SQLException {
        List<Reimbursement> reimbursements = new ArrayList<>();
        reimbursements.add(new Reimbursement(1, 2, "test1", "test2", "test3", "r",
                new User(1, "test4", "test", "test", "test", "test", "test"),
                new User(2, "a", "b", "c", "d", "e", "f"), "test5", "test6"));
        reimbursements.add(new Reimbursement(1, 2, "a", "b", "c", "r",
                new User(1, "test1", "test", "test", "test", "test", "test"),
                new User(2, "a", "b", "c", "d", "e", "f"), "d", "e"));

        when(mockDao.getReimbursements(1)).thenReturn(reimbursements);

        List<ResponseReimbursementDTO> expected = new ArrayList<>();
        expected.add(new ResponseReimbursementDTO(1, 2, "test1", "test2", "test3", "r", "test4", "a", "test5", "test6"));
        expected.add(new ResponseReimbursementDTO(1, 2, "a", "b", "c", "r", "test1", "a", "d", "e"));


        List<ResponseReimbursementDTO> actual = reimbursementService.getReimbursements("1");

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void test_addReimbursement_invalidImage() {
        InputStream notImage = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        UploadedFile testFile = new UploadedFile(notImage, "test", "test", "test", 0);

        AddReimbursementDTO dto = new AddReimbursementDTO();
        dto.setReceipt(testFile);

        Assertions.assertThrows(InvalidImageException.class, () -> {
            reimbursementService.addReimbursement("1", dto);
        });
    }

    @Test
    public void test_handleReimbursement_positive() throws SQLException {
        when(mockDao.handleReimbursement(1, 2, 3, "test")).thenReturn(true);
        reimbursementService.handleReimbursement("1", "2", 3, "test");
    }
}
