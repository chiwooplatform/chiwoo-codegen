package org.chiwooplatform.gen.config;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import org.springframework.beans.factory.annotation.Autowired;

import org.chiwooplatform.gen.CodeTypes.DBMS;
import org.chiwooplatform.gen.GenContextHolder;
import org.chiwooplatform.gen.PropertyHolder;
import org.chiwooplatform.gen.model.TableColumnMeta;

public class ValueHolder {

    @Autowired
    private PropertyHolder props;

    @Autowired
    private freemarker.template.Configuration freemarkerConfigure;

    public static final String FREEMARKER_CONFIGURE = "freemarkerConfigure";

    public static final String PERMISSION_GROUP = "permissionGroup";

    public static final String BASE_PACKAGE = "basePackage";

    public static final String BASE_PACKAGE_DIR = "basePackageDir";

    public static final String BASE_JAVA_DIR = "baseJavaDir";

    public static final String BASE_RESOURCE_DIR = "baseResourceDir";

    public static final String BASE_TEST_JAVADIR = "baseTestJavaDir";

    public static final String TARGET_DIR = "targetDir";

    public static final String TARGET_TEST_DIR = "targetTestDir";

    public static final String COLUMNS_META = "columnsMeta";

    public static final String DBMS = "dbms";

    public static final String PKG_NAME = "pkgName";

    public static final String TABLE_NAME = "tableName";

    public static final String MODEL_NAME = "modelName";

    public static final String SERVICE_NAME = "serviceName";

    public static final String GENERATE_REALPATH = "generateRealPath";
    //    public final Inflector inflector = new Inflector();
    //
    //    /**
    //     * @return the inflector
    //     */
    //    public Inflector getInflector() {
    //        return inflector;
    //    }

    public boolean isNotEmpty( Object value ) {
        if ( value == null || value.toString().trim().length() < 1 ) {
            return false;
        }
        return true;
    }

    private String clazzName( String name ) {
        String result = null;
        if ( name != null ) {
            result = WordUtils.capitalizeFully( name, new char[] { '_', '-', ' ' } );
            result = result.replaceAll( "_|-| ", "" );
        }
        return result;
    }

    private String methodName( String name ) {
        if ( name == null ) {
            return null;
        }
        String result = clazzName( name );
        return result.substring( 0, 1 ).toLowerCase() + result.substring( 1 );
    }

    private String abbr( String name ) {
        if ( name == null ) {
            return "";
        }
        return name.substring( 0, 1 ).toLowerCase() + name.substring( 1 );
    }

    public freemarker.template.Configuration freemarkerConfigure() {
        return freemarkerConfigure;
    }

    public GenContextHolder getContext() {
        GenContextHolder context = GenContextHolder.get();
        context.add( BASE_PACKAGE, getBasePackage() );
        context.add( FREEMARKER_CONFIGURE, freemarkerConfigure() );
        context.add( BASE_PACKAGE_DIR, basePackageDir() );
        context.add( BASE_JAVA_DIR, baseJavaDir() );
        context.add( BASE_RESOURCE_DIR, baseResourceDir() );
        context.add( BASE_TEST_JAVADIR, baseTestJavaDir() );
        context.add( TARGET_DIR, targetSrcDir() );
        context.add( TARGET_TEST_DIR, targetTestDir() );
        return context;
    }

    public Object value( String key ) {
        return GenContextHolder.get().value( key );
    }

    public boolean generateRealpath() {
        String value = (String) GenContextHolder.get().value( GENERATE_REALPATH );
        if ( "true".equals( value ) || "1".equals( value ) ) {
            return true;
        }
        return false;
    }

    public String getPluginPackage() {
        return props.getPluginPackage();
    }

    @SuppressWarnings("unchecked")
    public List<TableColumnMeta> columnsMeta() {
        return (List<TableColumnMeta>) GenContextHolder.get().value( COLUMNS_META );
    }

    public DBMS dbms() {
        return (DBMS) GenContextHolder.get().value( DBMS );
    }

    public String getPermCode() {
        String group = (String) GenContextHolder.get().value( PERMISSION_GROUP );
        String pkg = pkgName();
        String code = null;
        if ( pkg != null && pkg.length() > 1 ) {
            code = clazzName( pkg ) + getDomainName();
        } else {
            code = getDomainName();
        }
        return String.format( "%s_%s", group, code );
    }

    public String getDomainName() {
        return clazzName( (String) GenContextHolder.get().value( MODEL_NAME ) );
    }

    public String getDomainNm() {
        return abbr( getDomainName() );
    }

    public String getAbbr() {
        return abbrName();
    }

    public String abbrName() {
        return methodName( (String) GenContextHolder.get().value( MODEL_NAME ) );
    }

    public String pkgName() {
        String pkgName = (String) GenContextHolder.get().value( PKG_NAME );
        if ( pkgName == null ) {
            return null;
        }
        return pkgName.toLowerCase();
    }

    public String getPkgNm() {
        return pkgName();
    }

    public String tableName() {
        return (String) GenContextHolder.get().value( TABLE_NAME );
    }

    public String modelName() {
        return (String) GenContextHolder.get().value( MODEL_NAME );
    }

    /**
     * @return string value of serviceName 
     */
    public String getServiceName() {
        return (String) GenContextHolder.get().value( SERVICE_NAME );
    }

    public String getServiceNm() {
        return abbr( getServiceName() );
    }

    public String getBasePackage() {
        return props.getBasePackage();
    }

    public String basePackageDir() {
        return props.getBasePackageDir();
    }

    protected String baseJavaDir() {
        return props.getBaseJavaDir();
    }

    protected String baseResourceDir() {
        return props.getBaseResourceDir();
    }

    public String baseTestJavaDir() {
        return props.getBaseTestJavaDir();
    }

    public String targetDir() {
        return props.getTargetGenDir();
    }

    public String targetSrcDir() {
        return props.getTargetGenSrcDir();
    }

    public String targetTestDir() {
        return props.getTargetGenTestDir();
    }

    public String targetPackageDir() {
        return targetSrcDir() + basePackageDir();
    }

    public String sourcePackageDir() {
        return baseJavaDir() + basePackageDir();
    }

    public String testJavaPackageDir() {
        return baseTestJavaDir() + basePackageDir();
    }

    public String getMapConverClazz() {
        return props.getMapConverClazz();
    }

    public String getConverterClazz() {
        return props.getConverterClazz();
    }

    public String getDbmsCodeClazz() {
        return props.getDbmsCodeClazz();
    }

    public String columnRegDtm() {
        return props.getRegDtm();
    }

    public String getColumnCreator() {
        return props.getRegUserId();
    }

    public String getAttrCreator() {
        return clazzName( getColumnCreator() );
    }

    public String getAttrCreatorNm() {
        return abbr( getAttrCreator() );
    }

    public String columnUpdDtm() {
        return props.getUpdDtm();
    }

    public String getColumnModifier() {
        return props.getUpdUserId();
    }

    public String getAttrModifier() {
        return clazzName( getColumnModifier() );
    }

    public String getAttrModifierNm() {
        return abbr( getAttrModifier() );
    }
}