package net.anumbrella.lkshop.ui.fragment;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.ui.activity.RepairContentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：Anumbrella
 * Date：16/6/4 下午4:35
 */
public class RepairScreenTypeFragment extends Fragment {

    public static final String ARG_ITEM_INFO_SCREEN_TYPE = "item_info_type";

    private String type = null;

    @BindView(R.id.repair_screen_type)
    SimpleDraweeView repair_screen_type;


    @BindView(R.id.repair_screen_toolbar)
    Toolbar toolbar;

    public static RepairScreenTypeFragment newInstance(String type) {
        RepairScreenTypeFragment fragment = new RepairScreenTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_INFO_SCREEN_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair_screen_type, container, false);
        ButterKnife.bind(this, view);
        if (getArguments().containsKey(ARG_ITEM_INFO_SCREEN_TYPE)) {
            type = getArguments().getString(ARG_ITEM_INFO_SCREEN_TYPE);
        }
        setHasOptionsMenu(true);
        ((RepairContentActivity) getActivity()).setToolbar(toolbar);
        for (int i = 0; i < Config.repairScreenPhoneTypes.length - 1; i++) {
            if (type.equals(Config.repairScreenPhoneTypes[i])) {
                repair_screen_type.setBackground(getResources().getDrawable(Config.repairScreenTypePhoneImgs[i]));
            }
        }
        toolbar.setTitle(type);
        ((RepairContentActivity) getActivity()).setToolbar(toolbar);
        return view;
    }


}