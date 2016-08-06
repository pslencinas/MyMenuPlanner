package com.example.android.myargmenuplanner;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.myargmenuplanner.data.FoodContract;
import com.squareup.picasso.Picasso;



public class FoodsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = FoodsFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private FoodsAdapter mFoodsAdapter;
    private RecyclerView mRecyclerView;

    private ShareActionProvider mShareActionProvider;

    private Uri mUri;

    private static final int DETAIL_LOADER = 0;

    private static final String[] FOOD_COLUMNS = {

            FoodContract.FoodEntry.TABLE_NAME + "." + FoodContract.FoodEntry._ID,

            FoodContract.FoodEntry.COLUMN_ID,
            FoodContract.FoodEntry.COLUMN_TITLE,
            FoodContract.FoodEntry.COLUMN_IMAGE_ID,
            FoodContract.FoodEntry.COLUMN_DESCRIPTION


    };

    static final int COL_ID = 1;
    static final int COL_TITLE = 2;
    static final int COL_IMG_ID = 3;
    static final int COL_DESCRIPTION = 4;


    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mDescriptionView;

    public static ListView mListView1;

    public static String title;
    public static String description;
    public static String food_id;
    public static String image_id;

    public FoodsFragment() {
        setHasOptionsMenu(true);
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri foodUri, Uri ingrUri, FoodsAdapter.FoodsAdapterViewHolder vh);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(FoodsFragment.DETAIL_URI);
        }

        View rootView = inflater.inflate(R.layout.fragment_food_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_food);
        // Set the layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFoodsAdapter = new FoodsAdapter(getActivity(), new FoodsAdapter.FoodsAdapterOnClickHandler() {
            @Override
            public void onClick(Long id, FoodsAdapter.FoodsAdapterViewHolder vh) {

                ((Callback) getActivity())
                        .onItemSelected(FoodContract.FoodEntry.buildFoodUri(id)
                                ,FoodContract.IngrEntry.buildIngrByFoodUri(id), vh );
            }
        });

        // specify an adapter (see also next example)
        mRecyclerView.setAdapter(mFoodsAdapter);

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    void onChanged( ) {

        //getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Log.i(LOG_TAG, "Dentro de onCreateLoader");

        mUri = FoodContract.FoodEntry.CONTENT_URI;

        return new CursorLoader(
                getActivity(),
                mUri,
                FOOD_COLUMNS,
                null,
                null,
                null
        );


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        if (data != null) {

            mFoodsAdapter.swapCursor(data);


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }




}