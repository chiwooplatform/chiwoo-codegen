package org.chiwooplatform.gen.builder;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class CopyPasteClazzTemplateCallback
    extends AbstractBuilder {

    public CopyPasteClazzTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    public String build()
        throws Exception {
        String taskName = null;
        if ( holder.getPkgNm() != null ) {
            taskName = String.format( "// ~ %s : %s ", holder.getPkgNm().toUpperCase(),
                                      holder.getDomainName().toUpperCase() );
        } else {
            taskName = String.format( "// ~ %s ", "TASK", holder.getDomainName().toUpperCase() );
        }
        final String businessComment = StringUtils.rightPad( taskName, 75, '-' );
        holder.getContext().add( "businessComment", businessComment );
        Template t = holder.freemarkerConfigure().getTemplate( "builder.copypaste-clazz.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    public List<TableColumnMeta> getColumnsMeta() {
        return holder.columnsMeta();
    }
}
