package com.crossent.services.departmentservice.controller;

import com.crossent.services.departmentservice.config.FildUploadConfiguration;
import com.crossent.services.departmentservice.dao.ApiDao;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private FildUploadConfiguration fileconf;

	@Autowired
	private ApiDao apiDao;

	@GetMapping("/")
	public String get() {
		LOGGER.info("mybatis find");
		return String.format("%s",  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	@GetMapping(path = "/all")
	public String helloWorld() {
		LOGGER.info("all");
		return String.format("%s %s", apiDao.selectName(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	@PutMapping(path = "/{name}/{test}")
	public String testInsert(@PathVariable("name") String name,@PathVariable("test") String test) {
		LOGGER.info("insert");
		Map<String, String> requstMap = new HashMap<String,String>();
		requstMap.put("name",name);
		requstMap.put("test",test);
		return String.format("%s %s", apiDao.insertName(requstMap), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	@PatchMapping(path = "/{name}/{test}")
	public String testUpdate(@PathVariable("name") String name,@PathVariable("test") String test) {
		LOGGER.info("update");
		Map<String, String> requstMap = new HashMap<String,String>();
		requstMap.put("name",name);
		requstMap.put("test",test);
		return String.format("%s %s", apiDao.updateName(requstMap), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	@DeleteMapping(path = "/{name}")
	public String testDelete(@PathVariable("name") String name) {
		LOGGER.info("delete");
		Map<String, String> requstMap = new HashMap<String,String>();
		requstMap.put("name",name);
		return String.format("%s %s", apiDao.deleteName(requstMap), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile multipartFile) {

		LOGGER.info("### upload :: "+fileconf.getUploadDir());

		File targetFile = new File(fileconf.getUploadDir()  + multipartFile.getOriginalFilename());
		String fileString ="";
		try {

			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);

		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);
			e.printStackTrace();
		}



		try {
			// 바이트 단위로 파일읽기
			String filePath = fileconf.getUploadDir() + multipartFile.getOriginalFilename(); // 대상 파일
			FileInputStream fileStream = null; // 파일 스트림

			fileStream = new FileInputStream( filePath );// 파일 스트림 생성
			//버퍼 선언
			byte[ ] readBuffer = new byte[fileStream.available()];
			while (fileStream.read( readBuffer ) != -1){}

			fileString = new String(readBuffer);

			//System.out.println(new String(readBuffer)); //출력

			fileStream.close(); //스트림 닫기

		} catch (Exception e) {
			e.getStackTrace();
		}


		return fileString;
	}


}