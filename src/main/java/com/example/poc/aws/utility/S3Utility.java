package com.example.poc.aws.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Component
public class S3Utility {
	
	@Autowired
	private AmazonS3 s3Client;

	/**
	 * createBucket
	 * 
	 * @param bucketName
	 */
	public String createBucket(String bucketName) {
		if(!s3Client.doesBucketExistV2(bucketName)) {
			s3Client.createBucket(new CreateBucketRequest(bucketName));
			s3Client.getBucketLocation(new GetBucketLocationRequest(bucketName));
			return "Bucket Created";
		}
		return "Bucker Already Exist";
	}

	private PutObjectResult uploadOnS3(String bucketName, String fileName, InputStream file) {
		return s3Client.putObject(bucketName, fileName, file, null);
	}

	public Object uploadFiles(String bucketName, List<MultipartFile> files) {
		Map<String, Object> bucketDetails = new HashMap<>();
		bucketDetails.put("Bucket Name",bucketName);
		List<String> fileNames = new ArrayList<>();
		if(s3Client.doesBucketExistV2(bucketName)) {
			for(MultipartFile file : files) {
				try {
					uploadOnS3(bucketName, file.getOriginalFilename(), file.getInputStream());
					fileNames.add(file.getOriginalFilename());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			bucketDetails.put("fileDetails", fileNames);
			return bucketDetails;
		}
		return "Bucket Not Exist - Please Create Bucket First";
	}
}