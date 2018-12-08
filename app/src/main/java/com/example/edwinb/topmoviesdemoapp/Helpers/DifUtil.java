package com.example.edwinb.topmoviesdemoapp.Helpers;

import android.support.v7.util.DiffUtil;

import com.example.edwinb.topmoviesdemoapp.http.http.apimodel.Result;
import com.example.edwinb.topmoviesdemoapp.topmovies.ViewModel;

import java.util.List;

/********************************
 * This DiffUtil is for replacing the notify data set changed
 * for the recyclerview adapter, it's a more efficient way to
 * see the difference between two populated lists in a recyclerview
 * and avoid having to cause duplicate lists.
 */
public class DifUtil extends DiffUtil.Callback {

    private List<ViewModel> oldList;
    private List<ViewModel> newList;

    public DifUtil(List<ViewModel> oldList, List<ViewModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return oldList.get(i).getName().equals(newList.get(i1).getName());
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldList.get(i).equals(newList.get(i1));
    }
}
