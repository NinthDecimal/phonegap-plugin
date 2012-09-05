package me.kiip.api.phonegap;

import me.kiip.api.Kiip;

import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.phonegap.api.Plugin;

/**
 * @author Nick HS
 * @company Kiip Inc.
 * @website http://kiip.me/
 *
 */
public class KiipPhoneGapPlugin extends Plugin {
	public static final String TAG = "KiipPhoneGapPlugin";
	public static final String INIT = "init";
	public static final String ACHIEVEMENT = "unlockAchievement";
	public static final String LEADERBOARD = "saveLeaderboard";
	public static final String END_SESSION = "endSession";

	/* (non-Javadoc)
	 * @see org.apache.cordova.api.Plugin#execute(java.lang.String, org.json.JSONArray, java.lang.String)
	 */
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
		PluginResult result = null;
		
		if (action.equals(INIT)) {
			Log.d(TAG, "Initialzing and Starting Kiip Session");

			try {
				final String API_KEY = data.getString(0);
				final String API_SECRET = data.getString(1);
				
				this.cordova.getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Kiip.init(cordova.getActivity(), API_KEY, API_SECRET);
						Kiip.getInstance().startSession(cordova.getActivity(), null);
					}
				});
			    
			    result = new PluginResult(Status.OK);
			    
			} catch (JSONException e) {
				Log.e(TAG, "Failed to parse data passed in");
				result = new PluginResult(Status.JSON_EXCEPTION);
				e.printStackTrace();
			} catch (Exception e) {
				Log.e(TAG, "An uncaught exception occured");
				result = new PluginResult(Status.ERROR);
			}
		}
		
		else if (action.equals(ACHIEVEMENT)) {
			try {
				String ID = data.getString(0);
				Kiip.getInstance().unlockAchievement(ID, null);
			    result = new PluginResult(Status.OK);

			} catch (JSONException e) {
				Log.e(TAG, "Failed to parse data passed in");
				result = new PluginResult(Status.JSON_EXCEPTION);
			} catch (Exception e) {
				Log.e(TAG, "An uncaught exception occured");
				result = new PluginResult(Status.ERROR);
			}
		}
		
		else if (action.equals(LEADERBOARD)) {
			try {
				String ID = data.getString(0);
				int SCORE = data.getInt(1);
				Kiip.getInstance().saveLeaderboard(ID, SCORE, null);
			    result = new PluginResult(Status.OK);

			} catch (JSONException e) {
				Log.e(TAG, "Failed to parse data passed in");
				result = new PluginResult(Status.JSON_EXCEPTION);
			} catch (Exception e) {
				Log.e(TAG, "An uncaught exception occured");
				result = new PluginResult(Status.ERROR);
			}
		}
		
		else if (action.equals(END_SESSION)) {
			try {
				
				if (Kiip.getInstance() == null) {
				} else {
					Kiip.getInstance().endSession(cordova.getActivity(), null);
				}
				
				result = new PluginResult(Status.OK);
				
			} catch (Exception e) {
				Log.e(TAG, "An uncaught exception occured");
				result = new PluginResult(Status.ERROR);
			}
		}
		else {
			Log.e(TAG, "Invalid Command");
			result = new PluginResult(Status.INVALID_ACTION);
		}	
		return result;
	}

}
