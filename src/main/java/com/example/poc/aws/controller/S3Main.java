package com.example.poc.aws.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class S3Main {
	
	private AmazonS3 amazonS3;
	private Properties properties;

	S3Main(String accesskey, String secretkey) {
		amazonS3 = AmazonS3ClientBuilder.standard()
		.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accesskey, secretkey)))
		.withRegion(Regions.AP_SOUTH_1).build();
	}
	
	S3Main() {
		properties = new Properties();
		try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
			properties = new Properties();
			properties.load(input);
		} catch (IOException ex) {}
		
		amazonS3 = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(
			new BasicAWSCredentials(properties.getProperty("cloud.aws.credentials.accessKey"),
			properties.getProperty("cloud.aws.credentials.secretKey"))))
			.withRegion(Regions.AP_SOUTH_1).build();
	}

	public void upload() {
		File folder = new File(properties.getProperty("local.file.path"));
		for (File file : folder.listFiles()) {
			amazonS3.putObject(properties.getProperty("amazon.aws.bucket.name"), file.getName(), file);
			System.out.println(file.getName() + " -- Uploaded");
		}
	}

	public static void main(String[] args) {
		S3Main s3Main = new S3Main();
		s3Main.upload();
	}
}