package org.chiwooplatform.gen.builder.mybatis;

import java.util.List;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.builder.AbstractBuilder;
import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class MybatisMapperBuilderTemplateCallback
    extends AbstractBuilder {

    public MybatisMapperBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    public String build()
        throws Exception {
        Template t = holder.freemarkerConfigure().getTemplate( "builder.mybatis-mapper.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    public List<TableColumnMeta> getColumnsMeta() {
        return holder.columnsMeta();
    }
}
