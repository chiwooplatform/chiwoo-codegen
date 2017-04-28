package org.chiwooplatform.gen.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.Attribute;
import org.chiwooplatform.gen.model.TableColumnMeta;

public abstract class AbstractDataBuilder
    extends AbstractBuilder {

    protected AbstractDataBuilder( ValueHolder holder ) {
        super( holder );
    }

    protected final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

    protected String jdbcType( String colType ) {
        String jdbcType = null;
        switch ( colType ) {
            case "Date":
            case "DATETIME":
                jdbcType = "TIMESTAMP";
                break;
            case "String":
            case "VARCHAR":
            case "VARCHAR2":
                jdbcType = "VARCHAR";
                break;
            case "NUMBER":
            case "Float":
            case "Integer":
            case "Long":
            case "Double":
            case "java.math.BigDecimal":
                jdbcType = "INTEGER";
                break;
            default:
                jdbcType = "VARCHAR";
                break;
        }
        return jdbcType;
    }

    protected String parameterType( List<Attribute>   primaryKeys ) {
        if ( primaryKeys.size() < 1 ) {
            return "string";
        } else if ( primaryKeys.size() > 1 ) {
            return "map";
        }
        Attribute attr = primaryKeys.get( 0 );
        String javaType = attr.getType();
        switch ( javaType ) {
            case "Integer":
                return "integer";
            default:
                return "string";
        }
    }

    private boolean isUserColumn( final String column ) {
        return column.equals( holder.getColumnCreator() ) || column.equals( holder.getColumnModifier() );
    }

    protected String defaultValue( String javaType, TableColumnMeta col ) {
        final String column = col.getColumn_name().toLowerCase();
        String defaultVal = col.getDefaultValue();
        if ( StringUtils.isEmpty( defaultVal ) ) {
            switch ( javaType ) {
                case "String":
                    defaultVal = column;
                    break;
                case "Date":
                    defaultVal = "new Date()";
                    break;
                case "java.sql.Timestamp":
                    defaultVal = "new Timestamp( System.currentTimeMillis() )";
                    break;
                case "Float":
                case "Integer":
                case "Long":
                case "Double":
                case "java.math.BigDecimal":
                    defaultVal = "-999";
                    break;
                default:
                    defaultVal = "default_value";
                    break;
            }
            if ( column.equals( "use_yn" ) || column.endsWith( "_yn" ) ) {
                defaultVal = "true";
            } else if ( column.equals( "ordno" ) ) {
                defaultVal = "1";
            } else if ( isUserColumn( column ) ) {
                defaultVal = "userid()";
            }
        } else {
            if ( column.equals( "use_yn" ) || column.endsWith( "_yn" ) ) {
                defaultVal = "true";
            }
        }
        return defaultVal;
    }

    protected String changedValue( String javaType, String defaultValue ) {
        String value = null;
        switch ( javaType ) {
            case "String":
                value = "changed_" + defaultValue;
                break;
            case "Date":
                value = "new Date( System.currentTimeMillis() )";
                break;
            case "java.sql.Timestamp":
                value = "new Timestamp( System.currentTimeMillis() )";
                break;
            case "Float":
            case "Integer":
            case "Long":
            case "Double":
            case "java.math.BigDecimal":
                value = "-777";
                break;
            default:
                value = "changed_value";
                break;
        }
        return value;
    }

    protected String model( List<TableColumnMeta> columnsMeta ) {
        String domainName = holder.getDomainName();
        List<TableColumnMeta> keys = primaryColumns( columnsMeta );
        StringBuilder builder = new StringBuilder();
        builder.append( "    " ).append( String.format( "protected %s model()", holder.getDomainName() ) );
        builder.append( NLBS4 ).append( "{" );
        builder.append( NLBS4 ).append( String.format( "    %s model = new %s();", domainName, domainName ) );
        for ( TableColumnMeta col : columnsMeta ) {
            final String column = col.getColumn_name().toLowerCase();
            Attribute attr = attribute( col );
            logger.debug( "attr: {}", attr );
            if ( isPrimaryKey( col, keys ) ) {
                builder.append( NLBS8 )
                       .append( String.format( "model.set%s( %s );", attr.getName(), "this." + attr.getNm() ) );
            } else {
                if ( isUserColumn( column ) ) {
                    builder.append( NLBS8 )
                           .append( String.format( "model.set%s( %s );", attr.getName(), attr.getValue() ) );
                } else {
                    builder.append( NLBS8 )
                           .append( String.format( "model.set%s( %s );", attr.getName(), attr.getVal() ) );
                }
            }
        }
        builder.append( NLBS4 ).append( "    return model;" );
        builder.append( NLBS4 ).append( "}" );
        return builder.toString();
    }

    private boolean isPrimaryKey( TableColumnMeta col, List<TableColumnMeta> primaryColumns ) {
        for ( TableColumnMeta pkey : primaryColumns ) {
            if ( col.getColumn_name().equals( pkey.getColumn_name() ) ) {
                return true;
            }
        }
        return false;
    }

    private TableColumnMeta assertionColumn( List<TableColumnMeta> columnsMeta ) {
        List<TableColumnMeta> primaryColumns = primaryColumns( columnsMeta );
        TableColumnMeta assertionCol = null;
        for ( TableColumnMeta col : columnsMeta ) {
            if ( isPrimaryKey( col, primaryColumns ) ) {
                // skip
            } else if ( "VARCHAR".equals( col.getType() ) ) {
                assertionCol = col;
                break;
            }
        }
        if ( assertionCol == null ) {
            for ( TableColumnMeta col : columnsMeta ) {
                if ( col.isPrimarykey() || isPrimaryKey( col, primaryColumns ) ) {
                    // skip
                } else {
                    assertionCol = col;
                    break;
                }
            }
        }
        return assertionCol;
    }

    private Attribute attribute( TableColumnMeta col ) {
        String javaType = javaType( col );
        String defaultValue = defaultValue( javaType, col );
        String nm = inflector.lowerCamelCase( col.getColumn_name(), '_' );
        String name = upperName( nm );
        Attribute attr = new Attribute();
        attr.setNm( nm );
        attr.setName( name );
        attr.setType( javaType );
        attr.setValue( defaultValue );
        attr.setAssertValue( changedValue( javaType, defaultValue ) );
        attr.setColumn( col.getColumn_name().toLowerCase() );
        attr.setNullable( col.isNullable() );
        attr.setPrimaryKey( col.isPrimarykey() );
        attr.setComment( col.getComments() );
        return attr;
    }

    protected List<TableColumnMeta> primaryColumns( List<TableColumnMeta> columnsMeta ) {
        List<TableColumnMeta> keys = new ArrayList<>();
        for ( TableColumnMeta col : columnsMeta ) {
            if ( col.isPrimarykey() ) {
                keys.add( col );
            }
        }
        if ( keys.size() < 1 ) {
            for ( TableColumnMeta col : columnsMeta ) {
                keys.add( col );
                break;
            }
        }
        return keys;
    }

    protected List<Attribute> primaryAttrs( List<TableColumnMeta> keys ) {
        List<Attribute> attrs = new ArrayList<>();
        StringBuilder b = new StringBuilder();
        for ( TableColumnMeta col : keys ) {
            Attribute attr = attribute( col );
            attrs.add( attr );
            b.append( ", " ).append( attr.getNm() );
        }
        holder.getContext().add( "testRest.bindAttrs", b.toString() );
        return attrs;
    }

    protected List<Attribute> normalAttrs( List<TableColumnMeta> cols ) {
        List<Attribute> attrs = new ArrayList<>();
        for ( TableColumnMeta col : cols ) {
            Attribute attr = attribute( col );
            attrs.add( attr );
        }
        return attrs;
    }

    protected Attribute assertionAttr( List<TableColumnMeta> columnsMeta ) {
        TableColumnMeta col = assertionColumn( columnsMeta );
        Attribute attr = attribute( col );
        return attr;
    }
}
