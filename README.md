[[overview]]

The chiwoo-codegen automatically generates the following major classes of springframework by referring to the DBMS table schema information:

- Message: Client request and server response object
- Controller: The front-controller class delegated through servlet-dispatcher.
- Service: A class of service that reflects data and is responsible for requesting transactions.
- Repository: A data handler class that accesses a repository, such as a DBMS.
- Model: The Repository class refers to the data model object.
- Unit-test: A unit test class such as Mybatis Mapper, Spring Controller.

Note that, It currently handles to schema information through MariaDB and MySQL.


### Environment Properties


"chiwoo-codegen" needs to create two configuration files, "application-gen.yml" and "code-gen.properties".

"application-gen.yml" file is used by "chiwoo-mybatis" for getting schema information from dbms.
~~~~
spring:
  application:
    name: code-gen
  datasource:
    url: jdbc:mariadb://192.168.56.11:3306/CHIWOOSAMPLES
    username: aider
    password: aider1234
    driver-class-name: org.mariadb.jdbc.Driver
  freemarker:
    charset: UTF-8

mybatis:
    type-aliases-package: org.chiwooplatform.mybatis.model,org.chiwooplatform.gen.model
    type-handlers-package: org.chiwooplatform.mybatis.handler
    mapper-locations: classpath*:codegen/sql/mariadb/*.xml

    configuration:
        map-underscore-to-camel-case: false
        default-fetch-size: 100
        default-statement-timeout: 30
        auto-mapping-behavior: partial
        auto-mapping-unknown-column-behavior: warning
~~~~

The "code-gen.properties" file is used as the base information to generate the code automatically within the project.

~~~~
codegen.plugin.base-package=org.chiwooplatform
codegen.plugin.mapper-class=org.chiwooplatform.mybatis.mapper.BaseMapper
codegen.plugin.map-convertable-class=org.chiwooplatform.context.model.MapConvertable
codegen.plugin.converter-utils-class=org.chiwooplatform.context.supports.ConverterUtils
codegen.plugin.dbms-code-class=org.chiwooplatform.context.annotation.DbmsCode
# naming-conjection=not-support|camel-case|snake-case|spinal-case
codegen.plugin.naming-conjection=camel-case

project.base-package=org.chiwooplatform.simple
project.column-names.regDtm=reg_dtm
project.column-names.regUserId=register_id
project.column-names.updDtm=upd_dtm
project.column-names.updUserId=modifier_id

project.base-package-dir=/org/chiwooplatform/simple
project.base-java-dir=./src/main/java
project.base-resource-dir=./src/main/resources
project.base-test-java-dir=./src/test/java
project.base-test-resource-dir=./src/test/resources

project.target-gen-dir=./target/generated-sources

~~~~

### Generate Classes

Code generation uses JUnit tests. You can create classes automatically just by creating the following test class in your project.

 
~~~~
package tool;

import org.chiwooplatform.gen.AbstractGenerator;
import org.chiwooplatform.gen.CodeTypes.DBMS;
import org.junit.Test;

public class SimpleCodeGenerator
    extends AbstractGenerator {

    @Test
    public void generateAll()
        throws Exception {
        generator.generateAll();
    }

    @Test
    public void generateDAM()
        throws Exception {
        generator.generateDAM();
    }

    protected DBMS dbms() {
        return DBMS.MARIADB;
    }

    protected String permissionGroup() {
        return "API";
    }

    protected String tableName() {
        return "COM_CODE";
    }

    protected String pkgName() {
        return "com";
    }

    protected String modelName() {
        return "Code";
    }

    protected String serviceName() {
        return "CommonService";
    }
}

~~~~
