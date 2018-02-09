## annotation-2-markdown
一个支持在Java注释中使用markdown语法的maven插件

### install
```
mvn clean install
```


### test
test-markdown-plugin就是测试用maven项目


```
cd test-markdown-plugin
mvn clean compile
```

### usage


引入插件


```
<build>
        <plugins>
            <plugin>
                <groupId>cn.edu.ncu</groupId>
                <artifactId>annotation-2-markdown</artifactId>
                <version>0.0.1</version>
                <configuration>
                    <srcDirectory>your/path</srcDirectory>
                    <outputDirectory>your/output/path</outputDirectory>
                </configuration>
                <executions>
                    <execution>
                        <phase>compile</phase>
                        <goals>
                            <goal>annotation2markdown</goal>
                        </goals>
                    </execution>

                </executions>
            </plugin>
        </plugins>
    </build>
```


将srcDirectory与outputDirectory修改成你自己的目录



```
/**markdown
 * ## HelloWorld.java
 * * this is a test class
 */


public class HelloWorld {

    /**markdown
     * ## method hello
     *
     *
     * print hello
     *
     *
     * src:
     * ```
     * System.out.println("hello");
     * ```*/
    public void hello(){
        System.out.println("hello");
    }
}
```


生成文档


```
mvn clean compile
```


你就会发现文档在你指定的目录生成了


用法详细实例在test-markdown-plugin目录下