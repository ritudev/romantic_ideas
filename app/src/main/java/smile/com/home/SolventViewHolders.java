package smile.com.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.NativeExpressAdView;

public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final CardView adCardView;
    public final CardView itemCardView;
    public final ImageView adLoader;
    public TextView countryName;
    public ImageView countryPhoto;
    public RelativeLayout item_layout;
    public NativeExpressAdView adView;

    public SolventViewHolders(View itemView) {
        super(itemView);
        countryName = (TextView) itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.item_image);
        adLoader = (ImageView) itemView.findViewById(R.id.item_loader);
        item_layout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
        adView = (NativeExpressAdView) itemView.findViewById(R.id.adView_native_listing);
        adCardView = (CardView) itemView.findViewById(R.id.ad_card_view);
        itemCardView = (CardView) itemView.findViewById(R.id.card_view);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        //view.setBackgroundColor(view.getContext().getColor(R.color.selected_color));
    }
}
