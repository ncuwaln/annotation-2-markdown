package cn.edu.ncu.exception;


import java.nio.file.Path;

public class FileNotReadableException extends Throwable{
    private Path path;

    public FileNotReadableException(Path path) {
        path = path;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not readable", path.getFileName().toString());
    }
}
