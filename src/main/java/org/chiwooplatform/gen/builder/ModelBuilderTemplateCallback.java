package org.chiwooplatform.gen.builder;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class ModelBuilderTemplateCallback
    extends AbstractBuilder {

    private boolean buildToString = true;

    public ModelBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    private String buildDeclearFields() {
        List<TableColumnMeta> _columnsMeta = getColumnsMeta();
        StringBuilder builder = new StringBuilder();
        // TODO need enhancement for chooseing inflector by ConjectionType
        // ConjectionType conjectionType = ConjectionType.get( props.getNamingConjection() );
        for ( TableColumnMeta col : _columnsMeta ) {
            String cname = col.getColumn_name().toLowerCase();
            String attrName = inflector.lowerCamelCase( cname, '_' );
            String javaType = javaType( col );
            builder.append( NL );
            if ( "Date".equals( javaType ) ) {
                builder.append( NLBS4 ).append( "@JsonFormat(pattern = AppConstants.DEFAULT_TIMESTAMP_FORMAT)" );
            }
            if ( cname.endsWith( "_cd" ) ) {
                String comments = col.getComments();
                if ( StringUtils.isEmpty( comments ) ) {
                    comments = cname;
                } else {
                    comments = getTokendText( comments );
                }
                builder.append( NLBS4 ).append( "@DbmsCode(parent = -1, description = " ).append( CM )
                       .append( comments ).append( CM ).append( ")" );
            }
            if ( cname.endsWith( "_yn" ) ) {
                builder.append( NLBS4 ).append( "@Range(min = 0, max = 1)" );
            }
            if ( !col.isNullable() ) {
                if ( "reg_dtm".equals( cname ) || "upd_dtm".equals( cname ) ) {
                    // skip mandatory option
                } else {
                    if ( "String".equals( javaType ) ) {
                        builder.append( NLBS4 ).append( "@NotEmpty" );
                    } else {
                        builder.append( NLBS4 ).append( "@NotNull" );
                    }
                    builder.append( NLBS4 ).append( "@JsonProperty(required = true)" );
                }
            }
            builder.append( NLBS4 ).append( "private " );
            builder.append( javaType );
            builder.append( " " ).append( attrName ).append( ";" );
        }
        return builder.toString();
    }

    private String buildGetterSetterMethods() {
        List<TableColumnMeta> _columnsMeta = getColumnsMeta();
        StringBuilder builder = new StringBuilder();
        builder.append( NLBS4 ).append( "// ~ getter / setter methods ----------------------------------------------" );
        for ( TableColumnMeta col : _columnsMeta ) {
            String attrName = inflector.lowerCamelCase( col.getColumn_name().toLowerCase(), '_' );
            builder.append( NLBS4 )
                   .append( String.format( "public final %s get%s()", javaType( col ), upperName( attrName ) ) );
            builder.append( NLBS4 ).append( "{" );
            builder.append( NLBS4 ).append( "    return " ).append( attrName ).append( ";" );
            builder.append( NLBS4 ).append( "}" );
            builder.append( NL );
            builder.append( NLBS4 ).append( String.format( "public final void set%s( %s %s)", upperName( attrName ),
                                                           javaType( col ), attrName ) );
            builder.append( NLBS4 ).append( "{" );
            builder.append( NLBS4 ).append( String.format( "    this.%s = %s;", attrName, attrName ) );
            builder.append( NLBS4 ).append( "}" );
        }
        return builder.toString();
    }

    private String buildToString() {
        List<TableColumnMeta> _columnsMeta = getColumnsMeta();
        StringBuilder builder = new StringBuilder();
        // BEGIN - toString
        builder.append( NLBS4 ).append( "@Override" );
        builder.append( NLBS4 ).append( "public String toString()" );
        builder.append( NLBS4 ).append( "{" );
        builder.append( NLBS4 ).append( "    StringBuilder builder = new StringBuilder();" );
        builder.append( NLBS4 ).append( "    builder.append( " ).append( '"' )
               .append( getClazzName( holder.getDomainName() ) ).append( " [" );
        int i = 0;
        for ( TableColumnMeta col : _columnsMeta ) {
            String attrName = inflector.lowerCamelCase( col.getColumn_name().toLowerCase(), '_' );
            if ( i == 0 ) {
                builder.append( attrName ).append( "=" ).append( CM ).append( " );" );
                builder.append( NLBS4 ).append( "    builder.append( " ).append( attrName ).append( " );" );
            } else {
                builder.append( NLBS4 ).append( "    builder.append( " ).append( CM ).append( ", " ).append( attrName )
                       .append( "=" ).append( CM ).append( " );" );
                builder.append( NLBS4 ).append( "    builder.append( " ).append( attrName ).append( " );" );
            }
            ++i;
        }
        builder.append( NL ).append( "        builder.append( " ).append( '"' ).append( "]" ).append( '"' )
               .append( " );" );
        builder.append( NL ).append( "        return builder.toString();" );
        builder.append( NL ).append( "    }" );
        // E-N-D - toString
        return builder.toString();
    }

    public List<TableColumnMeta> getColumnsMeta() {
        return holder.columnsMeta();
    }

    public String build()
        throws Exception {
        holder.getContext().add( "declearFields", buildDeclearFields() );
        if ( buildToString ) {
            holder.getContext().add( "declearToString", buildToString() );
        }
        holder.getContext().add( "declearGetterSetterMethods", buildGetterSetterMethods() );
        Template t = holder.freemarkerConfigure().getTemplate( "builder.model.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }
}
