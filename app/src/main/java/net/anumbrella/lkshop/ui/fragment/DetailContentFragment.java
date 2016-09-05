package net.anumbrella.lkshop.ui.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.utils.JUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.CommentProductAdapter;
import net.anumbrella.lkshop.db.DBManager;
import net.anumbrella.lkshop.model.CommentDataModel;
import net.anumbrella.lkshop.model.bean.CommentProductDataModel;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.model.bean.RecommendContentModel;
import net.anumbrella.lkshop.ui.activity.DetailContentActivity;
import net.anumbrella.lkshop.ui.activity.ProductPayDetailActivity;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * author：Anumbrella
 * Date：16/6/1 下午1:51
 */
public class DetailContentFragment extends Fragment {


    public static final String ARG_ITEM_INFO_RECOMMEND = "item_info_recommend";

    public static final String ARG_ITEM_INFO_LISTPRODUCT = "item_info_listproduct";

    private ListProductContentModel listProductContentModel;

    private RecommendContentModel recommendContentModel;

    private boolean isCollectFlag = false;

    private Context mcontext;

    public static CommentProductAdapter adapter;

    private CommentProductDataModel.ProductDetailData productDetailData;

    private CommentProductDataModel productDataModelData = null;

    private int pid, uid;


    @BindView(R.id.appBar)
    AppBarLayout appbar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.backdrop)
    SimpleDraweeView backdropImg;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.collect_tip)
    TextView collect_tip;

    @BindView(R.id.add_product_shopping)
    TextView add_product_shopping;

    @BindView(R.id.collect_icon)
    ImageView collect_icon;


    @BindView(R.id.collect_layout)
    LinearLayout collect_layout;


    @BindView(R.id.comment_content)
    EasyRecyclerView recyclerView;

    @BindView(R.id.detail_data_pay)
    TextView detail_data_pay;


    public static DetailContentFragment newInstance(ListProductContentModel listProductData) {
        DetailContentFragment fragment = new DetailContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailContentFragment.ARG_ITEM_INFO_LISTPRODUCT, listProductData);
        fragment.setArguments(args);
        return fragment;
    }


    public static DetailContentFragment newInstance(RecommendContentModel data) {
        DetailContentFragment fragment = new DetailContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(DetailContentFragment.ARG_ITEM_INFO_RECOMMEND, data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = getContext();
        uid = BaseUtils.readLocalUser(mcontext).getUid();
    }

    @Override
    public void onResume() {
        if (adapter != null) {
            setData();
            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailcontent, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        adapter = new CommentProductAdapter(mcontext);
        recyclerView.setErrorView(R.layout.view_net_comment_error);
        recyclerView.setAdapterWithProgress(adapter);
        setData();
        if (getArguments().containsKey(ARG_ITEM_INFO_RECOMMEND)) {
            recommendContentModel = getArguments().getParcelable(ARG_ITEM_INFO_RECOMMEND);
        } else if (getArguments().containsKey(ARG_ITEM_INFO_LISTPRODUCT)) {
            listProductContentModel = getArguments().getParcelable(ARG_ITEM_INFO_LISTPRODUCT);
        }
        setHasOptionsMenu(true);
        ((DetailContentActivity) getActivity()).setToolbar(toolbar);

        if (recommendContentModel != null) {

            if (!TextUtils.isEmpty(recommendContentModel.getImageUrl())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    backdropImg.setBackground(getResources().getDrawable(R.mipmap.lk_icon));
                }
            }

            pid = recommendContentModel.getPid();
            collapsingToolbar.setTitle(recommendContentModel.getTitle());
            productDataModelData = new CommentProductDataModel();
            productDetailData = productDataModelData.getProductDetailData();
            productDetailData.setPrice(recommendContentModel.getPrice());
            productDetailData.setProductName(recommendContentModel.getTitle());
            productDetailData.setImg(recommendContentModel.getImageUrl());
            productDetailData.setPhoneCarrieroperator(recommendContentModel.getCarrieroperator());
            productDetailData.setPhoneColor(recommendContentModel.getColor());
            productDetailData.setPhoneStorage(recommendContentModel.getStorage());

        } else {
            if (listProductContentModel != null) {
                if (!TextUtils.isEmpty(listProductContentModel.getImageUrl())) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        backdropImg.setBackground(getResources().getDrawable(R.mipmap.lk_icon));
                    }
                }
                pid = listProductContentModel.getPid();
                collapsingToolbar.setTitle(listProductContentModel.getTitle());
                productDataModelData = new CommentProductDataModel();
                productDetailData = productDataModelData.getProductDetailData();
                productDetailData.setPrice(listProductContentModel.getPrice());
                productDetailData.setProductName(listProductContentModel.getTitle());
                productDetailData.setImg(listProductContentModel.getImageUrl());
                productDetailData.setPhoneCarrieroperator(listProductContentModel.getCarrieroperator());
                productDetailData.setPhoneColor(listProductContentModel.getColor());
                productDetailData.setPhoneStorage(listProductContentModel.getStorage());
            }
        }

        upadateCollectState();
        upadteAddShoppingState();
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                TypedArray array = getActivity().getTheme().obtainStyledAttributes(new int[] {
                        android.R.attr.colorPrimary,
                });
                int color = array.getColor(0, 0xFF00FF);
                int red = (color & 0xff0000) >> 16;
                int green = (color & 0x00ff00) >> 8;
                int blue = (color & 0x0000ff);
                toolbar.setBackgroundColor(Color.argb((int) (percentage * 255), red, green, blue));
                array.recycle();

            }
        });
        return view;
    }

    public void setData() {
        CommentDataModel.getCommentData(new Subscriber<List<CommentProductDataModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "网络不给力", Toast.LENGTH_SHORT).show();
                if (adapter.getCount() == 0) {
                    recyclerView.showError();
                }
            }

            @Override
            public void onNext(List<CommentProductDataModel> commentProductDataModels) {
                if (adapter.getCount() > 0) {
                    adapter.clear();
                    adapter.addAll(setProductCommentData(commentProductDataModels));
                } else {
                    adapter.addAll(setProductCommentData(commentProductDataModels));
                }
            }
        });

    }

    private List<CommentProductDataModel> setProductCommentData(List<CommentProductDataModel> data) {
        List<CommentProductDataModel> list = new ArrayList<>();
        if (pid > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getPid() == pid) {
                    list.add(data.get(i));
                }
            }
        }
        if (list.size() == 0) {
            list.add(null);
        }
        list.add(0, productDataModelData);
        return list;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.collect_layout, R.id.add_product_shopping, R.id.detail_data_pay})
    public void click(View view) {
        if (BaseUtils.checkLogin(mcontext)) {
            switch (view.getId()) {
                case R.id.collect_layout:
                    if (isCollectFlag) {
                        collect_icon.setBackground(getResources().getDrawable(R.mipmap.collect));
                        collect_tip.setText("收藏");
                        isCollectFlag = false;
                        if (pid > 0 && uid > 0) {
                            DBManager.getManager(mcontext).deleteCollect(pid, uid);
                        }
                    } else {
                        collect_icon.setBackground(getResources().getDrawable(R.mipmap.collect_ok));
                        collect_tip.setText("已经收藏");
                        Toast.makeText(getContext(), "已经收藏", Toast.LENGTH_SHORT).show();
                        isCollectFlag = true;
                        if (pid > 0 && uid > 0) {
                            DBManager.getManager(mcontext).addCollect(pid, uid);
                        }
                    }
                    break;

                case R.id.add_product_shopping:
                    if (!checkAddShoppingState()) {
                        createDialog();
                    }
                    break;
                case R.id.detail_data_pay:
                    if (uid > 0) {
                        Intent intent = new Intent();
                        ArrayList<ListProductContentModel> arrayList = new ArrayList<>();
                        ListProductContentModel passOnData = new ListProductContentModel();
                        if (listProductContentModel != null) {
                            passOnData = listProductContentModel;
                            passOnData.setSum(1);
                            passOnData.setUid(uid);
                        } else if (recommendContentModel != null) {
                            passOnData.setPid(recommendContentModel.getPid());
                            passOnData.setUid(uid);
                            passOnData.setSum(1);
                            passOnData.setPrice(recommendContentModel.getPrice());
                            passOnData.setImageUrl(recommendContentModel.getImageUrl());
                            passOnData.setCarrieroperator(recommendContentModel.getCarrieroperator());
                            passOnData.setStorage(recommendContentModel.getStorage());
                            passOnData.setColor(recommendContentModel.getColor());
                            passOnData.setType(recommendContentModel.getType());
                            passOnData.setTitle(recommendContentModel.getTitle());

                        }
                        arrayList.add(passOnData);
                        intent.putParcelableArrayListExtra("data", arrayList);
                        intent.setClass(getContext(), ProductPayDetailActivity.class);
                        startActivity(intent);
                    }

                    break;
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void upadteAddShoppingState() {
        if (checkAddShoppingState()) {
            JUtils.Toast("已经加入购物车");
            add_product_shopping.setBackground(getResources().getDrawable(R.color.addShopping_bg));
            add_product_shopping.setTextColor(getResources().getColor(R.color.textColor_gray));
            add_product_shopping.setText("已加入购物车");
        }
    }

    public boolean checkAddShoppingState() {
        boolean isAddShopping = DBManager.getManager(mcontext).checkShopping(pid, uid);
        return isAddShopping;
    }


    private void createDialog() {
        final TextView dialog_product_sum;
        View view = LayoutInflater.from(mcontext).inflate(R.layout.product_detail_dialog_content, null);
        dialog_product_sum = ((TextView) view.findViewById(R.id.dialog_product_sum));
        if (productDetailData.getPhoneColor() != -1 && productDetailData.getPhoneCarrieroperator() != -1 && productDetailData.getPhoneStorage() != -1) {
            ((TextView) view.findViewById(R.id.dialog_phone_color)).setText(BaseUtils.transform("color", String.valueOf(productDetailData.getPhoneColor())));
            ((TextView) view.findViewById(R.id.dialog_phone_carrieroperator)).setText(BaseUtils.transform("carrieroperator", String.valueOf(productDetailData.getPhoneCarrieroperator())));
            ((TextView) view.findViewById(R.id.dialog_phone_stroage)).setText(BaseUtils.transform("storage", String.valueOf(productDetailData.getPhoneStorage())));
        } else {
            ((LinearLayout) view.findViewById(R.id.phone_detail_layout)).setVisibility(View.GONE);
        }
        if (productDetailData != null) {
            ((TextView) view.findViewById(R.id.dialog_price)).setText("￥ " + productDetailData.getPrice());
            ((TextView) view.findViewById(R.id.dialog_product_name)).setText(productDetailData.getProductName());
            ((SimpleDraweeView) view.findViewById(R.id.product_dialog_img)).setImageURI(Uri.parse(productDetailData.getImg()));
        }

        Holder holder = new ViewHolder(view);
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.dialog_close:
                        dialog.dismiss();
                        break;
                    case R.id.dialog_ok:
                        int sum = Integer.parseInt(dialog_product_sum.getText().toString());
                        if (uid > 0 && pid > 0 && sum > 0) {
                            DBManager.getManager(mcontext).addShopping(pid, uid, sum);
                            upadteAddShoppingState();
                        }
                        dialog.dismiss();
                        break;
                    case R.id.dialog_product_sum_add:
                        dialog_product_sum.setText(String.valueOf(Integer.parseInt(dialog_product_sum.getText().toString()) + 1));
                        break;
                    case R.id.dialog_product_sum_sub:
                        if (Integer.parseInt(dialog_product_sum.getText().toString()) > 1) {
                            dialog_product_sum.setText(String.valueOf(Integer.parseInt(dialog_product_sum.getText().toString()) - 1));
                        }
                        break;
                }
            }
        };


        DialogPlus dialogPlus = DialogPlus.newDialog(mcontext)
                .setContentHolder(holder)
                .setGravity(Gravity.BOTTOM)
                .setFooter(R.layout.product_detail_dialog_footer)
                .setCancelable(true)
                .setOnClickListener(clickListener)
                .create();
        dialogPlus.show();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void upadateCollectState() {
        boolean isCollect = DBManager.getManager(mcontext).checkCollect(pid, uid);
        if (isCollect) {
            isCollectFlag = true;
            collect_icon.setBackground(getResources().getDrawable(R.mipmap.collect_ok));
            collect_tip.setText("已经收藏");
        } else {
            collect_icon.setBackground(getResources().getDrawable(R.mipmap.collect));
            collect_tip.setText("收藏");
        }
    }
}
