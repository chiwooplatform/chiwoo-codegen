package org.chiwooplatform.gen.builder.mybatis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.builder.AbstractDataBuilder;
import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class MariaDBMapperSqlBuilderTemplateCallback
    extends AbstractDataBuilder {

    protected final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

    public MariaDBMapperSqlBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    private List<TableColumnMeta> _columnsMeta;

    private List<TableColumnMeta> _keys = new LinkedList<>();

    private String saveOrUpdateSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append( NL ).append( "insert  into " ).append( holder.tableName() ).append( " (" );
        String cname = null;
        for ( TableColumnMeta col : _columnsMeta ) {
            cname = col.getColumn_name().toLowerCase();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            builder.append( NL ).append( "        " );
            builder.append( col.getColumn_name().toLowerCase() ).append( "," );
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        builder.append( NL ).append( "values (" );
        for ( TableColumnMeta col : _columnsMeta ) {
            cname = col.getColumn_name().toLowerCase();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            String colType = col.getType();
            String jdbcType = jdbcType( colType );
            builder.append( NL ).append( "        " );
            if ( cname.equals( holder.columnRegDtm() ) ) {
                builder.append( String.format( "%s,", CURRENT_TIMESTAMP ) );
            } else {
                builder.append( String.format( "#{%s,jdbcType=%s},", cname, jdbcType ) );
            }
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        builder.append( NL ).append( "on duplicate key " );
        builder.append( NL ).append( "update  " ).append( holder.tableName() );
        builder.append( NL ).append( "set" );
        int i = 0;
        for ( TableColumnMeta col : _columnsMeta ) {
            cname = col.getColumn_name().toLowerCase();
            if ( cname.equals( holder.columnRegDtm() ) || cname.equals( holder.getColumnCreator() ) ) {
                continue;
            }
            String colType = col.getType();
            String jdbcType = jdbcType( colType );
            if ( col.isPrimarykey() ) {
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
                       .append( String.format( "#{%s,jdbcType=%s},", cname, jdbcType ) );
            }
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        i = 0;
        for ( TableColumnMeta pk : _keys ) {
            cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            }
            ++i;
        } ;
        return builder.toString();
    }

    private String insertSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append( "insert  into " ).append( holder.tableName() ).append( " (" );
        String cname = null;
        for ( TableColumnMeta col : _columnsMeta ) {
            cname = col.getColumn_name().toLowerCase();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            builder.append( NL ).append( "        " );
            builder.append( col.getColumn_name().toLowerCase() ).append( "," );
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        builder.append( NL ).append( "values (" );
        for ( TableColumnMeta col : _columnsMeta ) {
            cname = col.getColumn_name().toLowerCase();
            if ( cname.equals( holder.columnUpdDtm() ) || cname.equals( holder.getColumnModifier() ) ) {
                continue;
            }
            String colType = col.getType();
            String jdbcType = jdbcType( colType );
            builder.append( NL ).append( "        " );
            if ( cname.equals( holder.columnRegDtm() ) ) {
                builder.append( String.format( "%s,", CURRENT_TIMESTAMP ) );
            } else {
                builder.append( String.format( "#{%s,jdbcType=%s},", cname, jdbcType ) );
            }
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( " )" );
        return builder.toString();
    }

    private String updateSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append( "update  " ).append( holder.tableName() );
        builder.append( NL ).append( "set" );
        int i = 0;
        for ( TableColumnMeta col : _columnsMeta ) {
            String cname = col.getColumn_name().toLowerCase();
            if ( cname.equals( holder.columnRegDtm() ) || cname.equals( holder.getColumnCreator() ) ) {
                continue;
            }
            String colType = col.getType();
            String jdbcType = jdbcType( colType );
            if ( col.isPrimarykey() ) {
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
                       .append( String.format( "#{%s,jdbcType=%s},", cname, jdbcType ) );
            }
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        i = 0;
        for ( TableColumnMeta pk : _keys ) {
            String cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    private String deleteSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append( "delete" );
        builder.append( NL ).append( "from    " ).append( holder.tableName() );
        List<TableColumnMeta> pkeys = new ArrayList<>();
        int i = 0;
        for ( TableColumnMeta pk : pkeys ) {
            String cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            }
            ++i;
        }
        i = 0;
        for ( TableColumnMeta pk : _keys ) {
            String cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    private String selectGetSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append( "select  " );
        int i = 0;
        for ( TableColumnMeta col : _columnsMeta ) {
            if ( i > 0 ) {
                builder.append( NL ).append( "        " );
            }
            builder.append( "a." );
            builder.append( col.getColumn_name().toLowerCase() ).append( "," );
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( NL ).append( "from    " ).append( holder.tableName() ).append( " a" );
        i = 0;
        for ( TableColumnMeta pk : _keys ) {
            String cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    private String selectListSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append( "select  " );
        int i = 0;
        for ( TableColumnMeta col : _columnsMeta ) {
            if ( i > 0 ) {
                builder.append( NL ).append( "        " );
            }
            builder.append( "a." );
            builder.append( col.getColumn_name().toLowerCase() ).append( "," );
            ++i;
        }
        builder.deleteCharAt( builder.length() - 1 );
        builder.append( NL ).append( "from    " ).append( holder.tableName() ).append( " a" );
        i = 0;
        for ( TableColumnMeta pk : _keys ) {
            String cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( "a." ).append( cname ).append( " = nvl(" )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) ).append( ", a." ).append( cname )
                       .append( " )" );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( "a." ).append( cname ).append( " = nvl(" )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) ).append( ", a." ).append( cname )
                       .append( " )" );
            }
            ++i;
        }
        return builder.toString();
    }

    private String selectKey() {
        StringBuilder builder = new StringBuilder();
        builder.append( "<selectKey keyProperty=" ).append( CM ).append( "existsYn" ).append( CM )
               .append( " resultType=" ).append( CM ).append( holder.abbrName() ).append( CM ).append( " order=" )
               .append( CM ).append( "BEFORE" ).append( CM ).append( "<![CDATA[" );
        builder.append( NL ).append( "select  case" );
        builder.append( NLBS10 ).append( "when count(1) > 0 then 1 else 0 end as existsYn" );
        builder.append( NL ).append( "from    " ).append( holder.tableName() ).append( " a" );
        int ii = 0;
        for ( TableColumnMeta pk : _keys ) {
            String cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( ii == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( cname ).append( " = " ).append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            }
            ++ii;
        }
        builder.append( NL ).append( "]]></selectKey>" );
        return builder.toString();
    }

    private String enableDisableSQL( boolean enabled ) {
        String enableColumnName = null;
        for ( TableColumnMeta col : _columnsMeta ) {
            String cname = col.getColumn_name().toLowerCase();
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
        for ( TableColumnMeta pk : _keys ) {
            String cname = pk.getColumn_name().toLowerCase();
            String colType = pk.getType();
            String jdbcType = jdbcType( colType );
            if ( i == 0 ) {
                builder.append( NL ).append( "where   " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            } else {
                builder.append( NL ).append( "and     " );
                builder.append( StringUtils.rightPad( cname, 18 ) ).append( " = " )
                       .append( String.format( "#{%s,jdbcType=%s}", cname, jdbcType ) );
            }
            ++i;
        }
        return builder.toString();
    }

    public String build()
        throws Exception {
        List<TableColumnMeta> _columnsMeta = getColumnsMeta();
        for ( TableColumnMeta col : _columnsMeta ) {
            if ( col.isPrimarykey() ) {
                _keys.add( col );
            }
        }
        holder.getContext().add( "mapper.parameterType", parameterType( _keys ) );
        holder.getContext().add( "mapper.selectKey", selectKey() );
        holder.getContext().add( "mapper.saveOrUpdate", saveOrUpdateSQL() );
        holder.getContext().add( "mapper.insert", insertSQL() );
        holder.getContext().add( "mapper.update", updateSQL() );
        holder.getContext().add( "mapper.delete", deleteSQL() );
        holder.getContext().add( "mapper.get", selectGetSQL() );
        holder.getContext().add( "mapper.query", selectListSQL() );
        holder.getContext().add( "mapper.enable", enableDisableSQL( true ) );
        holder.getContext().add( "mapper.disable", enableDisableSQL( false ) );
        Template t = holder.freemarkerConfigure().getTemplate( "builder.mybatis-sqlmap.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    public List<TableColumnMeta> getColumnsMeta() {
        this._columnsMeta = holder.columnsMeta();
        return _columnsMeta;
    }
}
