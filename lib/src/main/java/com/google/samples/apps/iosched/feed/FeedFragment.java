/*
 * Copyright (c) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.samples.apps.iosched.feed;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.samples.apps.iosched.feed.data.FeedMessage;
import com.google.samples.apps.iosched.lib.R;

import java.util.List;

public class FeedFragment extends Fragment implements FeedContract.View {

    private FeedContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private FeedAdapter mFeedAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.feed_fragment, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.feed_recycler_view);
        Point screenSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
        mFeedAdapter = new FeedAdapter(getContext(), screenSize);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mFeedAdapter);
        return root;
    }

    @Override
    public void setPresenter(FeedContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingFeedMessages(boolean loading) {}

    @Override
    public void updateFromDataset(List<FeedMessage> feedMessages) {
        mFeedAdapter.updateItems(feedMessages);
        mFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorText) {}
}