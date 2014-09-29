package me.kiip.api.phonegap;

import java.util.HashMap;
import java.util.Map;

import me.kiip.sdk.Kiip;
import me.kiip.sdk.Kiip.OnContentListener;
import me.kiip.sdk.Poptart;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.webkit.WebSettings.PluginState;

public class KiipPhoneGapPlugin extends CordovaPlugin implements OnContentListener {
	static final String TAG = "KiipPhoneGapPlugin";

	public boolean hasRun = true;
	String mContentCallbackID;

	String KIIP_APP_KEY;
	String KIIP_APP_SECRET;

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
		if (action.equals("initializeKiip") && hasRun ) {
			try {
				KIIP_APP_KEY = args.getString(0);
				KIIP_APP_SECRET = args.getString(1);
			} catch (JSONException e) {
				e.printStackTrace();
				callbackContext.error("Invalid JSON args used. expected a string as the first arg.");
				return false;
			}

			hasRun = false;
			Log.i(TAG, "About to init kiip");

			cordova.getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Kiip k = Kiip.init(cordova.getActivity().getApplication(), KIIP_APP_KEY, KIIP_APP_SECRET);
					Kiip.setInstance(k);
				}
			});

			Log.i(TAG, "Kiip initialized");

			callbackContext.success();
			return true;

		} else if (action.equals("startSession")) {
			startSession();
			return true;
		} else if (action.equals("endSession")) {
			endSession();
			return true;
		} else if (action.equals("saveMoment")) {
			final String momentName;

			try {
				momentName = args.getString(0);
			} catch (JSONException e) {
				e.printStackTrace();
				callbackContext.error("JSON Exception");
				return false;
			}

			cordova.getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Kiip.getInstance().saveMoment(momentName, new Kiip.Callback() {

						@Override
						public void onFinished(Kiip kiip, Poptart poptart) {
							if (poptart != null) {
								Log.i(TAG, "Showing reward");
								poptart.show(cordova.getActivity());
						  } else {
								Log.i(TAG, "No reward to show");
							}
						}

						@Override
						public void onFailed(Kiip kiip, Exception exception) {
							Log.w(TAG, "Failed to load Kiip Reward");
							Log.w(TAG, "Exception=" + exception);
						}
					});
				}
			});
			callbackContext.success();
			return true;
		}
		else if (hasRun){
			callbackContext.error("Has Run");
			return false;
			}
		else {
			callbackContext.error("Invalid");
			return false;
		}
	}

	private PluginResult startSession() {
		Kiip.getInstance().startSession(null);
		PluginResult result = new PluginResult(PluginResult.Status.OK);
		return result;
	}

	private PluginResult endSession() {
		Kiip.getInstance().endSession(null);
		PluginResult result = new PluginResult(PluginResult.Status.OK);
		return result;
	}

	@Override
	public void onContent(Kiip kiip, String contentId, int amount, String transactionId,
			String signature) {
		Log.i(TAG, "onContent called" + mContentCallbackID);

		Map<String, String> map = new HashMap<String, String>();
		map.put("contentId", contentId);
		map.put("amount", Integer.toString(amount));
		map.put("transactionId", transactionId);
		map.put("signature", signature);

		JSONObject data = new JSONObject(map);
		PluginResult result = new PluginResult(PluginResult.Status.OK, data);
		result.setKeepCallback(true);
	}
}
