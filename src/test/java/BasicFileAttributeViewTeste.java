import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class BasicFileAttributeViewTeste {

    String HOME = System.getProperty("user.home");
    Path home = Paths.get(HOME);
    BasicFileAttributeView basicView =
            Files.getFileAttributeView(home, BasicFileAttributeView.class);

    BasicFileAttributes basicAttribs = basicView.readAttributes();

    //Para obter a hora em que o arquivo foi criado:
    FileTime created = basicAttribs.creationTime();

    //Para obter o horário da última modificação:
    FileTime modified = basicAttribs.lastModifiedTime();

    //E para obter o último horário de acesso:
    FileTime accessed = basicAttribs.lastAccessTime();

    public BasicFileAttributeViewTeste() throws IOException {
    }

    @Test
    public void givenPath_whenGetsFileSize_thenCorrect() {
        long size = basicAttribs.size();
        assertTrue(size > 0);
    }

    @Test
    public void givenPath_whenChecksIfDirectory_thenCorrect() {
        boolean isDir = basicAttribs.isDirectory();
        assertTrue(isDir);
    }

    @Test
    public void givenPath_whenChecksIfFile_thenCorrect() {
        boolean isFile = basicAttribs.isRegularFile();
        assertFalse(isFile);
    }

    @Test
    public void givenPath_whenChecksIfSymLink_thenCorrect() {
        boolean isSymLink = basicAttribs.isSymbolicLink();
        assertFalse(isSymLink);
    }

    @Test
    public void givenPath_whenChecksIfOther_thenCorrect() {
        boolean isOther = basicAttribs.isOther();
        assertFalse(isOther);
    }



    @Test
    public void givenFileTimes_whenComparesThem_ThenCorrect() {
        FileTime created = basicAttribs.creationTime();
        FileTime modified = basicAttribs.lastModifiedTime();
        FileTime accessed = basicAttribs.lastAccessTime();

        assertTrue(0 >= created.compareTo(accessed));
        assertTrue(0 <= modified.compareTo(created));
        assertEquals(0, created.compareTo(created));

        accessed.to(TimeUnit.SECONDS);
        accessed.to(TimeUnit.HOURS);
        accessed.toMillis();

        accessed.toString();
    }


}
