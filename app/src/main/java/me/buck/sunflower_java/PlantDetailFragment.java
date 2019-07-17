package me.buck.sunflower_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.buck.sunflower_java.util.InjectorUtils;
import me.buck.sunflower_java.viewmodels.PlantDetailViewModel;

/**
 * Created by buck on 2019-06-18
 */
public class PlantDetailFragment extends Fragment {

    @BindView(R.id.detail_image)            ImageView               mDetailImage;
    @BindView(R.id.toolbar_layout)          CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.appbar)                  AppBarLayout            mAppbar;
    @BindView(R.id.plant_name)              TextView                mPlantName;
    @BindView(R.id.plant_watering)          TextView                mPlantWatering;
    @BindView(R.id.plant_description)       TextView                mPlantDescription;
    @BindView(R.id.plant_detail_scrollview) NestedScrollView        mPlantDetailScrollview;
    @BindView(R.id.fab)                     FloatingActionButton    mFab;


    private PlantDetailFragmentArgs mArgs;
    private String                  mShareText;
    private PlantDetailViewModel    mViewModel =
            InjectorUtils.providePlantDetailViewModelFactory(requireActivity(), mArgs.getPlantId()).create(PlantDetailViewModel.class);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = PlantDetailFragmentArgs.fromBundle(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.plant_detail_fragment, container, false);
        Unbinder bind = ButterKnife.bind(inflate);

        mViewModel = InjectorUtils.providePlantDetailViewModelFactory(requireActivity(), mArgs.getPlantId()).create(PlantDetailViewModel.class);

        mViewModel.getPlant().observe(this, plant -> {
            mToolbarLayout.setTitle(plant.getName());
            Glide.with(getActivity()).load(plant.getImageUrl()).into(mDetailImage);
            mPlantName.setText(plant.getName());
            mPlantWatering.setText(plant.getWateringInterval());
            mPlantDescription.setText(plant.getDescription());

            if (plant == null) {
                mShareText = "";
            } else {
                mShareText = getString(R.string.share_text_plant, plant.getName());
            }
        });

        mFab.setOnClickListener(v -> {
            mViewModel.addPlantToGarden();
            Snackbar.make(v, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show();
        });
        setHasOptionsMenu(true);
        return inflate;
    }


}
