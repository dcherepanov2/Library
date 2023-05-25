package com.example.MyBookShopApp.dto;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

public class ExtensionFileTypeDetector extends FileTypeDetector {

    private static final String PDF_EXTENSION = "pdf";
    private static final String EPUB_EXTENSION = "epub";
    private static final String FB2_EXTENSION = "fb2";
    @Override
    public String probeContentType(Path path) throws IOException {
        String fileName = path.getFileName().toString();
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex < 0 || extensionIndex == fileName.length() - 1) {
            return null;
        }
        String extension = fileName.substring(extensionIndex + 1).toLowerCase();
        switch (extension) {
            case PDF_EXTENSION:
                return "0";
            case EPUB_EXTENSION:
                return "1";
            case FB2_EXTENSION:
                return "2";
            default:
                return null;
        }
    }
}
