package com.joje.dbee.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {

	public void write(String contetns, Path path);

	public void write(List<String> contents, Path path);

	public File readToFile(Path path);

	public boolean isFile(Path path);

	public Path getPath(String fileName);
}
