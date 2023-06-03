import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    final static String gameDir = System.getProperty("user.home") + "\\Games\\GunRunner";
    final static String dirStr = "Каталог ";
    final static String fileStr = "Файл ";
    final static String ifCreated = " создан";
    final static String ifExist = " уже существует";
    final static String createdDirError = "Не удалось создать каталог ";
    final static String newLine = "\n";

    public static void main(String[] args) {
        String[] oneLevelDirs = {"src", "res", "savegames", "temp"};
        String[] srcDirs = {"main", "test"};
        String[] resDirs = {"drawables", "vectors", "icons"};
        String[] srcMainFiles = {"Main.java", "Utils.java"};
        String[] logFiles = {"test.txt"};
        StringBuilder log = new StringBuilder();
        log.append(createSubDirs(oneLevelDirs, gameDir));
        log.append(createSubDirs(srcDirs, gameDir + "\\" + oneLevelDirs[0]));
        log.append(createSubDirs(resDirs, gameDir + "\\" + oneLevelDirs[1]));
        log.append(createFiles(srcMainFiles, gameDir + "\\" + oneLevelDirs[0] + "\\" + srcDirs[1]));
        log.append(createFiles(logFiles, gameDir + "\\" + oneLevelDirs[3]));
        try (FileWriter fileWriter = new FileWriter(gameDir + "\\" + oneLevelDirs[3] + "\\" + logFiles[0])) {
            fileWriter.write(log.toString());
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private static String createFiles(String[] strings, String parentDir) {
        StringBuilder result = new StringBuilder();
        File thisParentDir = new File(parentDir);
        for (String item : strings) {
            File file = new File(thisParentDir, item);
            try {
                if (file.createNewFile()) {
                    result.append(fileStr).append(file).append(ifCreated).append(newLine);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }


        return result.toString();
    }

    private static String createSubDirs(String[] directories, String parentDir) {
        StringBuilder result = new StringBuilder();
        if (new File(parentDir).exists()) {
            result.append(dirStr).append(parentDir).append(ifExist).append(newLine);
        } else {
            if (new File(parentDir).mkdirs()) {
                result.append(dirStr).append(parentDir).append(ifCreated).append(newLine);
            } else {
                result.append(createdDirError).append(parentDir).append(newLine);
                return result.toString();
            }
        }
        for (String dir : directories) {
            File path = new File(parentDir, dir);
            if (path.mkdir()) {
                result.append(dirStr).append(path).append(ifCreated).append(newLine);
            } else if (path.exists()) {
                result.append(dirStr).append(path).append(ifExist).append(newLine);
            } else {
                result.append(createdDirError).append(path).append(newLine);
            }
        }
        return result.toString();
    }
}