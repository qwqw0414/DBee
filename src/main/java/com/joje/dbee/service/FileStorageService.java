package com.joje.dbee.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {

	void write(String contetns, Path path);

	void write(List<String> contents, Path path);

	File readToFile(Path path);

	boolean isFile(Path path);

	Path getPath(String fileName);
}
