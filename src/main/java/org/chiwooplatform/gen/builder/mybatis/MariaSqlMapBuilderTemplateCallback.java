package org.chiwooplatform.gen.builder.mybatis;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.builder.AbstractDataBuilder;
import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.Attribute;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class MariaSqlMapBuilderTemplateCallback
    extends AbstractDataBuilder {

    protected final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

    public MariaSqlMapBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    private String saveOrUpdateSQL( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "insert  into " ).append( holder.tableName() ).append( " (" );
        for ( Attribute col : attrs ) {
            String cname = col.getColumn();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            builder.append( NL ).append( "        " );
            builder.append( col.getColumn() ).append( "," );
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        builder.append( NL ).append( "values (" );
        for ( Attribute attr : attrs ) {
            String cname = attr.getColumn();
            String nm = attr.getNm();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            String jdbcType = jdbcType( attr.getType() );
            builder.append( NL ).append( "        " );
            if ( cname.equals( holder.columnRegDtm() ) ) {
                builder.append( String.format( "%s,", CURRENT_TIMESTAMP ) );
            } else {
                builder.append( String.format( "#{%s,jdbcType=%s},", nm, jdbcType ) );
            }
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        builder.append( NL ).append( "on duplicate key " );
        builder.append( NL ).append( "update" );
        int i = 0;
        for ( Attribute attr : attrs ) {
            if ( attr.isPrimaryKey() ) {
                continue;
            }
            String nm = attr.getNm();
            String cname = attr.getColumn();
            if ( cname.equals( holder.columnRegDtm() ) || cname.equals( holder.getColumnCreator() ) ) {
                continue;
            }
            String jdbcType = jdbcType( attr.getType() );
            if ( i == 0 ) {
                builder.append( "  " );
            } else {
                builder.append( NL ).append( "        " );
            }
            if ( cname.equals( holder.columnUpdDtm() ) ) {
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "%s,", CURRENT_TIMESTAMP ) );
            } else {
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s},", nm, jdbcType ) );
            }
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        return builder.toString();
    }

    private String insertSQL( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "insert  into " ).append( holder.tableName() ).append( " (" );
        for ( Attribute col : attrs ) {
            String cname = col.getColumn();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            builder.append( NL ).append( "        " );
            builder.append( col.getColumn() ).append( "," );
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        builder.append( NL ).append( "values (" );
        for ( Attribute col : attrs ) {
            String nm = col.getNm();
            String cname = col.getColumn();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            String jdbcType = jdbcType( col.getType() );
            builder.append( NL ).append( "        " );
            if ( cname.equals( holder.columnRegDtm() ) ) {
                builder.append( String.format( "%s,", CURRENT_TIMESTAMP ) );
            } else {
                builder.append( String.format( "#{%s,jdbcType=%s},", nm, jdbcType ) );
            }
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        return builder.toString();
    }

    private String updateSQL( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "update  " ).append( holder.tableName() );
        builder.append( NL ).append( "set" );
        int i = 0;
        for ( Attribute col : attrs ) {
            String nm = col.getNm();
            String cname = col.getColumn();
            if ( cname.equals( holder.columnRegDtm() ) || cname.equals( holder.getColumnCreator() ) ) {
                continue;
            }
            String jdbcType = jdbcType( col.getType() );
            if ( col.isPrimaryKey() ) {
                continue;
            }
            if ( i == 0 ) {
                builder.append( "     " );
            } else {
                builder.append( NL ).append( "        " );
            }
            if ( cname.equals( holder.columnUpdDtm() ) ) {
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "%s,", CURRENT_TIMESTAMP ) );
            } else {
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s},", nm, jdbcType ) );
            }
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        i = 0;
        for ( Attribute col : attrs ) {
            if ( !col.isPrimaryKey() ) {
                continue;
            }
            String nm = col.getNm();
            String cname = col.getColumn();
            String jdbcType = jdbcType( col.getType() );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    private String deleteSQL( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "delete" );
        builder.append( NL ).append( "from    " ).append( holder.tableName() );
        //        List<TableColumnMeta> pkeys = new ArrayList<>();
        int i = 0;
        for ( Attribute col : attrs ) {
            if ( !col.isPrimaryKey() ) {
                continue;
            }
            String nm = col.getNm();
            String cname = col.getColumn();
            String jdbcType = jdbcType( col.getType() );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    private String selectGetSQL( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "select  " );
        int i = 0;
        for ( Attribute col : attrs ) {
            String cname = col.getColumn();
            if ( i > 0 ) {
                builder.append( NL ).append( "        " );
            }
            builder.append( "a." );
            builder.append( cname ).append( "," );
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( NL ).append( "from    " ).append( holder.tableName() ).append( " a" );
        i = 0;
        for ( Attribute col : attrs ) {
            if ( !col.isPrimaryKey() ) {
                continue;
            }
            String nm = col.getNm();
            String cname = col.getColumn();
            String jdbcType = jdbcType( col.getType() );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( "a." + cname ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( "a." + cname ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    private String selectListSQL( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "select  " );
        int i = 0;
        for ( Attribute col : attrs ) {
            String cname = col.getColumn();
            if ( i > 0 ) {
                builder.append( NL ).append( "        " );
            }
            builder.append( "a." );
            builder.append( cname ).append( "," );
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( NL ).append( "from    " ).append( holder.tableName() ).append( " a" );
        i = 0;
        for ( Attribute col : attrs ) {
            if ( !col.isPrimaryKey() ) {
                continue;
            }
            String nm = col.getNm();
            String cname = col.getColumn();
            String jdbcType = jdbcType( col.getType() );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   1 = 1" );
            } else {
                // TODO 나중에 쿼리 조건을 동적으로 바이딩 되도록 보완 하자구
                builder.append( NL ).append( "-- and     " ).append( "a." + cname ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    private String selectKey( List<Attribute> attrs ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "<selectKey keyProperty=" ).append( CM ).append( "existsYn" ).append( CM )
               .append( " resultType=" ).append( CM ).append( holder.abbrName() ).append( CM ).append( " order=" )
               .append( CM ).append( "BEFORE" ).append( CM ).append( "<![CDATA[" );
        builder.append( NL ).append( "select  case" );
        builder.append( NLBS10 ).append( "when count(1) > 0 then 1 else 0 end as existsYn" );
        builder.append( NL ).append( "from    " ).append( holder.tableName() ).append( " a" );
        int ii = 0;
        for ( Attribute pk : attrs ) {
            if ( pk.isPrimaryKey() ) {
                continue;
            }
            String nm = pk.getNm();
            String cname = pk.getColumn();
            String jdbcType = jdbcType( pk.getType() );
            if ( ii == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( "a." + cname ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( "a." + cname ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            }
            ++ii;
        }
        builder.append( NL ).append( "]]></selectKey>" );
        return builder.toString();
    }

    private String enableDisableSQL( List<Attribute> attrs, boolean enabled ) {
        String enableColumnName = null;
        for ( Attribute col : attrs ) {
            String cname = col.getColumn();
            if ( "use_yn".equals( cname ) || "useyn".equals( cname ) ) {
                enableColumnName = cname;
                break;
            }
        }
        if ( enableColumnName == null ) {
            return null;
        }
        String value = ( enabled ? "1" : "0" );
        StringBuilder builder = new StringBuilder();
        builder.append( "update  " ).append( holder.tableName() );
        builder.append( NL ).append( "set     " ).append( enableColumnName ).append( " = " ).append( value );
        int i = 0;
        for ( Attribute pk : attrs ) {
            if ( pk.isPrimaryKey() ) {
                continue;
            }
            String nm = pk.getNm();
            String cname = pk.getColumn();
            String jdbcType = jdbcType( pk.getType() );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", nm, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    public String build()
        throws Exception {
        List<TableColumnMeta> columnsMeta = getColumnsMeta();
        List<Attribute> attrs = normalAttrs( columnsMeta );
        List<Attribute> primaryAttrs = primaryAttrs( primaryColumns( columnsMeta ) );
        holder.getContext().add( "mapper.parameterType", parameterType( primaryAttrs ) );
        holder.getContext().add( "mapper.selectKey", selectKey( attrs ) );
        holder.getContext().add( "mapper.saveOrUpdate", saveOrUpdateSQL( attrs ) );
        holder.getContext().add( "mapper.insert", insertSQL( attrs ) );
        holder.getContext().add( "mapper.update", updateSQL( attrs ) );
        holder.getContext().add( "mapper.delete", deleteSQL( attrs ) );
        holder.getContext().add( "mapper.get", selectGetSQL( attrs ) );
        holder.getContext().add( "mapper.query", selectListSQL( attrs ) );
        holder.getContext().add( "mapper.enable", enableDisableSQL( attrs, true ) );
        holder.getContext().add( "mapper.disable", enableDisableSQL( attrs, false ) );
        Template t = holder.freemarkerConfigure().getTemplate( "builder.mybatis-sqlmap.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    public List<TableColumnMeta> getColumnsMeta() {
        return holder.columnsMeta();
    }
}
