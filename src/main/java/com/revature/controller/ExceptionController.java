package com.revature.controller;

import com.revature.exception.ImageNotFoundException;
import com.revature.exception.InvalidImageException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;

public class ExceptionController implements Controller {

    private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    private ExceptionHandler<FailedLoginException> failedLogin = (exception, ctx) -> {
        logger.warn("Failed login. Exception message: " + exception.getMessage());
        ctx.status(400);
        ctx.json(exception.getMessage());
    };

    private ExceptionHandler<InvalidImageException> invalidImage = (exception, ctx) -> {
        logger.warn("User attempted to upload an invalid image. Exception message: " + exception.getMessage());
        ctx.status(400);
        ctx.json(exception.getMessage());
    };

    private ExceptionHandler<IllegalArgumentException> invalidArgument = (exception, ctx) -> {
        logger.warn("User input was an illegal argument. Exception message: " + exception.getMessage());
        ctx.status(400);
        ctx.json(exception.getMessage());
    };

    private ExceptionHandler<ImageNotFoundException> imageNotFound = (exception, ctx) -> {
        logger.warn("User attempted to retrieve an image that was not found. Exception message: " + exception.getMessage());
        ctx.status(404);
        ctx.json(exception.getMessage());
    };

    @Override
    public void mapEndpoints(Javalin app) {
        app.exception(FailedLoginException.class, failedLogin);
        app.exception(InvalidImageException.class, invalidImage);
        app.exception(IllegalArgumentException.class, invalidArgument);
        app.exception(ImageNotFoundException.class, imageNotFound);
    }
}