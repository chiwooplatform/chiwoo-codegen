<#assign packageName=isNotEmpty( pkgName() )?then( "${context.value('basePackage')}.rest.${pkgNm}", 
    "${context.value('basePackage')}.rest" ) />
<#assign modelClazz=isNotEmpty( pkgName() )?then( "${context.value('basePackage')}.model.${pkgNm}.${domainName}", 
    "${context.value('basePackage')}.model.${domainName}" ) />
<#assign baseUri=isNotEmpty( pkgName() )?then( "/${pkgNm}/${abbr}s", "/${abbr}s" ) />
<#assign pathVars="${context.value('controller.pathVars')!}" />
<#assign buildParam="${context.value('controller.buildParam')!}" />
package ${packageName};

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${pluginPackage}.context.Constants;
import ${pluginPackage}.context.ContextHolder;
import ${pluginPackage}.context.annotation.Loggable;
import ${pluginPackage}.context.supports.ConverterUtils;
import ${pluginPackage}.web.supports.RequestValidator;
import ${pluginPackage}.web.supports.WebUtils;
import ${basePackage}.message.RequestMessage;
import ${basePackage}.message.ResponseMessage;
import ${modelClazz};
import ${basePackage}.service.${serviceName};

/**
 * @author yourname <yourname@your.email>
 */
@Loggable
@RestController
public class ${domainName}Controller {

    protected static final String BASE_URI = "${baseUri}";

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    private RequestValidator validator;

    @Autowired
    private ${serviceName} ${serviceNm};

    /**
     * Get the '${domainNm}' data corresponding to the URI PathVariables.
     *
     * @param token is the Authentication token.${context.value('controller.pathParams')}
     * @param param is the Query parameters.
     * @return the ResponseMessage that is converted to JSON or XML messages.
     */
    ${context.value('controller.mapping.get')}
    @PreAuthorize("hasPermission(#token, '${permCode}.get')")
    public ResponseEntity<?> get( @RequestHeader(Constants.AUTH_TOKEN) String token,${pathVars}
                                  @RequestParam Map<String, Object> param )
        throws Exception {
        // logger.debug( "tXID: {}, userseq: {}, principal: {}", ContextHolder.tXID(), ContextHolder.userseq(), ContextHolder.principal() );${buildParam}
        ResponseMessage response = new ResponseMessage();
        ${domainName} ${domainNm} = ${serviceNm}.getAt${domainName}Mapper( param );
        if ( ${domainNm} == null ) {
            throw WebUtils.notFoundException();
        }
        response.set${domainName}( ${domainNm} );
        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    /**
     * '${domainNm}' data is newly registered.
     *
     * @param token is the Authentication token.
     * @param requestMessage It is contained '${domainNm}' data to register.
     * @param request is the HttpServletRequest.
     * @return the ResponseMessage that is converted to JSON or XML messages.
     */
    ${context.value('controller.mapping.add')}
    @PreAuthorize("hasPermission(#token, '${permCode}.add')")
    public ResponseEntity<?> add( @RequestHeader(Constants.AUTH_TOKEN) String token,
                                  @RequestBody RequestMessage requestMessage, HttpServletRequest request ) {
        ${domainName} ${domainNm} = requestMessage.get${domainName}();
        validator.validate( ${domainNm} );
        ResponseMessage response = new ResponseMessage();
        ${domainNm}.set${attrCreator}( ContextHolder.userseq() ); // XXX 사용자 아이디 를 설정 
        ${serviceNm}.addAt${domainName}Mapper( ${domainNm} );
        response.set${domainName}( ${domainNm} );
        return new ResponseEntity<>( response, HttpStatus.CREATED );
    }

    /**
     * Get the '${domainNm}' data corresponding to the query parameters condition.
     *
     * @param param is the Query parameters.
     * @param token is the Authentication token.
     * @return the ResponseMessage that is converted to JSON or XML messages.
     */
    ${context.value('controller.mapping.query')}
    @PreAuthorize("hasPermission(#token, '${permCode}.query')")
    public ResponseEntity<?> query( @RequestHeader(Constants.AUTH_TOKEN) String token,
                                    @RequestParam Map<String, Object> param )
        throws Exception {
        ResponseMessage response = new ResponseMessage();
        List<${domainName}> ${domainNm}s = ConverterUtils.toBeans( ${serviceNm}.queryMapAt${domainName}Mapper( param ), ${domainName}.class );
        response.set${domainName}s( ${domainNm}s );
        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    /**
     * Modify the '${domainNm}' information.
     *
     * @param token is the Authentication token.
     * @param requestMessage It is contained '${domainNm}' data to register.
     * @param request is the HttpServletRequest.
     * @return the ResponseMessage that is converted to JSON or XML messages.
     */
    ${context.value('controller.mapping.modify')}
    @PreAuthorize("hasPermission(#token, '${permCode}.modify')")
    public ResponseEntity<?> modify( @RequestHeader(Constants.AUTH_TOKEN) String token,${pathVars}
                                     @RequestBody RequestMessage requestMessage, HttpServletRequest request ) {
        ${domainName} ${domainNm} = requestMessage.get${domainName}();
        validator.validate( ${domainNm} );
        ResponseMessage response = new ResponseMessage();
        ${domainNm}.set${attrModifier}( ContextHolder.userseq() ); 
        ${serviceNm}.modifyAt${domainName}Mapper( ${domainNm} );
        response.set${domainName}( ${domainNm} );
        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    /**
     * Remove the '${domainNm}' data corresponding to the URI PathVariables.
     *
     * @param token is the Authentication token.${context.value('controller.pathParams')}
     * @param param is the Query parameters.
     * @return the ResponseMessage that is converted to JSON or XML messages.
     */  
    ${context.value('controller.mapping.remove')}
    @PreAuthorize("hasPermission(#token, '${permCode}.remove')")
    public ResponseEntity<?> remove( @RequestHeader(Constants.AUTH_TOKEN) String token,${pathVars}
                                     @RequestParam Map<String, Object> param ) {${buildParam}
        ${serviceNm}.removeAt${domainName}Mapper( param );
        response.set${domainName}( ${domainNm} );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

    /**
     * Enable the '${domainNm}' data.
     *
     * @param token is the Authentication token.
     * @param requestMessage It is contained '${domainNm}' data to register.
     * @param request is the HttpServletRequest.
     * @return the ResponseMessage that is converted to JSON or XML messages.
     */
    ${context.value('controller.mapping.enable')}
    @PreAuthorize("hasPermission(#token, '${permCode}.enable')")
    public ResponseEntity<?> enable( @RequestHeader(Constants.AUTH_TOKEN) String token,${pathVars}
                                     @RequestParam Map<String, Object> param ) {${buildParam}
        param.put( "${attrModifierNm}", ContextHolder.userseq() );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

    /**
     * Disable the '${domainNm}' data.
     *
     * @param token is the Authentication token.
     * @param requestMessage It is contained '${domainNm}' data to register.
     * @param request is the HttpServletRequest.
     * @return the ResponseMessage that is converted to JSON or XML messages.
     */
    ${context.value('controller.mapping.disable')}
    @PreAuthorize("hasPermission(#token, '${permCode}.disable')")
    public ResponseEntity<?> disable( @RequestHeader(Constants.AUTH_TOKEN) String token,${pathVars}
                                      @RequestParam Map<String, Object> param ) {${buildParam}
        param.put( "${attrModifierNm}", ContextHolder.userseq() ); 
        ${serviceNm}.enableAt${domainName}Mapper( param );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }
}
