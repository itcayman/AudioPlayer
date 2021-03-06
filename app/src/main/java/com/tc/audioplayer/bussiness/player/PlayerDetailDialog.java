package com.tc.audioplayer.bussiness.player;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tc.audioplayer.R;
import com.tc.audioplayer.bussiness.fav.FavHelper;
import com.tc.audioplayer.player.PlayerManager;
import com.tc.audioplayer.player.SimplePlayerListener;
import com.tc.audioplayer.utils.AdMobUtils;
import com.tc.audioplayer.utils.AudioDurationUtil;
import com.tc.audioplayer.utils.FileUtil;
import com.tc.audioplayer.widget.lrcview.LrcView;
import com.tc.base.utils.DeviceUtils;
import com.tc.base.utils.TLogger;
import com.tc.model.entity.PlayList;
import com.tc.model.entity.SongEntity;
import com.tc.model.usecase.OnlineCase;

import java.io.File;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.tc.audioplayer.utils.FileUtil.getLrcFile;


/**
 * Created by itcayman on 2017/8/20.
 */

public class PlayerDetailDialog extends DialogFragment {
    private static final String TAG = PlayerDetailDialog.class.getSimpleName();
    private OnlineCase onlineCase;
    private ImageView ivClose;
    private TextView tvName;
    private TextView tvAuthor;
    private ImageView ivAvatar;
    private ImageView ivPlayMode;
    private ImageView ivPlayPause;
    private ImageView ivPrev;
    private ImageView ivNext;
    private ImageView ivFav;
    private TextView tvCurrentDuration;
    private TextView tvTotalDuration;
    private SeekBar seekBar;
    private LrcView lrcView;
    private FrameLayout adView;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private DetailPlayerListener playerListener;
    private SeekbarListener seekbarListener;
    private boolean seekbarStarting;
    private String lrclink;
    public boolean isShown;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onlineCase = new OnlineCase();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ActionSheet);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dialog_player, container);
        ivClose = (ImageView) view.findViewById(R.id.iv_arrow_down);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvAuthor = (TextView) view.findViewById(R.id.tv_author);
        ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
        ivPlayMode = (ImageView) view.findViewById(R.id.iv_play_mode);
        ivPlayPause = (ImageView) view.findViewById(R.id.iv_play_pause);
        ivPrev = (ImageView) view.findViewById(R.id.iv_prev);
        ivNext = (ImageView) view.findViewById(R.id.iv_next);
        ivFav = (ImageView) view.findViewById(R.id.iv_fav);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        lrcView = (LrcView) view.findViewById(R.id.lrc);
        tvCurrentDuration = (TextView) view.findViewById(R.id.tv_current_duration);
        tvTotalDuration = (TextView) view.findViewById(R.id.tv_total_duration);
        adView = (FrameLayout) view.findViewById(R.id.ad_play);

        seekbarListener = new SeekbarListener();

        ivPlayMode.setOnClickListener(onClickListener);
        ivPlayPause.setOnClickListener(onClickListener);
        ivPrev.setOnClickListener(onClickListener);
        ivNext.setOnClickListener(onClickListener);
        ivFav.setOnClickListener(onClickListener);
        ivClose.setOnClickListener(onClickListener);
        seekBar.setOnSeekBarChangeListener(seekbarListener);

        playerListener = new DetailPlayerListener();
        PlayerManager.getInstance().addPlayListener(playerListener);
        initPlayerStatus();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isShown = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        initWindow();
    }

    private void initWindow() {
        android.view.WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = (int) (DeviceUtils.getScreenWidthPx(getContext()) * 0.95);
        p.height = (int) (DeviceUtils.getScreenHeightPx(getContext()) * 0.95);
        p.gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes(p);
    }

    public void setLrclink(String lrclink, long time) {
        this.lrclink = lrclink;
        if (onlineCase == null) {
            return;
        } else {
            showLrc(lrclink, time);
        }
    }

    public void updateLrcTime(long time) {
        if (lrcView == null) {
            return;
        }
        lrcView.updateTime(time);
    }

    private void showLrc(String lrclink, long time) {
        if (lrclink == null) {
            return;
        }
        if (TextUtils.isEmpty(lrclink)) {
            showLrcAd();
            return;
        }
        File file = getLrcFile(lrclink);
        if (file.exists()) {
            adView.setVisibility(View.GONE);
            lrcView.loadLrc(file, time);
        } else {
            loadServerLrc(lrclink);
        }
    }

    private void showLrcAd() {
        lrcView.loadLrc("");
        if (adView.getVisibility() == View.GONE) {
            AdMobUtils.showNativeContentAd(getContext(), adView);
        }
    }

    /**
     * 记载服务器lrc文件
     */
    private void loadServerLrc(String lrclink) {
        Action1<Boolean> onNext = (saveLrcSuccess) -> {
            if (saveLrcSuccess && !TextUtils.isEmpty(lrclink)) {
                File file = getLrcFile(lrclink);
                if (file.exists()) {
                    adView.setVisibility(View.GONE);
                    lrcView.loadLrc(file);
                } else {
                    showLrcAd();
                }
            }
        };
        Action1<Throwable> onError = (throwable) -> {
            if (isAdded()) {
                showLrcAd();
            }
        };
        File lrcFile = FileUtil.getLrcFile(lrclink);
        onlineCase.getMusicFile(lrclink)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map((responseBody) -> {
                    return FileUtil.writeResponseBodyToDisk(responseBody, lrcFile);
                })
                .subscribe(onNext, onError);
    }


    /**
     * 初始化播放器当前状态
     */
    private void initPlayerStatus() {
        PlayList playList = PlayerManager.getInstance().getPlayList();
        SongEntity song = playList.getCurrentSong();
        if (song == null) {
            return;
        }
        //处理进度UI
        int progress = PlayerManager.getInstance().getProgress();
        int duration = progress * song.file_duration / 100;
        String currentDuration = AudioDurationUtil.secondsToString(duration);
        String totalDuration = AudioDurationUtil.secondsToString(song.file_duration);
        tvCurrentDuration.setText(currentDuration);
        tvTotalDuration.setText(totalDuration);
        seekBar.setProgress(progress);
        //处理播放状态UI
        if (PlayerManager.getInstance().isPlaying()) {
            ivPlayPause.setImageResource(R.drawable.selector_play);
        } else {
            ivPlayPause.setImageResource(R.drawable.selector_pause);
        }
        //处理歌曲信息UI
        boolean isFav = FavHelper.isFav(song);
        ivFav.setSelected(isFav);
        tvName.setText(song.title);
        tvAuthor.setText(song.author);
        setLrclink(song.lrclink, duration * 1000);
        int playMode = PlayerManager.getInstance().getPlayMode();
        updatePlayModeUI(playMode);
        String pic = song.getPic_small();
        if (TextUtils.isEmpty(pic)) {
            Glide.with(this)
                    .load(R.drawable.default_cover)
                    .asBitmap()
                    .transform(new RoundedCornersTransformation(getContext(), 20, 0))
                    .into(ivAvatar);
        } else {
            int index = pic.indexOf(",w_");
            if (index > 0) {
                pic = pic.substring(0, index) + ",w_300,h_267";
            }
            Glide.with(this)
                    .load(pic)
                    .asBitmap()
                    .transform(new RoundedCornersTransformation(getContext(), 20, 0))
                    .into(ivAvatar);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroyView() {
        PlayerManager.getInstance().removePlayListener(playerListener);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isShown = false;
    }

    private void updatePlayModeUI(int playMode) {
        switch (playMode) {
            case PlayList.SINGLE:
                ivPlayMode.setImageResource(R.drawable.selector_mode_single);
                break;
            case PlayList.LOOP:
                ivPlayMode.setImageResource(R.drawable.selector_mode_loop);
                break;
            case PlayList.SHUFFLE:
                ivPlayMode.setImageResource(R.drawable.selector_mode_random);
                break;
            default:
        }
    }

    private View.OnClickListener onClickListener = (view) -> {
        switch (view.getId()) {
            case R.id.iv_play_mode:
                int playMode = PlayerManager.getInstance().switchNextMode();
                updatePlayModeUI(playMode);
                break;
            case R.id.iv_play_pause:
                PlayerManager.getInstance().playPause();
                break;
            case R.id.iv_prev:
                lrcView.reset();
                seekBar.setProgress(0);
                PlayerManager.getInstance().playPrev();
                break;
            case R.id.iv_next:
                lrcView.reset();
                seekBar.setProgress(0);
                PlayerManager.getInstance().playNext();
                break;
            case R.id.iv_fav:
                SongEntity songEntity = PlayerManager.getInstance().getPlayList().getCurrentSong();
                boolean isFav = view.isSelected();
                if (isFav) {
                    FavHelper.unfavSong(songEntity);
                    view.setSelected(false);
                } else {
                    FavHelper.favSong(getContext(), songEntity);
                    view.setSelected(true);
                }
                break;
            case R.id.iv_arrow_down:
                dismiss();
                break;
            default:
        }
    };

    private class SeekbarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            TLogger.d(TAG, "onProgressChanged: progress=" + progress);
            if (seekbarStarting) {
                PlayList playList = PlayerManager.getInstance().getPlayList();
                SongEntity song = playList.getCurrentSong();
                int totalDuration = song.file_duration;
                tvCurrentDuration.setText(AudioDurationUtil.secondsToString(progress * totalDuration / 100));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            TLogger.d(TAG, "onStartTrackingTouch: progress=" + seekBar.getProgress());
            seekbarStarting = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            TLogger.d(TAG, "onStopTrackingTouch: progress=" + seekBar.getProgress());
            PlayerManager.getInstance().seekTo(seekBar.getProgress());
            SongEntity song = PlayerManager.getInstance().getPlayList().getCurrentSong();
            long time = seekBar.getProgress() * 10 * song.file_duration;
            lrcView.onDrag(time);
            seekbarStarting = false;
        }
    }

    private class DetailPlayerListener extends SimplePlayerListener {
        @Override
        public void onPreparingStart() {
            initPlayerStatus();
            TLogger.d(TAG, "onPreparingStart");
        }

        @Override
        public void onBufferingUpdate(int percent) {
            TLogger.d(TAG, "onBufferingUpdate: percent=" + percent);
            seekBar.setSecondaryProgress(percent);
            int seektoDuration = PlayerManager.getInstance().getSeektoDuration();
            if (percent >= 100 && seektoDuration > 0) {
                PlayList playList = PlayerManager.getInstance().getPlayList();
                SongEntity song = playList.getCurrentSong();
                int totalDuration = song.file_duration;
                if (totalDuration == 0) {
                    return;
                }
                int progress = seektoDuration * 100 / totalDuration;
                PlayerManager.getInstance().seekTo(progress);
            }
        }

        @Override
        public void onPlay() {
            TLogger.d(TAG, "onPlay");
            ivPlayPause.setImageResource(R.drawable.selector_play);
            initPlayerStatus();
        }

        @Override
        public void onPause() {
            TLogger.d(TAG, "onPause");
            ivPlayPause.setImageResource(R.drawable.selector_pause);
        }

        @Override
        public void onResume() {
            TLogger.d(TAG, "onResume");
            ivPlayPause.setImageResource(R.drawable.selector_play);
        }

        @Override
        public void onProgress(boolean isPlaying, int progress, int duration, int secondProgress) {
            if (seekbarStarting || (progress == 0 && duration == 0 && secondProgress == 0)) {
                return;
            }
            TLogger.d(TAG, "onProgress: progress=" + progress + " duration=" + duration
                    + " secondProgress=" + secondProgress);
            seekBar.setProgress(progress);
            String totalDuration = AudioDurationUtil.secondsToString(duration);
            tvCurrentDuration.setText(totalDuration);
            updateLrcTime(duration * 1000);
        }

        @Override
        public void onCompletion() {
            TLogger.d(TAG, "onCompletion");
            ivPlayPause.setImageResource(R.drawable.selector_pause);
        }

        @Override
        public void onError(int errorCode) {
            super.onError(errorCode);
        }
    }
}
