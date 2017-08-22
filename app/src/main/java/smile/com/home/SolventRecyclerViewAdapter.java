package smile.com.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;

import static smile.com.home.Constants.DESCRIPTION;
import static smile.com.home.Constants.SELECTED_ITEM;
import static smile.com.home.Constants.SELECTED_POSITION;
import static smile.com.home.Constants.TITLE;

public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventViewHolders> {
    // A menu item view type.
    private static final int MENU_ITEM_VIEW_TYPE = 0;

    // The Native Express ad view type.
    private static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;

    private static final int ITEMS_PER_AD = 5;


    private List<itemData> itemList;
    private Context context;
    public static int[][] gradientColors = new int[][]{{0xff00d2ff, 0xff3a7bd5}, {0xffff7f3f, 0xffff3e5e}, {0xff43e1a8, 0xff219393}, {0xffc96dd8, 0xff3023ae}, {0xfff4d451, 0xfff09819},
            {0xfffc76b7, 0xff920260}, {0xff928dab, 0xff1f1c2c}};
    private ArrayList<String> imageList = new ArrayList<>();

    public SolventRecyclerViewAdapter(Context context, List<itemData> itemList) {
        this.itemList = itemList;
        this.context = context;
        AppUtil.prepareImageList(imageList);
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView);
        return rcv;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return MENU_ITEM_VIEW_TYPE;
        return (position % ITEMS_PER_AD == 0 && AppUtil.getInstance().isNetworkAvailable()) ? NATIVE_EXPRESS_AD_VIEW_TYPE
                : MENU_ITEM_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
                showItem(holder, position);
                break;

            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                showItem(holder, position);
                showAdView(holder);
                break;
            default:
                break;

        }
    }

    private void showItem(SolventViewHolders holder, int position) {
        holder.adCardView.setVisibility(View.GONE);
        int colorIndex = position % gradientColors.length;
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                gradientColors[colorIndex]);
        float radiusValue = Application.getInstance().convertDpToPixel(4, context);
        float[] radius = {radiusValue, radiusValue, radiusValue, radiusValue, 0f, 0f, 0f, 0f};
        gd.setCornerRadii(radius);
        holder.item_layout.setBackgroundDrawable(gd);

        holder.countryName.setText(itemList.get(position).getItemText());
        final int item_position = position;
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailActivity(item_position);
            }
        });
        if(itemList.get(position).getImageUrl() == null){
            AppUtil.setImage(context,holder.countryPhoto,AppUtil.getRandomImageForList(imageList));
        }
    }

    /**
     * This method updates the native ad view in the Adapter.
     *
     * @param holder
     */
    private void showAdView(final SolventViewHolders holder) {
        holder.adLoader.setVisibility(View.VISIBLE);
        holder.adCardView.setVisibility(View.VISIBLE);
        holder.adView.loadAd(new AdRequest.Builder().addTestDevice(AdUtil.TEST_DEVICE_ID).build());
        holder.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                holder.adView.setVisibility(View.VISIBLE);
                holder.adLoader.setVisibility(View.GONE);
            }
        });
    }

    private void openDetailActivity(int item_position) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        Description description;
        if (itemList.get(item_position).getDescription() != null) {
            description = itemList.get(item_position).getDescription();
            intent.putExtra(DESCRIPTION, description);
        }
        intent.putExtra(TITLE, itemList.get(item_position).getItemText());
        intent.putExtra(SELECTED_POSITION, item_position);
        intent.putExtra(SELECTED_ITEM, itemList.get(item_position));
        context.startActivity(intent);
        AppUtil.getInstance().logInFabric("open_detail_page_ for",itemList.get(item_position).getItemText());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
