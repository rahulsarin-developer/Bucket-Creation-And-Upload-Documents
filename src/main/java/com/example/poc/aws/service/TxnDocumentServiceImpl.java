package com.example.poc.aws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.poc.aws.utility.S3Utility;

@Service
public class TxnDocumentServiceImpl implements TxnDocumentService {

	@Autowired
	private S3Utility s3Utility;
	
	@Override
	public String createBucket(String bucketName) {
		return s3Utility.createBucket(bucketName.toLowerCase());
	}

	@Override
	public Object uploadFiles(String bucketName, List<MultipartFile> files) {
		return s3Utility.uploadFiles(bucketName.toLowerCase(), files);
	}
}