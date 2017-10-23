package com.tc.audioplayer.bussiness.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tc.audioplayer.Navigator;
import com.tc.audioplayer.base.BaseListFragment;
import com.tc.audioplayer.player.PlayerManager;
import com.tc.audioplayer.utils.StringUtils;
import com.tc.model.entity.ArtistEntity;
import com.tc.model.entity.SearchWrapper;
import com.tc.model.entity.SongEntity;

import java.util.ArrayList;

/**
 * Created by itcayman on 2017/10/23.
 */

public class SearchResultFragment extends BaseListFragment {
    public static final int SONG = 0;
    public static final int AUTHOR = 1;
    public static final int ARTIST = 2;
    private SearchResultAdapter adapter;

    public static SearchResultFragment newInstance(int type) {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setEnabled(false);
        adapter = new SearchResultAdapter(getContext());
        adapter.setOnItemClickListener((v, position) -> {
            switch (adapter.getListItemViewHolderType(position)) {
                case SearchResultAdapter.TYPE_SONG:
//                    PlayList playList = new PlayList();
//                    playList.addSong((SongEntity) adapter.getItem(position));
                    SongEntity entity = (SongEntity) adapter.getItem(position);
                    PlayerManager.getInstance().play(entity);
                    break;
                case SearchResultAdapter.TYPE_ARTIST:
                    ArtistEntity artist = (ArtistEntity) adapter.getItem(position);
                    artist.author = StringUtils.replaceEm(artist.author);
                    Navigator.toArtistDetailActivity(getContext(), artist.ting_uid, artist.artist_id,
                            artist.author, artist.avatar_middle, artist.country);
                    break;
                case SearchResultAdapter.TYPE_ALBUM:
                    SearchWrapper.ResultBean.AlbumInfoBean.AlbumListBean albumn = (SearchWrapper.ResultBean.AlbumInfoBean.AlbumListBean) adapter.getItem(position);
                    albumn.title = StringUtils.replaceEm(albumn.title);
                    albumn.author = StringUtils.replaceEm(albumn.author);
                    Navigator.toAlbumnDetailActivity(getContext(), albumn.album_id);
                    break;
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        adapter.setData((ArrayList) data);
    }
}
