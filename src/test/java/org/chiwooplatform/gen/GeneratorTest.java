package org.chiwooplatform.gen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import org.chiwooplatform.gen.CodeTypes.DBMS;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
public class GeneratorTest
    extends AbstractGenerator {

    @Autowired
    private PropertyHolder propertyHolder;

    @Test
    public void testName()
        throws Exception {
        logger.info( "valueHolder.basePackageDir(): {}", valueHolder.basePackageDir() );
        logger.info( "valueHolder.getContext(): {}", valueHolder.getContext() );
        logger.info( "valueHolder.getContext(): {}", valueHolder.getContext().validate() );
        logger.info( "generator: {}", generator );
        logger.info( "propertyHolder: {}", propertyHolder );
    }

    @Test
    public void generateModel()
        throws Exception {
        generator.generateModel();
    }

    @Test
    public void generateMapper()
        throws Exception {
        generator.generateMapper();
    }

    @Test
    public void generateSqlMapper()
        throws Exception {
        generator.generateSqlMapper();
    }

    @Test
    public void generateController()
        throws Exception {
        generator.generateController();
    }

    @Test
    public void generateCopyPasteClazz()
        throws Exception {
        generator.generateCopyPasteClazz();
    }

    @Test
    public void generateTestMapper()
        throws Exception {
        generator.generateTestMapper();
    }
    
    @Test
    public void generateTestController()
        throws Exception {
        generator.generateTestController();
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
