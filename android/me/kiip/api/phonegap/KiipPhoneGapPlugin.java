package me.kiip.api.phonegap;

import me.kiip.api.Kiip;

import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.phonegap.api.Plugin;

/**
 * @author nick
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
				
				// Kiip.init((Activity) this.ctx, API_KEY, API_SECRET);
				// Kiip.getInstance().startSession((Activity)this.ctx, null);
				
				this.ctx.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Kiip.init((Activity) ctx, API_KEY, API_SECRET);
						Kiip.getInstance().startSession((Activity) ctx, null);
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
				
				this.ctx.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
                                                if (Kiip.getInstance() == null) {
                                                }
                                                else {
						    Kiip.getInstance().endSession((Activity) ctx, null);
                                                }
					}
				});
				
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
