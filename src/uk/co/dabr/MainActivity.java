package uk.co.dabr;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {

	private WebView wv;
	private boolean loading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		wv = new WebView(this);
		setContentView(wv);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				Log.e("dabr", description);
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				loading = true;
				getActionBar().setTitle(url);
				getActionBar().show();
				super.onPageStarted(view, url, favicon);
			}

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void onPageFinished(WebView view, String url) {
				loading = false;
				getActionBar().setTitle(view.getTitle());
				getActionBar().hide();
				super.onPageFinished(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		wv.setPictureListener(new PictureListener() {

			@Override
			public void onNewPicture(WebView arg0, Picture arg1) {
				loading = true;

			}
		});
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl(getResources().getString(R.string.home));

	}

	@Override
	public void onBackPressed() {
		if (loading) {
			wv.stopLoading();
			loading = false;
		} else {
			if (wv.canZoomOut()) {
				wv.zoomOut();
			} else {
				if (wv.canGoBack()) {
					wv.goBack();
				} else {
					finish();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().show();
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB
				&& !loading) {
			getActionBar().hide();
		}
		super.onOptionsMenuClosed(menu);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
