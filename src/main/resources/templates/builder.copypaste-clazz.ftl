<#assign packageName=isNotEmpty( pkgName() )?then( "${context.value('basePackage')}.rest.${pkgNm}", 
    "${context.value('basePackage')}.rest" ) />
<#assign modelClazz=isNotEmpty( pkgName() )?then( "${context.value('basePackage')}.model.${pkgNm}.${domainName}", 
    "${context.value('basePackage')}.model.${domainName}" ) />
<#assign mapperClazz=isNotEmpty( pkgName() )?then( "${context.value('basePackage')}.dam.mapper.${pkgNm}.${domainName}Mapper", 
    "${context.value('basePackage')}.dam.mapper.${domainName}Mapper" ) />
<#assign mapperName="${domainName}Mapper" />
<#assign mapperNm="${domainNm}Mapper" />
//-----------------------------------------------------------------------------
//
// The "CopyPasteClazzTemplate" provides template functionality for binding configuration information 
// for complex classes such as services and message classes.
//
// See the code snippet template information for each class below.
//
//-----------------------------------------------------------------------------


/**
 * Please copy-paste the code snippet below without conflict in the ${basePackage}.service.${serviceName} class.
 */
package ${basePackage}.service;

import static ${pluginPackage}.mybatis.mapper.BaseMapper.rowBounds;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${pluginPackage}.context.annotation.Loggable;
import ${pluginPackage}.mybatis.mapper.BaseMapper;
import ${pluginPackage}.mybatis.model.ResultMap;
import ${modelClazz};
import ${mapperClazz};

@Loggable
@Service
public class ${serviceName} {


    ${context.value('businessComment')!}

    @Autowired
    private ${mapperName} ${mapperNm};

    public ${domainName} getAt${domainName}Mapper( Map<String, Object> param ) {
        return ${domainNm}Mapper.get( param );
    }

    public List<${domainName}> queryAt${domainName}Mapper( Map<String, Object> param ) {
        return ${domainNm}Mapper.query( param, rowBounds( param ) );
    }

    public List<ResultMap> queryMapAt${domainName}Mapper( Map<String, Object> param ) {
        return ${domainNm}Mapper.queryForMap( param, rowBounds( param ) );
    }

    @Transactional
    public void saveOrUpdateAt${domainName}Mapper( ${domainName} ${domainNm} ) {
        ${domainNm}Mapper.saveOrUpdate( ${domainNm} );
    }

    @Transactional
    public void addAt${domainName}Mapper( ${domainName} ${domainNm} ) {
        ${domainNm}Mapper.add( ${domainNm} );
    }

    @Transactional
    public void modifyAt${domainName}Mapper( ${domainName} ${domainNm} ) {
        ${domainNm}Mapper.modify( ${domainNm} );
    }

    @Transactional
    public void removeAt${domainName}Mapper( ${domainName} ${domainNm} ) {
        ${domainNm}Mapper.remove( ${domainNm} );
    }

    @Transactional
    public void enableAt${domainName}Mapper( Map<String, Object> param ) {
        ${domainNm}Mapper.enable( param );
    }

    @Transactional
    public void disableAt${domainName}Mapper( Map<String, Object> param ) {
        ${domainNm}Mapper.disable( param );
    }

}


/**
 * Please copy-paste the code snippet below without conflict in the ${basePackage}.message.RequestMessage class.
 */
package ${basePackage}.message;

public class RequestMessage
    implements Serializable {

    ${context.value('businessComment')!}

    private ${domainName} ${domainNm};
    
    public ${domainName} get${domainName}()
    {
        return ${domainNm};
    }
    
    public void set${domainName}( ${domainName} ${domainNm} )
    {
        this.${domainNm} = ${domainNm};
    }
    
    private List<${domainName}> ${domainNm}s;
    
    public List<${domainName}> get${domainName}s()
    {
        return this.${domainNm}s;
    }
    
    public void set${domainName}s( List<${domainName}> ${domainNm}s )
    {
        this.${domainNm}s = ${domainNm}s;
    }

}


/**
 * Please copy-paste the code snippet below without conflict in the ${basePackage}.message.ResponseMessage class.
 */
package ${basePackage}.message;

public class ResponseMessage
    implements Serializable {

    ${context.value('businessComment')!}

    private ${domainName} ${domainNm};
    
    public ${domainName} get${domainName}()
    {
        return ${domainNm};
    }
    
    public void set${domainName}( ${domainName} ${domainNm} )
    {
        this.${domainNm} = ${domainNm};
    }
    
    private List<${domainName}> ${domainNm}s;
    
    public List<${domainName}> get${domainName}s()
    {
        return this.${domainNm}s;
    }
    
    public void set${domainName}s( List<${domainName}> ${domainNm}s )
    {
        this.${domainNm}s = ${domainNm}s;
    }

}

