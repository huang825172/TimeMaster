package com.zzs.timemaster;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;
import com.zzs.timemaster.Tabs.FutureFragment;
import com.zzs.timemaster.Tabs.MineFragment;
import com.zzs.timemaster.Tabs.TodayFragment;
import com.zzs.timemaster.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;
    private final ArrayList<Pair<Integer, Integer>> tabs = new ArrayList<>();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        tabs.add(new Pair<>(R.string.TodayPageTitle, R.mipmap.today));
        tabs.add(new Pair<>(R.string.FuturePageTitle, R.mipmap.future));
        tabs.add(new Pair<>(R.string.MinePageTitle, R.mipmap.mine));

        binding.MainViewPager2.setAdapter(new MainPagerAdapter(this));

        new TabLayoutMediator(
                binding.MainTabLayout,
                binding.MainViewPager2,
                (tab, position) -> {
                    tab.setText(tabs.get(position).first);
                    tab.setIcon(tabs.get(position).second);
                }).attach();
    }

    @Override
    public void onBackPressed() {
        if (binding.MainViewPager2.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            binding.MainViewPager2.setCurrentItem(binding.MainViewPager2.getCurrentItem() - 1);
        }
    }

    private static class MainPagerAdapter extends FragmentStateAdapter {

        private final ArrayList<Fragment> fragments = new ArrayList<>();

        public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);

            fragments.add(new TodayFragment());
            fragments.add(new FutureFragment());
            fragments.add(new MineFragment());
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}