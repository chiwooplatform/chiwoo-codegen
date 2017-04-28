<#assign packageName=isNotEmpty( pkgName() )?then( "${context.value('basePackage')}.dam.mapper.${pkgName()}", 
    "${context.value('basePackage')}.dam.mapper" ) />
<#assign modelClazz=isNotEmpty( pkgName() )?then( "${context.value('basePackage')}.model.${pkgName()}.${domainName}", 
    "${context.value('basePackage')}.model.${domainName}" ) />
<#assign interfaceClazz=isNotEmpty( mapConverClazz )?then( 
    "MapConvertable", 
    "Serializable" ) />
package ${packageName};

// import ${pluginPackage}.mybatis.mapper.BaseMapper;
import ${pluginPackage}.mybatis.mapper.ExtMapper;
import ${modelClazz};

public interface ${domainName}Mapper
    extends ExtMapper<${domainName}> {
}