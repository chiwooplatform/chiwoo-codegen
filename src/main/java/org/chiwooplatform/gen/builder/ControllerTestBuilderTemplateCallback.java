package org.chiwooplatform.gen.builder;

import java.util.List;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.Attribute;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class ControllerTestBuilderTemplateCallback
    extends AbstractDataBuilder {

    public ControllerTestBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    private String uriPath;

    private String pathVars;

    private void setLocalVariables( List<Attribute> attrs ) {
        StringBuffer paths = new StringBuffer();
        StringBuffer pathVars = new StringBuffer();
        for ( Attribute key : attrs ) {
            String nm = key.getNm();
            paths.append( "/" ).append( "{" ).append( nm ).append( "}" );
            pathVars.append( ", " ).append( nm );
        }
        this.uriPath = paths.toString();
        this.pathVars = pathVars.toString();
    }

    private Attribute primaryAttr( List<Attribute> primaryAttrs ) {
        if ( primaryAttrs != null ) {
            return primaryAttrs.get( 0 );
        }
        return null;
    }

    public String build()
        throws Exception {
        List<TableColumnMeta> columnsMeta = holder.columnsMeta();
        // columnsMeta.add( tempKey() ); // 테스트 목적 임.
        List<TableColumnMeta> primaryKeys = primaryColumns( columnsMeta );
        List<Attribute> primaryAttrs = primaryAttrs( primaryKeys );
        setLocalVariables( primaryAttrs );
        Attribute primaryAttr = primaryAttr( primaryAttrs );
        holder.getContext().add( "test.primaryAttr", primaryAttr );
        holder.getContext().add( "test.primaryAttrs", primaryAttrs );
        holder.getContext().add( "test.normalAttrs", normalAttrs( columnsMeta ) );
        holder.getContext().add( "test.assertAttr", assertionAttr( columnsMeta ) );
        holder.getContext().add( "test.model", model( columnsMeta ) );
        holder.getContext().add( "test.uriPath", this.uriPath );
        holder.getContext().add( "test.pathVars", this.pathVars );
        holder.getContext().add( "supportEnableDisable", supportEnableDisable( columnsMeta ));
        Template t = holder.freemarkerConfigure().getTemplate( "builder.test-rest-controller.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    //    protected TableColumnMeta tempKey() {
    //        TableColumnMeta c = new TableColumnMeta();
    //        c.setColumn_name( "uuid" );
    //        c.setColumn_size( 100 );
    //        c.setComments( "임시 아이디" );
    //        c.setData_precision( null );
    //        c.setData_scale( null );
    //        c.setDefaultValue( null );
    //        c.setNullable( false );
    //        c.setPrimarykey( true );
    //        c.setType( "String" );
    //        return c;
    //    }
    public List<TableColumnMeta> getColumnsMeta() {
        return holder.columnsMeta();
    }
}
