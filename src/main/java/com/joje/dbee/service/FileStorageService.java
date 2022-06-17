package com.joje.dbee.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {

	public void write(String contetns, Path path) throws Exception;

	public void write(List<String> contents, Path path) throws Exception;

	public File readToFile(Path path) throws Exception;

	public boolean isFile(Path path) throws Exception;

	public Path getPath(String dir, String fileName) throws Exception;

}
