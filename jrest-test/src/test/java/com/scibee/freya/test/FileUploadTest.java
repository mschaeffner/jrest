package com.scibee.freya.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.scibee.freya.Freya;
import com.scibee.freya.annotation.Post;
import com.scibee.freya.annotation.Put;
import com.scibee.freya.annotation.Param;
import com.scibee.freya.converter.GsonConverterFactory;
import com.scibee.freya.test.domain.FileEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreyaTestApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:1337")
public class FileUploadTest {

	interface UploadApp {

		@Post
		FileEntity POSTfile(@Param(name = "file") File f);

		@Post
		FileEntity POSTInputStream(@Param(name = "file") InputStream stream);

		@Post
		FileEntity POSTByteArray(@Param(name = "file") byte[] array);

		@Put
		FileEntity PUTfile(@Param(name = "file") File f);

		@Put
		FileEntity PUTInputStream(@Param(name = "file") InputStream stream);

		@Put
		FileEntity PUTByteArray(@Param(name = "file") byte[] array);

	}

	private Freya freya;
	private File file;
	private InputStream stream;
	private byte[] array;

	@Before
	public void setup() {
		freya = new Freya.Builder().baseUrl("http://localhost:1337/v1/file")
				.coverterFactory(new GsonConverterFactory()).build();
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
		UploadApp app = freya.create(UploadApp.class);
		FileEntity fileEntity = app.POSTfile(file);
		Assert.assertThat("application/octet-stream", Matchers.is(fileEntity.getContentType()));
		Assert.assertThat("file", Matchers.is(fileEntity.getFilename()));
		Assert.assertThat(206087L, Matchers.is(fileEntity.getFilesize()));
	}

	// TODO make this test working
	@Test(expected = RuntimeException.class)
	public void POSTInputstream() {
		UploadApp app = freya.create(UploadApp.class);
		FileEntity fileEntity = app.POSTInputStream(stream);
		Assert.assertThat("application/octet-stream", Matchers.is(fileEntity.getContentType()));
		Assert.assertThat("file", Matchers.is(fileEntity.getFilename()));
		Assert.assertThat(206087L, Matchers.is(fileEntity.getFilesize()));
	}

	// TODO make this test working
	@Test(expected = RuntimeException.class)
	public void POSTByteArray() throws IOException {
		UploadApp app = freya.create(UploadApp.class);
		FileEntity fileEntity = app.POSTByteArray(array);
		Assert.assertThat("application/octet-stream", Matchers.is(fileEntity.getContentType()));
		Assert.assertThat("file", Matchers.is(fileEntity.getFilename()));
		Assert.assertThat(206087L, Matchers.is(fileEntity.getFilesize()));
	}

	@Test
	public void PUTfile() {
		UploadApp app = freya.create(UploadApp.class);
		FileEntity fileEntity = app.PUTfile(file);
		Assert.assertThat("application/octet-stream", Matchers.is(fileEntity.getContentType()));
		Assert.assertThat("file", Matchers.is(fileEntity.getFilename()));
		Assert.assertThat(206087L, Matchers.is(fileEntity.getFilesize()));
	}

	// TODO make this test working
	@Test(expected = RuntimeException.class)
	public void PUTInputstream() {
		UploadApp app = freya.create(UploadApp.class);
		FileEntity fileEntity = app.PUTInputStream(stream);
		Assert.assertThat("application/octet-stream", Matchers.is(fileEntity.getContentType()));
		Assert.assertThat("file", Matchers.is(fileEntity.getFilename()));
		Assert.assertThat(206087L, Matchers.is(fileEntity.getFilesize()));
	}

	// TODO make this test working
	@Test(expected = RuntimeException.class)
	public void PUTByteArray() throws IOException {
		UploadApp app = freya.create(UploadApp.class);
		FileEntity fileEntity = app.PUTByteArray(array);
		Assert.assertThat("application/octet-stream", Matchers.is(fileEntity.getContentType()));
		Assert.assertThat("file", Matchers.is(fileEntity.getFilename()));
		Assert.assertThat(206087L, Matchers.is(fileEntity.getFilesize()));
	}
}
