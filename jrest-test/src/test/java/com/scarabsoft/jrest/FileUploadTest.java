package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Param;
import com.scarabsoft.jrest.annotation.Post;
import com.scarabsoft.jrest.annotation.Put;
import com.scarabsoft.jrest.converter.GsonConverterFactory;
import com.scarabsoft.jrest.converter.exception.StringExceptionConverterFactory;
import com.scarabsoft.jrest.domain.FileEntity;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JRestTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class FileUploadTest {

    private JRest jrest;
    private File file;
    private InputStream stream;
    private byte[] array;

    @Before
    public void setup() {
        jrest = new JRest.Builder().baseUrl("http://localhost:1337/v1/file")
                .converterFactory(new GsonConverterFactory())
                .exceptionFactory(new StringExceptionConverterFactory())
                .build();
        URL uri = ClassLoader.getSystemResource("char-willy.png");
        try {
            file = new File(uri.toURI());
            stream = new FileInputStream(file);
            array = Files.readAllBytes(Paths.get(uri.toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void POSTfile() {
        UploadApp app = jrest.create(UploadApp.class);
        FileEntity fileEntity = app.POSTfile(file);
        Assert.assertThat(fileEntity.getContentType(), Matchers.is("image/png"));
        Assert.assertThat(fileEntity.getFilename(), Matchers.is("file"));
        Assert.assertThat(fileEntity.getFilesize(), Matchers.is(206087L));
    }

    @Test
    public void POSTInputstream() {
        UploadApp app = jrest.create(UploadApp.class);
        FileEntity fileEntity = app.POSTInputStream(stream);
        Assert.assertThat(fileEntity.getContentType(), Matchers.is("image/png"));
        Assert.assertThat(fileEntity.getFilename(), Matchers.is("file"));
        Assert.assertThat(fileEntity.getFilesize(), Matchers.is(206087L));
    }

    @Test
    public void POSTByteArray() throws IOException {
        UploadApp app = jrest.create(UploadApp.class);
        FileEntity fileEntity = app.POSTByteArray(array);
        Assert.assertThat(fileEntity.getContentType(), Matchers.is("image/png"));
        Assert.assertThat(fileEntity.getFilename(), Matchers.is("file"));
        Assert.assertThat(fileEntity.getFilesize(), Matchers.is(206087L));
    }

    @Test
    public void PUTfile() {
        UploadApp app = jrest.create(UploadApp.class);
        FileEntity fileEntity = app.PUTfile(file);
        Assert.assertThat(fileEntity.getContentType(), Matchers.is("image/png"));
        Assert.assertThat(fileEntity.getFilename(), Matchers.is("file"));
        Assert.assertThat(fileEntity.getFilesize(), Matchers.is(206087L));
    }

    @Test
    public void PUTInputstream() {
        UploadApp app = jrest.create(UploadApp.class);
        FileEntity fileEntity = app.PUTInputStream(stream);
        Assert.assertThat(fileEntity.getContentType(), Matchers.is("image/png"));
        Assert.assertThat(fileEntity.getFilename(), Matchers.is("file"));
        Assert.assertThat(fileEntity.getFilesize(), Matchers.is(206087L));
    }

    @Test
    public void PUTByteArray() throws IOException {
        UploadApp app = jrest.create(UploadApp.class);
        FileEntity fileEntity = app.PUTByteArray(array);
        Assert.assertThat(fileEntity.getContentType(), Matchers.is("image/jpg"));
        Assert.assertThat(fileEntity.getFilename(), Matchers.is("file"));
        Assert.assertThat(fileEntity.getFilesize(), Matchers.is(206087L));
    }


    interface UploadApp {

        @Post
        FileEntity POSTfile(@Param(value = "file",contentType = "image/png") File f);

        @Post
        FileEntity POSTInputStream(@Param(value = "file", contentType = "image/png") InputStream stream);

        @Post
        FileEntity POSTByteArray(@Param(value = "file", contentType = "image/png") byte[] array);

        @Put
        FileEntity PUTfile(@Param(value = "file" , contentType = "image/png") File f);

        @Put
        FileEntity PUTInputStream(@Param(value = "file", contentType = "image/png") InputStream stream);

        @Put
        FileEntity PUTByteArray(@Param(value = "file", contentType = "image/jpg") byte[] array);

    }
}
