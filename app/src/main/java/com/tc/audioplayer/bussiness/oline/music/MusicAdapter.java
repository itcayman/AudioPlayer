package com.tc.audioplayer.bussiness.oline.music;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tc.audioplayer.AudioApplication;
import com.tc.audioplayer.R;
import com.tc.audioplayer.event.CollectEvent;
import com.tc.librecyclerview.adapter.HeaderFooterAdapter;
import com.tc.librecyclerview.adapter.RecyclerViewHolder;
import com.tc.model.db.DBManager;
import com.tc.model.db.greendao.CollectSongDao;
import com.tc.model.db.greendao.SongEntityDao;
import com.tc.model.entity.CollectSong;
import com.tc.model.entity.SongEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by tianchao on 2017/8/5.
 */

public class MusicAdapter extends HeaderFooterAdapter<SongEntity> {

    public MusicAdapter(Context context) {
        super(context);
    }

    @Override
    public View inflaterListItemView(ViewGroup parent, int viewType) {
        return mInflater.inflate(R.layout.item_music, parent, false);
    }

    @Override
    public void bindListItemData(RecyclerViewHolder holder, int dataIndex) {
        SongEntity item = getItem(dataIndex);
        holder.setText(R.id.tv_title, item.title);
        holder.setText(R.id.tv_author, item.author + "-" + item.album_title);
        Glide.with(mContext).load(item.pic_small).into((ImageView) holder.getView(R.id.iv_avatar));
        holder.getView(R.id.iv_more).setTag(item);
        holder.getView(R.id.iv_more).setOnClickListener(moreClickListener);
    }

    private View.OnClickListener moreClickListener = (view) -> {
        SongEntity item = (SongEntity) view.getTag();
        CollectSong collectSong = new CollectSong();
        collectSong.setSongid(item.getSong_id());
        collectSong.setSong(item);
        CollectSongDao dao = DBManager.getInstance(AudioApplication.getInstance())
                .getDaoSession().getCollectSongDao();
        SongEntityDao songEntityDao = DBManager.getInstance(AudioApplication.getInstance())
                .getDaoSession().getSongEntityDao();
        if (dao.load(collectSong.getId()) == null) {
            songEntityDao.insert(item);
            dao.insert(collectSong);
            EventBus.getDefault().post(new CollectEvent(collectSong));
            Toast.makeText(mContext, "收藏成功!", Toast.LENGTH_SHORT).show();
        }
    };
}
