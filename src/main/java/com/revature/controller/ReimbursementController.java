package com.revature.controller;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;

public class ReimbursementController implements Controller{

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        jwtService = JWTService.getInstance();
        reimbursementService = new ReimbursementService();
    }

    private Handler getAllReimbursements = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("Manager")) {
            throw new UnauthorizedResponse("You must be a manager to access this endpoint");
        }

        List<ResponseReimbursementDTO> reimbursements = reimbursementService.getReimbursements();
        ctx.json(reimbursements);
    };

    private Handler getReimbursementsForEmployee = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("Employee")) {
            throw new UnauthorizedResponse("You must be an employee to access this endpoint");
        }

        String userId = ctx.pathParam("user_id");
        if (!token.getBody().get("user_id").toString().equals(userId)) {
            throw new UnauthorizedResponse("You cannot obtain assignments that don't belong to yourself");
        }

        List<ResponseReimbursementDTO> reimbursements = reimbursementService.getReimbursements(userId);
        ctx.json(reimbursements);
    };

    private Handler addReimbursement = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("Employee")) {
            throw new UnauthorizedResponse("You must be an employee to access this endpoint");
        }

        String userId = ctx.pathParam("user_id");
        if (!token.getBody().get("user_id").toString().equals(userId)) {
            throw new UnauthorizedResponse("You cannot add a reimbursement for another employee");
        }

        int amount = Integer.parseInt(ctx.formParam("amount"));
        String submitted = ctx.formParam("submitted");
        String description = ctx.formParam("description");
        UploadedFile file = ctx.uploadedFile("image");
        int type = Integer.parseInt(ctx.formParam("type"));

        AddReimbursementDTO dto = new AddReimbursementDTO(amount, submitted, description, file, type);

        reimbursementService.addReimbursement(userId, dto);
        ctx.status(201);
    };

    private Handler handleReimbursement = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if (!token.getBody().get("user_role").equals("Manager")) {
            throw new UnauthorizedResponse("You must be a manager to access this endpoint");
        }

        String reimbursementId = ctx.pathParam("reimbursement_id");
        String status = ctx.queryParam("status");
        String resolved = ctx.queryParam("resolved");
        int userId = token.getBody().get("user_id", Integer.class);

        if (status == null) {
            throw new IllegalArgumentException("You need to provide a status query parameter");
        }

        reimbursementService.handleReimbursement(reimbursementId, status, userId, resolved);
        ctx.status(200);
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/reimbursements", getAllReimbursements);
        app.get("/users/{user_id}/reimbursements", getReimbursementsForEmployee);
        app.post("/users/{user_id}/reimbursements", addReimbursement);
        app.patch("/reimbursements/{reimbursement_id}", handleReimbursement);
    }
}
