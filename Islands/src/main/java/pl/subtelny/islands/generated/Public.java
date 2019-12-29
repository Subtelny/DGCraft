/*
 * This file is generated by jOOQ.
 */
package pl.subtelny.islands.generated;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import pl.subtelny.islands.generated.tables.IslandMembers;
import pl.subtelny.islands.generated.tables.Islands;
import pl.subtelny.islands.generated.tables.SkyblockIslands;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -2133019541;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.island_members</code>.
     */
    public final IslandMembers ISLAND_MEMBERS = pl.subtelny.islands.generated.tables.IslandMembers.ISLAND_MEMBERS;

    /**
     * The table <code>public.islands</code>.
     */
    public final Islands ISLANDS = pl.subtelny.islands.generated.tables.Islands.ISLANDS;

    /**
     * The table <code>public.skyblock_islands</code>.
     */
    public final SkyblockIslands SKYBLOCK_ISLANDS = pl.subtelny.islands.generated.tables.SkyblockIslands.SKYBLOCK_ISLANDS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.ISLANDS_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            IslandMembers.ISLAND_MEMBERS,
            Islands.ISLANDS,
            SkyblockIslands.SKYBLOCK_ISLANDS);
    }
}
