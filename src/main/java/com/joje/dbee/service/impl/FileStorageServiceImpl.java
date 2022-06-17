package com.joje.dbee.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joje.dbee.common.config.FileStorageProperties;
import com.joje.dbee.exception.FileStorageException;
import com.joje.dbee.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "FileStorageService")
public class FileStorageServiceImpl implements FileStorageService {

//	업로드 디렉토리 절대경로
	private final String fileStorageLocation;

//	업로드 디렉토리 상대경로
	private final String fileStoragePath;

	@Autowired
	public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
		this.fileStoragePath = fileStorageProperties.getUpladDir();
		this.fileStorageLocation = Paths.get(FilenameUtils.getName(fileStoragePath)).toAbsolutePath().normalize().toString();

		try {
			Files.createDirectories(Paths.get(fileStorageLocation));
		} catch (Exception e) {
			throw new FileStorageException("디렉토리 생성 실패 path=" + this.fileStorageLocation, e);
		}
	}

	@Override
	public void write(String contetns, Path path) throws Exception {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()))) {
			bw.write(contetns);
			log.info("File Write [path]=[{}]", path);
		} catch (IOException e) {
			throw new FileStorageException("파일 쓰기 실패 path=" + path, e);
		}
	}

	@Override
	public void write(List<String> contents, Path path) throws Exception {
		String writeData = "";
		for (int i = 0; i < contents.size(); i++) {
			writeData += (contents.size() - 1 == i) ? contents.get(i) : contents.get(i) + "\n";
		}
		this.write(writeData, path);
	}

	@Override
	public File readToFile(Path path) throws Exception {
		File file = path.toFile();

		if (!file.isFile())
			throw new FileStorageException("해당 파일을 찾을 수 없습니다. path=" + path);

		return file;
	}

	@Override
	public boolean isFile(Path path) throws Exception {
		return path.toFile().isFile();
	}

	@Override
	public Path getPath(String dir, String fileName) throws Exception {
		Path path = new File(this.fileStoragePath + dir).toPath();
		this.mkDir(path);
		return path.resolve(fileName);
	}

	private void mkDir(Path path) throws Exception {
		try {
			File file = new File(path.toString());
			if (!file.isDirectory()) {
				file.mkdirs();
				log.info("mkdir : 디렉토리 생성 [path]=[{}]", file.getPath());
			}
		} catch (Exception e) {
			throw new FileStorageException("디렉토리 생성 실패 path=" + path.getFileName(), e);
		}
	}
}
