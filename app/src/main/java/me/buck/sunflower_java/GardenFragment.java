package me.buck.sunflower_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.buck.sunflower_java.adapter.GardenPlantingAdapter;
import me.buck.sunflower_java.util.InjectorUtils;
import me.buck.sunflower_java.util.ListUtils;
import me.buck.sunflower_java.viewmodels.GardenPlantingListViewModel;

/**
 * Created by buck on 2019-06-18
 */
public class GardenFragment extends Fragment {

    @BindView(R.id.garden_list)  RecyclerView          mGardenList;
    @BindView(R.id.empty_garden) TextView              mEmptyGarden;
    private                      GardenPlantingAdapter mAdapter;

    private GardenPlantingListViewModel mViewModel =
            InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext()).create(GardenPlantingListViewModel.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.garden_fragment, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new GardenPlantingAdapter();
        mGardenList.setAdapter(mAdapter);

        subscribeUi();

        return view;
    }


    private void subscribeUi() {
        mViewModel.getGardenPlantings().observe(getViewLifecycleOwner(), plantings -> {
            boolean empty = ListUtils.isNullOrEmpty(plantings);
            mEmptyGarden.setVisibility(empty ? View.GONE : View.VISIBLE);
        });

        mViewModel.getPlantAndGardenPlantings().observe(getViewLifecycleOwner(), result -> {
            boolean empty = ListUtils.isNullOrEmpty(result);
            if (!empty) {
                mAdapter.submitList(result);
            }
        });
    }
}
