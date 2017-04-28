<#assign packageName=isNotEmpty( pkgNm )?then( "${basePackage}.dam.mapper.${pkgNm}", "${basePackage}.dam.mapper" ) />
<#assign modelClazz=isNotEmpty( pkgNm )?then( "${basePackage}.model.${pkgNm}.${domainName}", "${basePackage}.model.${domainName}" ) />
<#assign primaryAttrs=value('test.primaryAttrs') />
<#assign primaryAttr=value('test.primaryAttr') />
<#assign assertAttr=value('test.assertAttr') />
<#assign multiKeys=(primaryAttrs?size > 1)?then( 1, 0 ) />
package ${packageName};

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import ${pluginPackage}.mybatis.model.ResultMap;
import ${pluginPackage}.mybatis.mapper.BaseMapper;
import ${basePackage}.AbstractMapperTests;
import ${modelClazz};
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@ActiveProfiles(profiles = { /* "local", "dev" */ })
public class ${domainName}MapperTests
    extends AbstractMapperTests<${domainName}> {

    private Date CURRENT_TIMESTAMP = new Date();

    @Autowired
    private ${domainName}Mapper mapper;

<#list primaryAttrs as k>
    private ${k.type} ${k.nm} = ${k.val};
</#list>

<#if multiKeys==0>
    protected ${primaryAttr.type} id()
    {
        return this.${primaryAttr.nm};
    }
<#else>
    protected Map<String,Object> id()
    {
        Map<String,Object> id = new HashMap();
<#list primaryAttrs as k>
        id.put("${k.nm}", ${k.val});
        return id;
</#list>
    }
</#if>

${value('test.model')}

    @Test
    public void mapperIsNotNullTest() {
        logger.info( "mapper: {}", mapper );
        assertNotNull( mapper );
    }

    @Rollback(true)
    @Test
    public void ut1001_CRUD() {
        ${domainName} model = model();
        // mapper.saveOrUpdate( model );
        mapper.add( model );
        assertThat( capture.toString(), containsString( "insert into" ) );
        capture.flush();
        ${domainName} result = mapper.get( id() );
        assertThat( result.get${primaryAttr.name}(), is( this.${primaryAttr.nm} ) );
        logger.info( "result: {}", result );
<#if assertAttr??>
        result.set${assertAttr.name}( ${assertAttr.assertVal} );
        mapper.modify( result );
        result = mapper.get( id() );
        assertThat( result.get${assertAttr.name}(), is( ${assertAttr.assertVal} ) );
<#else>
        // result.setXXX( 'XXX' );
        mapper.modify( result );
        result = mapper.get( this.${primaryAttr.nm} );
        // assertThat( result.getXXX(), is( 'XXX' ) );
</#if>
        mapper.remove( id() );
        result = mapper.get( this.${primaryAttr.nm} );
        assertNull( result );
    }

    @Test
    public void ut1002_queryRowBounds()
        throws Exception {
        Code model = model();
        mapper.saveOrUpdate( model );
        Map<String, Object> param = new HashMap<>();
        param.put( "cd_id", this.cdId );
        // If you want to get the total record count, set BaseMapper.TOTAL_ROWCOUNT attribute value.
        param.put( BaseMapper.TOTAL_ROWCOUNT, null );
        // If you want to sort the columns in SQL, set value like "col1,col2:desc" format to BaseMapper.ORDERBY.
        param.put( BaseMapper.ORDERBY, "cd_id:desc," );
        logger.info( "TOTAL_ROWCOUNT: {}", param.get( BaseMapper.TOTAL_ROWCOUNT ) );
        RowBounds bounds = new RowBounds( 1, 10 );
        List<Code> list = mapper.query( param, bounds );
        assertNotNull( list );
        List<ResultMap> results = mapper.queryForMap( param, null );
        assertNotNull( results );
    }

}
