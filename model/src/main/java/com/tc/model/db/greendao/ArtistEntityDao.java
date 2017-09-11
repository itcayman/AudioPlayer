package com.tc.model.db.greendao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.tc.model.entity.ArtistEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ARTIST_ENTITY".
*/
public class ArtistEntityDao extends AbstractDao<ArtistEntity, String> {

    public static final String TABLENAME = "ARTIST_ENTITY";

    /**
     * Properties of entity ArtistEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Artist_id = new Property(0, String.class, "artist_id", true, "ARTIST_ID");
        public final static Property Avatar_mini = new Property(1, String.class, "avatar_mini", false, "AVATAR_MINI");
        public final static Property Avatar_s300 = new Property(2, String.class, "avatar_s300", false, "AVATAR_S300");
        public final static Property Ting_uid = new Property(3, String.class, "ting_uid", false, "TING_UID");
        public final static Property Del_status = new Property(4, String.class, "del_status", false, "DEL_STATUS");
        public final static Property Avatar_s500 = new Property(5, String.class, "avatar_s500", false, "AVATAR_S500");
        public final static Property Artist_name = new Property(6, String.class, "artist_name", false, "ARTIST_NAME");
        public final static Property Avatar_small = new Property(7, String.class, "avatar_small", false, "AVATAR_SMALL");
        public final static Property Avatar_s180 = new Property(8, String.class, "avatar_s180", false, "AVATAR_S180");
        public final static Property Song_num = new Property(9, int.class, "song_num", false, "SONG_NUM");
        public final static Property Country = new Property(10, String.class, "country", false, "COUNTRY");
        public final static Property Avatar_middle = new Property(11, String.class, "avatar_middle", false, "AVATAR_MIDDLE");
        public final static Property Album_num = new Property(12, int.class, "album_num", false, "ALBUM_NUM");
        public final static Property Artist_desc = new Property(13, String.class, "artist_desc", false, "ARTIST_DESC");
        public final static Property Author = new Property(14, String.class, "author", false, "AUTHOR");
        public final static Property Artist_source = new Property(15, String.class, "artist_source", false, "ARTIST_SOURCE");
    };

    private Query<ArtistEntity> songInfoEntity_Artist_listQuery;

    public ArtistEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ArtistEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ARTIST_ENTITY\" (" + //
                "\"ARTIST_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: artist_id
                "\"AVATAR_MINI\" TEXT," + // 1: avatar_mini
                "\"AVATAR_S300\" TEXT," + // 2: avatar_s300
                "\"TING_UID\" TEXT," + // 3: ting_uid
                "\"DEL_STATUS\" TEXT," + // 4: del_status
                "\"AVATAR_S500\" TEXT," + // 5: avatar_s500
                "\"ARTIST_NAME\" TEXT," + // 6: artist_name
                "\"AVATAR_SMALL\" TEXT," + // 7: avatar_small
                "\"AVATAR_S180\" TEXT," + // 8: avatar_s180
                "\"SONG_NUM\" INTEGER NOT NULL ," + // 9: song_num
                "\"COUNTRY\" TEXT," + // 10: country
                "\"AVATAR_MIDDLE\" TEXT," + // 11: avatar_middle
                "\"ALBUM_NUM\" INTEGER NOT NULL ," + // 12: album_num
                "\"ARTIST_DESC\" TEXT," + // 13: artist_desc
                "\"AUTHOR\" TEXT," + // 14: author
                "\"ARTIST_SOURCE\" TEXT);"); // 15: artist_source
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ARTIST_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ArtistEntity entity) {
        stmt.clearBindings();
 
        String artist_id = entity.getArtist_id();
        if (artist_id != null) {
            stmt.bindString(1, artist_id);
        }
 
        String avatar_mini = entity.getAvatar_mini();
        if (avatar_mini != null) {
            stmt.bindString(2, avatar_mini);
        }
 
        String avatar_s300 = entity.getAvatar_s300();
        if (avatar_s300 != null) {
            stmt.bindString(3, avatar_s300);
        }
 
        String ting_uid = entity.getTing_uid();
        if (ting_uid != null) {
            stmt.bindString(4, ting_uid);
        }
 
        String del_status = entity.getDel_status();
        if (del_status != null) {
            stmt.bindString(5, del_status);
        }
 
        String avatar_s500 = entity.getAvatar_s500();
        if (avatar_s500 != null) {
            stmt.bindString(6, avatar_s500);
        }
 
        String artist_name = entity.getArtist_name();
        if (artist_name != null) {
            stmt.bindString(7, artist_name);
        }
 
        String avatar_small = entity.getAvatar_small();
        if (avatar_small != null) {
            stmt.bindString(8, avatar_small);
        }
 
        String avatar_s180 = entity.getAvatar_s180();
        if (avatar_s180 != null) {
            stmt.bindString(9, avatar_s180);
        }
        stmt.bindLong(10, entity.getSong_num());
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(11, country);
        }
 
        String avatar_middle = entity.getAvatar_middle();
        if (avatar_middle != null) {
            stmt.bindString(12, avatar_middle);
        }
        stmt.bindLong(13, entity.getAlbum_num());
 
        String artist_desc = entity.getArtist_desc();
        if (artist_desc != null) {
            stmt.bindString(14, artist_desc);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(15, author);
        }
 
        String artist_source = entity.getArtist_source();
        if (artist_source != null) {
            stmt.bindString(16, artist_source);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ArtistEntity entity) {
        stmt.clearBindings();
 
        String artist_id = entity.getArtist_id();
        if (artist_id != null) {
            stmt.bindString(1, artist_id);
        }
 
        String avatar_mini = entity.getAvatar_mini();
        if (avatar_mini != null) {
            stmt.bindString(2, avatar_mini);
        }
 
        String avatar_s300 = entity.getAvatar_s300();
        if (avatar_s300 != null) {
            stmt.bindString(3, avatar_s300);
        }
 
        String ting_uid = entity.getTing_uid();
        if (ting_uid != null) {
            stmt.bindString(4, ting_uid);
        }
 
        String del_status = entity.getDel_status();
        if (del_status != null) {
            stmt.bindString(5, del_status);
        }
 
        String avatar_s500 = entity.getAvatar_s500();
        if (avatar_s500 != null) {
            stmt.bindString(6, avatar_s500);
        }
 
        String artist_name = entity.getArtist_name();
        if (artist_name != null) {
            stmt.bindString(7, artist_name);
        }
 
        String avatar_small = entity.getAvatar_small();
        if (avatar_small != null) {
            stmt.bindString(8, avatar_small);
        }
 
        String avatar_s180 = entity.getAvatar_s180();
        if (avatar_s180 != null) {
            stmt.bindString(9, avatar_s180);
        }
        stmt.bindLong(10, entity.getSong_num());
 
        String country = entity.getCountry();
        if (country != null) {
            stmt.bindString(11, country);
        }
 
        String avatar_middle = entity.getAvatar_middle();
        if (avatar_middle != null) {
            stmt.bindString(12, avatar_middle);
        }
        stmt.bindLong(13, entity.getAlbum_num());
 
        String artist_desc = entity.getArtist_desc();
        if (artist_desc != null) {
            stmt.bindString(14, artist_desc);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(15, author);
        }
 
        String artist_source = entity.getArtist_source();
        if (artist_source != null) {
            stmt.bindString(16, artist_source);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public ArtistEntity readEntity(Cursor cursor, int offset) {
        ArtistEntity entity = new ArtistEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // artist_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // avatar_mini
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // avatar_s300
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // ting_uid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // del_status
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // avatar_s500
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // artist_name
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // avatar_small
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // avatar_s180
            cursor.getInt(offset + 9), // song_num
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // country
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // avatar_middle
            cursor.getInt(offset + 12), // album_num
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // artist_desc
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // author
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15) // artist_source
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ArtistEntity entity, int offset) {
        entity.setArtist_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAvatar_mini(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAvatar_s300(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTing_uid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDel_status(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAvatar_s500(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setArtist_name(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAvatar_small(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAvatar_s180(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSong_num(cursor.getInt(offset + 9));
        entity.setCountry(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setAvatar_middle(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setAlbum_num(cursor.getInt(offset + 12));
        entity.setArtist_desc(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setAuthor(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setArtist_source(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
     }
    
    @Override
    protected final String updateKeyAfterInsert(ArtistEntity entity, long rowId) {
        return entity.getArtist_id();
    }
    
    @Override
    public String getKey(ArtistEntity entity) {
        if(entity != null) {
            return entity.getArtist_id();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "artist_list" to-many relationship of SongInfoEntity. */
    public List<ArtistEntity> _querySongInfoEntity_Artist_list(String artist_id) {
        synchronized (this) {
            if (songInfoEntity_Artist_listQuery == null) {
                QueryBuilder<ArtistEntity> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Artist_id.eq(null));
                songInfoEntity_Artist_listQuery = queryBuilder.build();
            }
        }
        Query<ArtistEntity> query = songInfoEntity_Artist_listQuery.forCurrentThread();
        query.setParameter(0, artist_id);
        return query.list();
    }

}
