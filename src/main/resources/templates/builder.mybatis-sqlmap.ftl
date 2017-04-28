<#assign mapperNamespace=isNotEmpty( pkgName() )?then( 
    "${context.value('basePackage')}.dam.mapper.${pkgName()}.${domainName}Mapper", 
    "${context.value('basePackage')}.dam.mapper.${domainName}Mapper" ) />
<#assign modelAlias="${abbrName()}" />
<#assign paramType="${context.value('mapper.parameterType')}" />
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperNamespace}">

    <insert id="saveOrUpdate" parameterType="${modelAlias}"><![CDATA[
${context.value("mapper.saveOrUpdate")}
 ]]></insert>

    <!-- You can use selectKey on add query for auto-increment primary key.

${context.value("mapper.selectKey")}
    -->
    <insert id="add" parameterType="${modelAlias}"><![CDATA[
${context.value("mapper.insert")}
 ]]></insert>

    <update id="modify" parameterType="${modelAlias}"><![CDATA[
${context.value("mapper.update")}
 ]]></update>
<#if isNotEmpty( "${context.value('mapper.enable')!}" )>

    <update id="enable" parameterType="${paramType}"><![CDATA[
${context.value("mapper.enable")}
]]></update>

    <update id="disable" parameterType="${paramType}"><![CDATA[
${context.value("mapper.disable")}
]]></update>
</#if>

    <delete id="remove" parameterType="${paramType}"><![CDATA[
${context.value("mapper.delete")}
]]></delete>

    <select id="get" parameterType="${paramType}" resultType="${modelAlias}"><![CDATA[
${context.value("mapper.get")}
]]></select>

    <select id="getForMap" parameterType="${paramType}" resultType="rmap"><![CDATA[
${context.value("mapper.get")}
]]></select>

    <select id="query" parameterType="map" resultType="${modelAlias}"><![CDATA[
${context.value("mapper.query")}
]]></select>

    <select id="queryForMap" parameterType="map" resultType="rmap"><![CDATA[
${context.value("mapper.query")}
]]></select>

</mapper>