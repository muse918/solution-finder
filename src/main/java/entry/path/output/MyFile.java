package entry.path.output;

import exceptions.FinderInitializeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

class MyFile {
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final StandardOpenOption[] FILE_OPEN_OPTIONS = new StandardOpenOption[]{
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.CREATE
    };

    static void mkdirs(String baseFilePath) throws FinderInitializeException {
        File outputFile = new File(baseFilePath);

        // 親ディレクトリがない場合は作成
        if (!outputFile.getParentFile().exists()) {
            boolean mkdirsSuccess = outputFile.getParentFile().mkdirs();
            if (!mkdirsSuccess) {
                throw new FinderInitializeException("Failed to make output directory: OutputBase=" + baseFilePath);
            }
        }
    }

    private final String path;
    private final File file;

    MyFile(String path) {
        this.path = path;
        this.file = new File(path);
    }

    void verify() throws FinderInitializeException {
        if (file.isDirectory())
            throw new FinderInitializeException("Cannot specify directory as output file path: Path=" + path);

        if (file.exists() && !file.canWrite())
            throw new FinderInitializeException("Cannot write output file: Path=" + path);
    }

    BufferedWriter newBufferedWriter() throws IOException {
        return Files.newBufferedWriter(file.toPath(), CHARSET, FILE_OPEN_OPTIONS);
    }
}