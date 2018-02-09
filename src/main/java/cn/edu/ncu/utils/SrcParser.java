package cn.edu.ncu.utils;

import cn.edu.ncu.exception.FileNotReadableException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.output.AppendableOutputStream;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SrcParser {
    private final String STRAT_TAG = "/**markdown";
    private final String END_TAG = "*/";


    private Parser parser;
    private StringBuilder builder;

    public SrcParser() {
        parser = Parser.builder().build();
        builder = new StringBuilder();
    }

    public String parser(Path inputPath) throws FileNotFoundException, FileNotReadableException {
        File file = inputPath.toFile();
        if (!file.canRead()){
            throw new FileNotReadableException(inputPath);
        }
        try {
            LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");
            while (iterator.hasNext()){
                String line = iterator.nextLine().trim();
                if (line.startsWith(STRAT_TAG)){
                    addContent(iterator, line.substring(line.indexOf(STRAT_TAG)+STRAT_TAG.length()));
                    builder.append("\n***\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(parser.parse(builder.toString()));
    }

    private void addContent(LineIterator iterator, String line) {
        if (line.endsWith(END_TAG)){
            builder.append(line.substring(0, line.indexOf(END_TAG)));
            return;
        }
        while (iterator.hasNext()){
            line = iterator.nextLine().trim();
            if (line.startsWith("*")){
                if (line.length() == 1){
                    builder.append("\n");
                    continue;
                }else if (line.equals(END_TAG)){
                    return;
                }
                else {
                    line = line.substring(1);
                }
            }
            if (line.endsWith(END_TAG)){
                builder.append(line.substring(0, line.lastIndexOf(END_TAG)));
                return;
            }
            builder.append(line);
            builder.append("\n");
        }
    }

    public String parser(String inputPath) throws FileNotFoundException, FileNotReadableException {
        return parser(Paths.get(inputPath));
    }
}