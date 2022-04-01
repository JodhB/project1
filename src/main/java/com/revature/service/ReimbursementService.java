package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.exception.InvalidImageException;
import com.revature.model.Reimbursement;
import com.revature.utility.CloudStorageUtility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {

    private ReimbursementDao reimbursementDao;

    public ReimbursementService() {
        reimbursementDao = new ReimbursementDao();
    }

    public ReimbursementService(ReimbursementDao mockDao) {
        reimbursementDao = mockDao;
    }

    public List<ResponseReimbursementDTO> getReimbursements() throws SQLException {
        List<Reimbursement> reimbursements = reimbursementDao.getReimbursements();
        List<ResponseReimbursementDTO> reimbursementDTOS = new ArrayList<>();

        for(Reimbursement reimbursement: reimbursements) {
            reimbursementDTOS.add(new ResponseReimbursementDTO(reimbursement.getId(), reimbursement.getAmount(), reimbursement.getSubmitted(),
                    reimbursement.getResolved(), reimbursement.getDescription(), reimbursement.getReceiptUrl(), reimbursement.getAuthor().getUsername(),
                    reimbursement.getResolver().getUsername(), reimbursement.getStatus(), reimbursement.getType()));
        }

        return reimbursementDTOS;
    }

    public List<ResponseReimbursementDTO> getReimbursements(String userId) throws SQLException {
        try {
            int id = Integer.parseInt(userId);

            List<Reimbursement> reimbursements = reimbursementDao.getReimbursements(id);
            List<ResponseReimbursementDTO> reimbursementDTOS = new ArrayList<>();

            for (Reimbursement reimbursement : reimbursements) {
                reimbursementDTOS.add(new ResponseReimbursementDTO(reimbursement.getId(), reimbursement.getAmount(), reimbursement.getSubmitted(),
                        reimbursement.getResolved(), reimbursement.getDescription(), reimbursement.getReceiptUrl(), reimbursement.getAuthor().getUsername(),
                        reimbursement.getResolver().getUsername(), reimbursement.getStatus(), reimbursement.getType()));
            }

            return reimbursementDTOS;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id must be a valid int");
        }
    }

    public void addReimbursement(String authorId, AddReimbursementDTO dto) throws IOException, InvalidImageException, SQLException {
        String contentType = dto.getReceipt().getContentType();

        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/gif")) {
            throw new InvalidImageException("Image must be a JPEG, PNG, or GIF");
        }

        String url = CloudStorageUtility.uploadFile(dto.getReceipt());

        try {
            int id = Integer.parseInt(authorId);
            reimbursementDao.addReimbursement(id, dto, url);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id must be a valid int");
        }
    }

    public void handleReimbursement(String reimbursementId, String status, int resolverId, String resolved) throws SQLException {
        try {
            int id = Integer.parseInt(reimbursementId);
            int s = Integer.parseInt(status);
            reimbursementDao.handleReimbursement(id, s, resolverId, resolved);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id must be a valid int");
        }
    }

}
