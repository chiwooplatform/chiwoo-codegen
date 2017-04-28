package org.chiwooplatform.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.chiwooplatform.gen.builder.AbstractBuilder;
import org.chiwooplatform.gen.builder.ControllerBuilderTemplateCallback;
import org.chiwooplatform.gen.builder.ControllerTestBuilderTemplateCallback;
import org.chiwooplatform.gen.builder.CopyPasteClazzTemplateCallback;
import org.chiwooplatform.gen.builder.ModelBuilderTemplateCallback;
import org.chiwooplatform.gen.builder.mybatis.MariaSqlMapBuilderTemplateCallback;
import org.chiwooplatform.gen.builder.mybatis.MybatisMapperBuilderTemplateCallback;
import org.chiwooplatform.gen.builder.mybatis.MybatisTestMapperBuilderTemplateCallback;
import org.chiwooplatform.gen.builder.mybatis.OracleSqlMapBuilderTemplateCallback;
import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.dam.mapper.GeneratorMapper;
import org.chiwooplatform.gen.model.TableColumnMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    private final Charset UTF8 = Charset.forName( "UTF-8" );

    @Autowired
    private PropertyHolder props;

    @Autowired
    private ValueHolder holder;

    private boolean generateRealPath = false;

    @Autowired
    protected GeneratorMapper mapper;

    protected List<TableColumnMeta> columnMetas;

    private List<String> generatedSources = new ArrayList<>();

    public List<String> getGeneratedSources() {
        return generatedSources;
    }

    protected String baseResourceDir() {
        return props.getBaseResourceDir();
    }

    protected String targetDir() {
        return props.getTargetGenSrcDir();
    }

    private void doPreprocess() {
        GenContextHolder.get().validate();
        this.generateRealPath = (Boolean) holder.value( ValueHolder.GENERATE_REALPATH );
        if ( holder.columnsMeta() == null ) {
            Map<String, Object> param = new HashMap<>();
            param.put( "table_name", GenContextHolder.get().value( ValueHolder.TABLE_NAME ) );
            logger.debug( "param: {}", param );
            List<TableColumnMeta> columnMetas = mapper.getColumnsMeta( param );
            GenContextHolder.get().add( ValueHolder.COLUMNS_META, columnMetas );
        }
        logger.info( "valueHolder.getContext(): {}", holder.getContext() );
    }

    protected void generateModel()
        throws Exception {
        doPreprocess();
        ModelBuilderTemplateCallback callback = new ModelBuilderTemplateCallback( holder );
        final String data = callback.build();
        String filepath = holder.targetPackageDir()
            + String.format( "/model/%s/%s.java", holder.pkgName(), holder.getDomainName() );
        if ( generateRealPath ) {
            filepath = holder.sourcePackageDir()
                + String.format( "/model/%s/%s.java", holder.pkgName(), holder.getDomainName() );
        }
        File file = new File( filepath );
        if ( !file.getParentFile().exists() ) {
            FileUtils.forceMkdir( file.getParentFile() );
        }
        FileUtils.writeStringToFile( file, data, UTF8, false );
        logger.info( "Created: {}", file.getAbsolutePath() );
        generatedSources.add( String.format( "\n\nModel: %s", data ) );
    }

    protected void generateMapper()
        throws Exception {
        MybatisMapperBuilderTemplateCallback callback = new MybatisMapperBuilderTemplateCallback( holder );
        String data = callback.build();
        String filepath = holder.targetPackageDir()
            + String.format( "/dam/mapper/%s/%sMapper.java", holder.pkgName(), holder.getDomainName() );
        if ( generateRealPath ) {
            filepath = holder.sourcePackageDir()
                + String.format( "/dam/mapper/%s/%sMapper.java", holder.pkgName(), holder.getDomainName() );
        }
        File file = new File( filepath );
        if ( !file.getParentFile().exists() ) {
            FileUtils.forceMkdir( file.getParentFile() );
        }
        FileUtils.writeStringToFile( file, data, UTF8, false );
        logger.info( "Created: {}", file.getAbsolutePath() );
        generatedSources.add( String.format( "\n\nMapper: %s", data ) );
    }

    public void generateSqlMapper()
        throws Exception {
        doPreprocess();
        String dir = "oracle";
        AbstractBuilder builder = null;
        switch ( holder.dbms() ) {
            case ORACLE:
                builder = new OracleSqlMapBuilderTemplateCallback( holder );
                break;
            case MARIADB:
                builder = new MariaSqlMapBuilderTemplateCallback( holder );
                dir = "mariadb";
                break;
            default:
                builder = new OracleSqlMapBuilderTemplateCallback( holder );
        }
        String data = builder.build();
        String filepath = targetDir() + String.format( "/sql/%s/%sMapper.xml", dir, holder.getDomainName() );
        if ( generateRealPath ) {
            filepath = baseResourceDir() + String.format( "/sql/%s/%sMapper.xml", dir, holder.getDomainName() );
        }
        File file = new File( filepath );
        if ( !file.getParentFile().exists() ) {
            FileUtils.forceMkdir( file.getParentFile() );
        }
        FileUtils.writeStringToFile( file, data, UTF8, false );
        logger.info( "Created: {}", file.getAbsolutePath() );
        generatedSources.add( String.format( "\n\nSqlMap: %s", data ) );
    }

    protected void generateController()
        throws Exception {
        doPreprocess();
        ControllerBuilderTemplateCallback callback = new ControllerBuilderTemplateCallback( holder );
        String data = callback.build();
        String filepath = holder.targetPackageDir()
            + String.format( "/rest/%s/%sController.java", holder.pkgName(), holder.getDomainName() );
        if ( generateRealPath ) {
            filepath = holder.sourcePackageDir()
                + String.format( "/rest/%s/%sController.java", holder.pkgName(), holder.getDomainName() );
        }
        File file = new File( filepath );
        if ( !file.getParentFile().exists() ) {
            FileUtils.forceMkdir( file.getParentFile() );
        }
        FileUtils.writeStringToFile( file, data, UTF8, false );
        logger.info( "Created: {}", file.getAbsolutePath() );
        generatedSources.add( String.format( "\n\nController: %s", data ) );
    }

    protected void generateCopyPasteClazz()
        throws Exception {
        doPreprocess();
        CopyPasteClazzTemplateCallback callback = new CopyPasteClazzTemplateCallback( holder );
        String data = callback.build();
        String filepath = holder.targetDir() + "/CopyPasteClazzTemplate.java";
        File file = new File( filepath );
        if ( !file.getParentFile().exists() ) {
            FileUtils.forceMkdir( file.getParentFile() );
        }
        FileUtils.writeStringToFile( file, data, UTF8, false );
        logger.info( "Created: {}", file.getAbsolutePath() );
        generatedSources.add( String.format( "\n\nCopyPasteClazz: %s", data ) );
    }

    protected void generateTestMapper()
        throws Exception {
        doPreprocess();
        String filepath = holder.targetTestDir()
            + String.format( "/dam/mapper/%s/%sMapperTest.java", holder.pkgName(), holder.getDomainName() );
        if ( generateRealPath ) {
            filepath = holder.testJavaPackageDir()
                + String.format( "/dam/mapper/%s/%sMapperTest.java", holder.pkgName(), holder.getDomainName() );
        }
        if ( new File( filepath ).exists() ) {
            FileUtils.forceDelete( new File( filepath ) );
        }
        MybatisTestMapperBuilderTemplateCallback callback = new MybatisTestMapperBuilderTemplateCallback( holder );
        String data = callback.build();
        File file = new File( filepath );
        if ( !file.getParentFile().exists() ) {
            FileUtils.forceMkdir( file.getParentFile() );
        }
        FileUtils.writeStringToFile( file, data, UTF8, false );
        logger.info( "Created: {}", file.getAbsolutePath() );
        generatedSources.add( String.format( "\n\nMapperTest: %s", data ) );
    }

    protected void generateTestController()
        throws Exception {
        doPreprocess();
        String filepath = holder.targetTestDir()
            + String.format( "/rest/%s/%sControllerTest.java", holder.pkgName(), holder.getDomainName() );
        if ( generateRealPath ) {
            filepath = holder.testJavaPackageDir()
                + String.format( "/rest/%s/%sControllerTest.java", holder.pkgName(), holder.getDomainName() );
        }
        if ( new File( filepath ).exists() ) {
            FileUtils.forceDelete( new File( filepath ) );
        }
        ControllerTestBuilderTemplateCallback callback = new ControllerTestBuilderTemplateCallback( holder );
        String data = callback.build();
        File file = new File( filepath );
        if ( !file.getParentFile().exists() ) {
            FileUtils.forceMkdir( file.getParentFile() );
        }
        FileUtils.writeStringToFile( file, data, UTF8, false );
        logger.info( "Created: {}", file.getAbsolutePath() );
        generatedSources.add( String.format( "\n\nControllerTest: %s", data ) );
    }

    /**
     * 생성된 결과물(소스)을 실제 디렉토리 경로에 생성 할 것인지를 결정
     * @throws Exception if error occurs
     */
    public void generateAll()
        throws Exception {
        doPreprocess();
        generateModel();
        generateMapper();
        generateSqlMapper();
        generateController();
        generateTestMapper();
        generateTestController();
        generateCopyPasteClazz();
    }

    /**
     * DataAccessManager 컴포넌트들만 생성
     * @throws Exception if error occurs
     */
    public void generateDAM()
        throws Exception {
        doPreprocess();
        generateModel();
        generateMapper();
        generateSqlMapper();
        generateTestMapper();
    }
}
