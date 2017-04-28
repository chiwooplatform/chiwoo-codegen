/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-21
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.gen;

import org.chiwooplatform.gen.CodeTypes.ConjectionType;
import org.chiwooplatform.gen.CodeTypes.DBMS;
import org.junit.Test;

public class SimpleTest
    extends AbstractGenerator {

    @Test
    public void testName()
        throws Exception {
        ConjectionType conType = ConjectionType.get( "snake-case" );
        System.out.println( "conType: " + conType );
    }

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
