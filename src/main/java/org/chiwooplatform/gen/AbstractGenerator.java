package org.chiwooplatform.gen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import org.chiwooplatform.gen.CodeTypes.DBMS;
import org.chiwooplatform.gen.config.ValueHolder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ActiveProfiles(profiles = { "gen" })
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodegenApplication.class)
public abstract class AbstractGenerator {

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    abstract protected DBMS dbms();

    abstract protected String permissionGroup();

    abstract protected String tableName();

    abstract protected String pkgName();

    abstract protected String modelName();

    abstract protected String serviceName();

    protected boolean generateRealPath() {
        return false;
    }

    @Autowired
    protected Generator generator;

    @Autowired
    protected ValueHolder valueHolder;

    @Before
    public void setUp() {
        GenContextHolder.get().add( ValueHolder.DBMS, dbms() );
        GenContextHolder.get().add( ValueHolder.TABLE_NAME, tableName() );
        GenContextHolder.get().add( ValueHolder.PKG_NAME, pkgName() );
        GenContextHolder.get().add( ValueHolder.MODEL_NAME, modelName() );
        GenContextHolder.get().add( ValueHolder.SERVICE_NAME, serviceName() );
        GenContextHolder.get().add( ValueHolder.PERMISSION_GROUP, permissionGroup() );
        GenContextHolder.get().add( ValueHolder.GENERATE_REALPATH, generateRealPath() );
    }
}
