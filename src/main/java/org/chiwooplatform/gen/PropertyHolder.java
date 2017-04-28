/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-21
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.gen;

import org.springframework.beans.factory.annotation.Value;

public class PropertyHolder {

    @Value("${codegen.plugin.base-package}")
    private String pluginPackage;

    @Value("${codegen.plugin.mapper-class}")
    private String pluginMapper;

    @Value("${codegen.plugin.map-convertable-class:}")
    private String mapConverClazz;

    @Value("${codegen.plugin.converter-utils-class:}")
    private String converterClazz;

    @Value("${codegen.plugin.dbms-code-class:}")
    private String dbmsCodeClazz;

    @Value("${codegen.plugin.naming-conjection:}")
    private String namingConjection;

    @Value("${project.base-package}")
    private String basePackage;

    @Value("${project.column-names.regDtm}")
    private String regDtm;

    @Value("${project.column-names.regUserId}")
    private String regUserId;

    @Value("${project.column-names.updDtm}")
    private String updDtm;

    @Value("${project.column-names.updUserId}")
    private String updUserId;

    @Value("${project.base-package-dir}")
    private String basePackageDir;

    @Value("${project.base-java-dir}")
    private String baseJavaDir;

    @Value("${project.base-resource-dir}")
    private String baseResourceDir;

    @Value("${project.base-test-java-dir}")
    private String baseTestJavaDir;

    @Value("${project.base-test-resource-dir}")
    private String baseTestResourceDir;

    @Value("${project.target-gen-dir}")
    private String targetGenDir;

    /**
     * @return the pluginPackage
     */
    public String getPluginPackage() {
        return pluginPackage;
    }

    /**
     * @return the pluginMapper
     */
    public String getPluginMapper() {
        return pluginMapper;
    }

    /**
     * @return the mapConvertable
     */
    public String getMapConverClazz() {
        return mapConverClazz;
    }

    /**
     * @return the converterUtils
     */
    public String getConverterClazz() {
        return converterClazz;
    }

    /**
     * @return the dbmsCode
     */
    public String getDbmsCodeClazz() {
        return dbmsCodeClazz;
    }

    /**
     * @return the namingConjection
     */
    public String getNamingConjection() {
        return namingConjection;
    }

    /**
     * @return the basePackage
     */
    public String getBasePackage() {
        return basePackage;
    }

    public String getPackage( String pkg ) {
        return basePackage + "." + pkg;
    }

    /**
     * @return the regDtm
     */
    public String getRegDtm() {
        return regDtm;
    }

    /**
     * @return the regUserId
     */
    public String getRegUserId() {
        return regUserId;
    }

    /**
     * @return the updDtm
     */
    public String getUpdDtm() {
        return updDtm;
    }

    /**
     * @return the updUserId
     */
    public String getUpdUserId() {
        return updUserId;
    }

    /**
     * @return the basePackageDir
     */
    public String getBasePackageDir() {
        return basePackageDir;
    }

    /**
     * @return the baseJavaDir
     */
    public String getBaseJavaDir() {
        return baseJavaDir;
    }

    /**
     * @return the baseResourceDir
     */
    public String getBaseResourceDir() {
        return baseResourceDir;
    }

    /**
     * @return the baseTestJavaDir
     */
    public String getBaseTestJavaDir() {
        return baseTestJavaDir;
    }

    /**
     * @return the baseTestResourceDir
     */
    public String getBaseTestResourceDir() {
        return baseTestResourceDir;
    }

    public String getTargetGenDir() {
        return targetGenDir;
    }

    /**
     * @return the targetGenDir
     */
    public String getTargetGenSrcDir() {
        return targetGenDir + "/src";
    }

    /**
     * @return the targetGenTestDir
     */
    public String getTargetGenTestDir() {
        return targetGenDir + "/test";
    }
}
