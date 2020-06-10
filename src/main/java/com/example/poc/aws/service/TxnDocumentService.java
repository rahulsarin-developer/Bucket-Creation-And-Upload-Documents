package com.example.poc.aws.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface TxnDocumentService {

	String createBucket(String bucketName);

	Object uploadFiles(String bucketName, List<MultipartFile> files);
}
