package com.revature.utility;

import com.google.cloud.storage.*;
import io.javalin.http.UploadedFile;

import java.io.IOException;

public class CloudStorageUtility {

    private static final String PROJECT_ID = "t-kingdom-343717";
    private static final String BUCKET_NAME = "project1-images";
    private static final String GOOGLE_URL = "https://storage.googleapis.com/";

    private CloudStorageUtility(){}

    public static String uploadFile(UploadedFile file) throws IOException {

        Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
        BlobId blobId = BlobId.of(BUCKET_NAME, file.getFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.createFrom(blobInfo, file.getContent());

        return GOOGLE_URL + BUCKET_NAME + "/" + file.getFilename();
    }
}
