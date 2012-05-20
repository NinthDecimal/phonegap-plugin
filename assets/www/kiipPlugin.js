var Kiip = function() {	
};

Kiip.prototype.init = function(api_key, api_secret, successCallback, failureCallback) {
	return cordova.exec(	successCallback,
							failureCallback,
							'KiipPlugin',
							'init',
							[api_key, api_secret]);
};

Kiip.prototype.unlockAchievement = function(achievementKey, successCallback, failureCallback) {
	return cordova.exec( successCallback, failureCallback, 'KiipPlugin', 'unlockAchievement', [achievementKey]);
}

Kiip.prototype.saveLeaderboard = function(leaderboardKey, leaderboardScore, successCallback, failureCallback) {
	return cordova.exec(successCallback, failureCallback, 'KiipPlugin', 'saveLeaderboard', [leaderboardKey, leaderboardScore]);
}

Kiip.prototype.endSession = function(successCallback, failureCallback) {
	return cordova.exec(successCallback, failureCallback, 'KiipPlugin', 'endSession', []);
}

kiip = new Kiip();