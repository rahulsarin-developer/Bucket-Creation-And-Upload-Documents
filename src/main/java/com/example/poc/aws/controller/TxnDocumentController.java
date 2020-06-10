package com.example.poc.aws.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.poc.aws.service.TxnDocumentService;

@RestController
@RequestMapping("/api/v1/aws")
public class TxnDocumentController {

	@Autowired
	private TxnDocumentService txnDocumentService;
	
	@PostMapping(value = "/createBucket")
	public Map<String, Object> createBucket(@RequestParam("bucketName") String bucketName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Status", txnDocumentService.createBucket(bucketName));
		return map;
	}
	
	@PostMapping(value = "/uploadFiles")
	public Map<String, Object> uploadFiles(@RequestParam("bucketName") String bucketName, List<MultipartFile> files) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Status", txnDocumentService.uploadFiles(bucketName, files));
		return map;
	}
}