package me.dimitri.fileupload;

import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class UsageExample {

    public static void main(String[] args) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/files/cat.png");
        if (file.exists()) {
            boolean success = uploadFile("http://localhost:8080/upload", file);
            System.out.println("Success: " + success);
        }
    }

    private static boolean uploadFile(String url, File file) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("text/json"), file))
                .build();

        Request request = new Request.Builder()
                .url(url)
                /*
                Basically you just take the string "username:passowrd" you set inside
                your config.yml and encode it in base64, then place it after "Basic" just like
                shown here.
                 */
                .addHeader("Authorization", "Basic c2hlcmxvY2s6cGFzc3dvcmQ=")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            System.out.println("Something went wrong");
            return false;
        }
        return true;
    }
}
