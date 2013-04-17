package me.kiip.api.phonegap;

import java.util.HashMap;
import java.util.Map;

import me.kiip.sdk.Kiip;
import me.kiip.sdk.Kiip.OnContentListener;
import me.kiip.sdk.Kiip.OnSwarmListener;
import me.kiip.sdk.Poptart;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.webkit.WebSettings.PluginState;

public class KiipPhoneGapPlugin extends Plugin implements OnSwarmListener, OnContentListener {
	static final String TAG = "KiipPhoneGapPlugin";

	String mContentCallbackID;
	String mSwarmCallbackID;

	String KIIP_APP_KEY;
	String KIIP_APP_SECRET;

	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		if (action.equals("initializeKiip")) {
			try {
				KIIP_APP_KEY = args.getString(0);
				KIIP_APP_SECRET = args.getString(1);
			} catch (JSONException e) {
				e.printStackTrace();
				return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			}

			Log.i(TAG, "About to init kiip");

			cordova.getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					Kiip k = Kiip.init(cordova.getActivity().getApplication(), KIIP_APP_KEY, KIIP_APP_SECRET);
					Kiip.setInstance(k);
				}
			});

			Log.e(TAG, "Kiip inited");

			PluginResult result = new PluginResult(PluginResult.Status.OK);
			return result;

		} else if (action.equals("startSession")) {
			return startSession();
		} else if (action.equals("endSession")) {
			return endSession();
		} else if (action.equals("saveMoment")) {
			final String momentName;

			try {
				momentName = args.getString(0);
			} catch (JSONException e) {
				e.printStackTrace();
				return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
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
			PluginResult result = new PluginResult(PluginResult.Status.OK);
			return result;
		} else if (action.equals("onContent")) {
			mContentCallbackID = callbackId;
			Kiip.getInstance().setOnContentListener(KiipPhoneGapPlugin.this);
			PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
			result.setKeepCallback(true);
			return result;
		} else if (action.equals("onSwarm")) {
			mSwarmCallbackID = callbackId;
			Kiip.getInstance().setOnSwarmListener(KiipPhoneGapPlugin.this);
			PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
			result.setKeepCallback(true);
			return result;
		} else {
			return new PluginResult(PluginResult.Status.INVALID_ACTION);
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
		this.success(result, mContentCallbackID);
	}

	@Override
	public void onSwarm(Kiip kiip, String id) {
		Log.i(TAG, "onSwarm called " + mSwarmCallbackID);
		Map<String, String> map = new HashMap<String, String>();
		map.put("swarmId", id);

		JSONObject data = new JSONObject(map);
		PluginResult result = new PluginResult(PluginResult.Status.OK, data);
		result.setKeepCallback(true);
		this.success(result, mSwarmCallbackID);
	}

}
