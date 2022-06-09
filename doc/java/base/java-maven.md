+ maven依赖排除

```
<dependency>
            <groupId>com.wisesoft.adjunct</groupId>
            <artifactId>com.wisesoft.adjunct.publicweb</artifactId>
            <version>${wisesoft-common.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>*</groupId>
                    <artifactId>*</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```
