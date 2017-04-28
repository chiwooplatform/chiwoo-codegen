<#assign packageName=isNotEmpty( pkgNm )?then( "${basePackage}.dam.mapper.${pkgNm}", "${basePackage}.dam.mapper" ) />
<#assign interfaceClazz=isNotEmpty( mapConverClazz )?then( 
    "MapConvertable", 
    "Serializable" ) />
package ${packageName};

import java.util.Date;
import java.util.Map;

<#if isNotEmpty(mapConverClazz)>
import ${mapConverClazz};
import ${converterClazz};
<#else>
import java.io.Serializable;
</#if>
<#if isNotEmpty(dbmsCodeClazz)>
import ${dbmsCodeClazz};
</#if>

import ${basePackage}.Constants;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author yourname <yourname@your.email>
 */
@SuppressWarnings("serial")
@Alias("${abbrName()}")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonRootName("${abbrName()}")
public class ${domainName}
    implements ${interfaceClazz} 
{
${context.value('declearFields')}
<#if isNotEmpty(mapConverClazz)>

    public Map<String, Object> toMap()
        throws Exception {
        return ConverterUtils.toMap( this );
    }
</#if>
${context.value('declearToString')!""}
${context.value('declearGetterSetterMethods')}

}