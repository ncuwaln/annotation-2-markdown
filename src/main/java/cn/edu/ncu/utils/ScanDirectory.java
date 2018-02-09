package cn.edu.ncu.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class ScanDirectory {

    class MyFileVisitor implements FileVisitor<Path>{

        List<String> fileList = new LinkedList<>();

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.toString().endsWith(".java")){
                fileList.add(file.toString());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        public List<String> getFileList() {
            return fileList;
        }
    }

    private String directory;

    public ScanDirectory() {
    }

    public ScanDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public List<String> scan() throws IOException {
        Path path = Paths.get(directory);
        if (!Files.exists(path) || !Files.isDirectory(path)){
            throw new NotDirectoryException(directory);
        }
        MyFileVisitor fileVisitor = new MyFileVisitor();
        Files.walkFileTree(path, fileVisitor);
        return fileVisitor.getFileList();
    }
}
