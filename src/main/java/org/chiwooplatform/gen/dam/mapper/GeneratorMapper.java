package org.chiwooplatform.gen.dam.mapper;

import java.util.List;
import java.util.Map;

import org.chiwooplatform.gen.model.TableColumnMeta;

/**
 * <pre>
 * Mybatis Mapper 파일을 제네레이터 하기위한 툴 입니다.
 * </pre>
 */
public interface GeneratorMapper
{
    List<TableColumnMeta> getColumnsMeta( Map<String, Object> map )
        throws RuntimeException;
}
