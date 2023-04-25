package springbox.exceptionhandle.recover;

import springbox.exceptionhandle.exception.NotAcceptFileExtensionException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ImageFileReader {
    public static final List<String> permissions = List.of("jpg", "png");

    public static void main(String[] args) {
        File file = new File("./src/main/resources/static");
        List<String> files = Arrays.asList(file.list());

        for (String fileName : files) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if(validateExtension(extension)) {
                 throw new NotAcceptFileExtensionException("해당 확장자는 허용하지 않습니다. 입력한 확장자 = " + extension);
            }
        }
    }

    private static boolean validateExtension(String extension) {
        return !permissions.contains(extension);
    }
}
