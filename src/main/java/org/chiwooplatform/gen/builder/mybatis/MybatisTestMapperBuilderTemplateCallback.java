package org.chiwooplatform.gen.builder.mybatis;

import java.util.List;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.builder.AbstractDataBuilder;
import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.Attribute;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class MybatisTestMapperBuilderTemplateCallback
    extends AbstractDataBuilder {

    public MybatisTestMapperBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    private Attribute primaryAttr( List<Attribute> primaryAttrs ) {
        if ( primaryAttrs != null ) {
            return primaryAttrs.get( 0 );
        }
        return null;
    }

    public String build()
        throws Exception {
        List<TableColumnMeta> columnsMeta = getColumnsMeta();
        List<TableColumnMeta> primaryColumns = primaryColumns( columnsMeta );
        List<Attribute> primaryAttrs = primaryAttrs( primaryColumns );
        Attribute primaryAttr = primaryAttr( primaryAttrs );
        Attribute assertionAttr = assertionAttr( columnsMeta );
        holder.getContext().add( "test.primaryAttr", primaryAttr );
        holder.getContext().add( "test.assertAttr", assertionAttr );
        holder.getContext().add( "test.primaryAttrs", primaryAttrs );
        holder.getContext().add( "test.model", model( columnsMeta ) );
        holder.getContext().add( "supportEnableDisable", supportEnableDisable( columnsMeta ));
        Template t = holder.freemarkerConfigure().getTemplate( "builder.test-mybatis-mapper.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    public List<TableColumnMeta> getColumnsMeta() {
        return holder.columnsMeta();
    }
}
