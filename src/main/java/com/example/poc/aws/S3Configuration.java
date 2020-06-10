package com.example.poc.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Configuration {

	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretkey;

	@Value("${cloud.aws.region.static}")
	private String regions;
	
	/**
	 * Get Basic AWS Credentials
	 * 
	 * @param accesskey
	 * @param secretkey
	 * @return
	 */
	private AWSCredentials getBasicAWSCredentials(String accesskey, String secretkey) {
		return new BasicAWSCredentials(accesskey, secretkey);
	}

	/**
	 * Get AmazonS3
	 * 
	 * @return
	 */
	@Bean
	public AmazonS3 getAmazonS3() {
		return AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(getBasicAWSCredentials(accessKey, secretkey)))
			.withRegion(regions).build();
	}
}