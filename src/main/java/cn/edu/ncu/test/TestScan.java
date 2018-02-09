package cn.edu.ncu.test;

import cn.edu.ncu.utils.ScanDirectory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestScan {
    private ScanDirectory scanDirectory = new ScanDirectory("/home/stcoder/project/java/annotation_2_markdown/src/main/java/cn/edu/ncu");

    @Test
    public void testScan() throws IOException {
        List<String> list = scanDirectory.scan();
        for (String file: list){
            System.out.println(file);
        }
    }
}
