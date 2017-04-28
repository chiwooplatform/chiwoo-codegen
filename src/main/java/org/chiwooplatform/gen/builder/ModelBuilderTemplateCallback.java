package org.chiwooplatform.gen.builder;

import java.util.List;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.Attribute;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class ModelBuilderTemplateCallback
    extends AbstractDataBuilder {

    private boolean buildToString = true;

    public ModelBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    private String declearFields( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        for ( Attribute attr : attrs ) {
            String javaType = attr.getType();
            String nm = attr.getNm();
            builder.append( NL );
            if ( "Date".equals( javaType ) || "java.sql.Timestamp".equals( javaType ) ) {
                builder.append( NLBS4 )
                       .append( "@JsonFormat(pattern = Constants.DEFAULT_TIMESTAMP_FORMAT, timezone = Constants.LOCAL_TIMEZONE)" );
            }
            if ( nm.endsWith( "Cd" ) || nm.endsWith( "_cd" ) ) {
                builder.append( NLBS4 ).append( "@DbmsCode(parent = -1, description = " ).append( CM )
                       .append( attr.getComment().replaceAll( "'", "`" ) ).append( CM ).append( ")" );
            }
            if ( !attr.isNullable() ) {
                if ( "Date".equals( javaType ) ) {
                    // skip mandatory option
                } else if ( "String".equals( javaType ) ) {
                    builder.append( NLBS4 ).append( "@NotEmpty" );
                } else {
                    builder.append( NLBS4 ).append( "@NotNull" );
                }
                builder.append( NLBS4 ).append( "@JsonProperty(required = true)" );
            }
            builder.append( NLBS4 ).append( "private " );
            builder.append( javaType );
            builder.append( " " ).append( nm ).append( ";" );
        }
        return builder.toString();
    }

    private String getterSetterMethods( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        for ( Attribute attr : attrs ) {
            String javaType = attr.getType();
            String nm = attr.getNm();
            String name = attr.getName();
            builder.append( NLBS4 ).append( String.format( "public final %s get%s()", javaType, name ) );
            builder.append( NLBS4 ).append( "{" );
            builder.append( NLBS4 ).append( "    return " ).append( nm ).append( ";" );
            builder.append( NLBS4 ).append( "}" );
            builder.append( NL );
            builder.append( NLBS4 ).append( String.format( "public final void set%s( %s %s)", name, javaType, nm ) );
            builder.append( NLBS4 ).append( "{" );
            builder.append( NLBS4 ).append( String.format( "    this.%s = %s;", nm, nm ) );
            builder.append( NLBS4 ).append( "}" );
        }
        return builder.toString();
    }

    private String toString( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        // BEGIN - toString
        builder.append( NLBS4 ).append( "public String toString()" );
        builder.append( NLBS4 ).append( "{" );
        builder.append( NLBS4 ).append( "    StringBuilder builder = new StringBuilder();" );
        builder.append( NLBS4 ).append( "    builder.append( " ).append( '"' )
               .append( getClazzName( holder.getDomainName() ) ).append( " [" );
        int i = 0;
        for ( Attribute attr : attrs ) {
            String nm = attr.getNm();
            if ( i == 0 ) {
                builder.append( nm ).append( "=" ).append( CM ).append( " );" );
                builder.append( NLBS8 ).append( "builder.append( " ).append( nm ).append( " );" );
            } else {
                builder.append( NLBS8 ).append( "builder.append( " ).append( CM ).append( ", " ).append( nm )
                       .append( "=" ).append( CM ).append( " );" );
                builder.append( NLBS8 ).append( "builder.append( " ).append( nm ).append( " );" );
            }
            ++i;
        }
        builder.append( NLBS8 ).append( "builder.append( " ).append( '"' ).append( "]" ).append( '"' ).append( " );" );
        builder.append( NLBS8 ).append( "return builder.toString();" );
        builder.append( NLBS4 ).append( "}" );
        // E-N-D - toString
        return builder.toString();
    }

    public String build()
        throws Exception {
        List<TableColumnMeta> columnsMeta = getColumnsMeta();
        List<Attribute> normalAttrs = normalAttrs( columnsMeta );
        holder.getContext().add( "declearFields", declearFields( normalAttrs ) );
        if ( buildToString ) {
            holder.getContext().add( "declearToString", toString( normalAttrs ) );
        }
        holder.getContext().add( "declearGetterSetterMethods", getterSetterMethods( normalAttrs ) );
        Template t = holder.freemarkerConfigure().getTemplate( "builder.model.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    public List<TableColumnMeta> getColumnsMeta() {
        return holder.columnsMeta();
    }
}
