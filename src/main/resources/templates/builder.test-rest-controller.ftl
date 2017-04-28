<#assign packageName=isNotEmpty( pkgNm )?then( "${basePackage}.rest.${pkgNm}", "${basePackage}.rest" ) />
<#assign restName="${domainName}Controller" />
<#assign modelClazz=isNotEmpty( pkgNm )?then( "${basePackage}.model.${pkgNm}.${domainName}", "${basePackage}.model.${domainName}" ) />
<#assign primaryAttrs=value('test.primaryAttrs') />
<#assign primaryAttr=value('test.primaryAttr') />
<#assign assertAttr=value('test.assertAttr') />
<#assign multiKeys=(primaryAttrs?size > 1)?then( 1, 0 ) />
<#assign attrs=value('test.normalAttrs') />
<#assign pathVars=value('test.pathVars') />
package ${packageName};

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import org.chiwooplatform.context.Constants;
import org.chiwooplatform.simple.AbstractControllerTests;
import ${modelClazz};

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { /* "local", "dev" */ })
public class ${restName}Test
    extends AbstractControllerTests<${domainName}> {

<#list primaryAttrs as k>
    private ${k.type} ${k.nm} = ${k.val};
</#list>

${value('test.model')}

    @Test
    public void loaderTest() {
        logger.info( "mockMvc: {}", mockMvc );
        assertNotNull( mockMvc );
    }

    /**
     * {@link ${restName}#add}
     * 
     * @throws Exception
     */
    @Test
    public void ut1001_add()
        throws Exception {
        ${domainName} ${domainNm} = model();
        // ${domainNm}.set${assertAttr.name}( ${assertAttr.assertVal} );
        String content = mockMessage( ${domainNm} );
        logger.debug( "content: {}", content );
        final String uri = ${restName}.BASE_URI;
        ResultActions actions = mockMvc.perform( post( uri ).contentType( MediaType.APPLICATION_JSON )
                                                            .header( Constants.AUTH_TOKEN, token( token() ) )
                                                            .content( content ) );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isCreated() );
        actions.andDo( print() );
    }

    /**
     * {@link ${restName}#get}
     * 
     * @throws Exception
     */
    @Test
    public void ut1002_get()
        throws Exception {
        final String uri = ${restName}.BASE_URI + "${value('test.uriPath')!''}";
<#list primaryAttrs as k>
        ${k.type} ${k.nm} = this.${k.nm};
</#list>
        ResultActions actions = mockMvc.perform( get( uri${pathVars} ).contentType( MediaType.APPLICATION_JSON )
                                                                 .header( Constants.AUTH_TOKEN, token() ) );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isOk() );
        // actions.andExpect( jsonPath( "$.${primaryAttr.nm}", equalTo( this.${primaryAttr.nm} ) ) );
        actions.andDo( print() );
    }

    /**
     * {@link ${restName}#query}
     * 
     * @throws Exception
     */
    @Test
    public void ut1003_query()
        throws Exception {
        final String uri = ${restName}.BASE_URI + "/query";
        ResultActions actions = mockMvc.perform( get( uri ).contentType( MediaType.APPLICATION_JSON )
                                                           .header( Constants.AUTH_TOKEN, token() )
                                                           // @formatter:off
                                                           // .param( lang(), "ko" )
<#list attrs as k>
                                                           // .param( "${k.nm}", "${k.value}" )
</#list>
                                                           // @formatter:on
                                                         );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isOk() );
        // actions.andExpect( jsonPath( "$[0].${primaryAttr.nm}", equalTo( this.${primaryAttr.nm}  ) ) );
        actions.andDo( print() );
    }

    /**
     * {@link ${restName}#modify}
     * 
     * @throws Exception
     */
    @Test
    public void ut1004_modify()
        throws Exception {
        ${domainName} ${domainNm} = model();
        ${domainNm}.set${assertAttr.name}( ${assertAttr.assertVal} );
        String content = mockMessage( ${domainNm} );
        logger.debug( "content: {}", content );
        final String uri = ${restName}.BASE_URI + "${value('test.uriPath')!''}";
<#list primaryAttrs as k>
        ${k.type} ${k.nm} = this.${k.nm};
</#list>
        ResultActions actions = mockMvc.perform( put( uri${pathVars} ).contentType( MediaType.APPLICATION_JSON )
                                                            .header( Constants.AUTH_TOKEN, token( token() ) )
                                                            .content( content ) );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isOk() );
        // actions.andExpect( jsonPath( "$.${primaryAttr.nm}", equalTo( this.${primaryAttr.nm} ) ) );
        actions.andDo( print() );
    }

    /**
     * {@link ${restName}#remove}
     * 
     * @throws Exception
     */
    @Test
    public void ut1005_remove()
        throws Exception {
        final String uri = ${restName}.BASE_URI + "${value('test.uriPath')!''}";
<#list primaryAttrs as k>
        ${k.type} ${k.nm} = this.${k.nm};
</#list>
        ResultActions actions = mockMvc.perform( delete( uri${pathVars} ).contentType( MediaType.APPLICATION_JSON )
                                                                 .header( Constants.AUTH_TOKEN, token() ) );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isNoContent() );
        actions.andDo( print() );
    }

    /**
     * {@link ${restName}#enable}
     * 
     * @throws Exception
     */
    @Test
    public void ut1006_enable()
        throws Exception {
        final String uri = ${restName}.BASE_URI + "${value('test.uriPath')!''}" + "/enable";
<#list primaryAttrs as k>
        ${k.type} ${k.nm} = this.${k.nm};
</#list>
        ResultActions actions = mockMvc.perform( put( uri${pathVars} ).contentType( MediaType.APPLICATION_JSON )
                                                                 .header( Constants.AUTH_TOKEN, token() ) );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isNoContent() );
        actions.andDo( print() );
    }

    /**
     * {@link ${restName}#enable}
     * 
     * @throws Exception
     */
    @Test
    public void ut1007_disable()
        throws Exception {
        final String uri = ${restName}.BASE_URI + "${value('test.uriPath')!''}" + "/disable";
<#list primaryAttrs as k>
        ${k.type} ${k.nm} = this.${k.nm};
</#list>
        ResultActions actions = mockMvc.perform( put( uri${pathVars} ).contentType( MediaType.APPLICATION_JSON )
                                                                 .header( Constants.AUTH_TOKEN, token() ) );
        actions.andExpect( content().contentType( MediaType.APPLICATION_JSON_UTF8 ) );
        actions.andExpect( status().isNoContent() );
        actions.andDo( print() );
    }

}
