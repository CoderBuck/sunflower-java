package me.buck.sunflower_java.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.annimon.stream.Stream;

import java.util.Iterator;
import java.util.List;

import me.buck.sunflower_java.data.GardenPlanting;
import me.buck.sunflower_java.data.GardenPlantingRepository;
import me.buck.sunflower_java.data.PlantAndGardenPlantings;

/**
 * Created by gwf on 2019/6/19
 */
public class GardenPlantingListViewModel extends ViewModel {

    private GardenPlantingRepository mRepository;

    public GardenPlantingListViewModel(GardenPlantingRepository repository) {
        mRepository = repository;
    }

    public LiveData<List<GardenPlanting>> getGardenPlantings() {return mRepository.getGardenPlantings();}

    public LiveData<List<PlantAndGardenPlantings>> getPlantAndGardenPlantings() {
        return Transformations.map(mRepository.getPlantAndGardenPlantings(), plantings -> Stream.of(plantings)
                .filter(it -> !it.getGardenPlantings().isEmpty())
                .toList());
    }
}
