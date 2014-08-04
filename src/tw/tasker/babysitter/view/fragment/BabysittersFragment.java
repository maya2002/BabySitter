package tw.tasker.babysitter.view.fragment;

import tw.tasker.babysitter.R;
import tw.tasker.babysitter.presenter.BabysitterListPresenter;
import tw.tasker.babysitter.presenter.impl.BabysittersPresenterImpl;
import tw.tasker.babysitter.utils.ProgressBarUtils;
import tw.tasker.babysitter.view.BabysitterListView;
import tw.tasker.babysitter.view.activity.BabysitterMapActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class BabysittersFragment extends BaseFragment implements
		BabysitterListView  {

	private BabysitterListPresenter mPresenter;
	public int mPosition;

	public BabysittersFragment(int position) {
		mPosition = position;
	}

	public static Fragment newInstance(int position) {
		BabysittersFragment fragment = new BabysittersFragment(position);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mPresenter = new BabysittersPresenterImpl(this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.babysitter_map, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		switch (id) {
		case R.id.action_map:
			Intent intent = new Intent();
			intent.setClass(getActivity(), BabysitterMapActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mPresenter.onCreate();
	}

	@Override
	public void showProgress() {
		showLoading();
	}

	@Override
	public void hideProgress() {
		hideLoading();
	}

	@Override
	public void setAdapter(BaseAdapter adapter) {
		mGridView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mPresenter.onListItemClick(position);
	}

	@Override
	public void onRefreshStarted(View arg0) {
		mPresenter.refresh();
	}
}
