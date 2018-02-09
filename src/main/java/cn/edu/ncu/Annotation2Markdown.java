package cn.edu.ncu;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import cn.edu.ncu.exception.FileNotReadableException;
import cn.edu.ncu.utils.ScanDirectory;
import cn.edu.ncu.utils.SrcParser;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Mojo(name = "annotation2markdown")
public class Annotation2Markdown
    extends AbstractMojo
{

//    源文件地址
    @Parameter(property = "annotation2markdown.srcDirectory", required = true)
    private String srcDirectory;
//    输出地址
    @Parameter(property = "annotation2markdown.outputDirectory", required = true)
    private String outputDirectory;

    @Parameter(property = "annotation2markdown.isClean", defaultValue = "true")
    private boolean isClean;

    public void execute()
        throws MojoExecutionException
    {
        run(srcDirectory, outputDirectory);
    }

    public void run(String srcDirectory, String outputDirectory) throws MojoExecutionException {
        SrcParser srcParser = new SrcParser();
        ScanDirectory scanDirectory = new ScanDirectory(srcDirectory);
        if (isClean){
            try {
                FileUtils.deleteDirectory(Paths.get(outputDirectory).toFile());
            } catch (IOException e) {
                e.printStackTrace();
                throw new MojoExecutionException("clean directory failed");
            }
        }
        if (!Files.exists(Paths.get(outputDirectory))){
            System.out.println("create outputDirectory");
            try {
                Files.createDirectory(Paths.get(outputDirectory));
            } catch (IOException e) {
                e.printStackTrace();
                throw new MojoExecutionException("create directory failed");
            }
        }
        try {
            List<String> fileList = scanDirectory.scan();
            for (String file: fileList){
                String content = srcParser.parser(file);
                Path outputPath = getOutputPath(file);
                FileUtils.writeStringToFile(outputPath.toFile(), content, Charset.defaultCharset(), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MojoExecutionException("srcDirectory must exist");
        } catch (FileNotReadableException e) {
            e.printStackTrace();
        }
    }

    private Path getOutputPath(String file) {
        Path filePath = Paths.get(file);
        String outputFileName = filePath.getFileName().toString()+".html";
        return Paths.get(outputDirectory, outputFileName);
    }
}
