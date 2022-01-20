package com.luis.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) throws IOException {
		String filename = multipartFile.getOriginalFilename();
		InputStream is = multipartFile.getInputStream();
		String contentType = multipartFile.getContentType();
		try {
			return uploadFile(filename, is, contentType);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro de IO");
		}
		

	}
	
	public URI uploadFile(String filename, InputStream is, String contentType) throws URISyntaxException {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("Iniciando upload");
			s3client.putObject(bucketName, filename, is, meta);
			LOG.info("Upload finalizado");
			return s3client.getUrl(bucketName, filename).toURI();
	}

}
