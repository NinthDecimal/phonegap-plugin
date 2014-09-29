var Kiip = function() {
};

Kiip.prototype.init = function(api_key, api_secret, successCallback, failureCallback) {
	return cordova.exec(	successCallback,
				failureCallback,
				'KiipPlugin',
				'initializeKiip',
				[api_key, api_secret]);
};

Kiip.prototype.saveMoment = function(key, successCallback, failureCallback) {
	return cordova.exec(successCallback, failureCallback, 'KiipPlugin', 'saveMoment', [key]);
};

Kiip.prototype.startSession = function(successCallback, failureCallback) {
	return cordova.exec(successCallback, failureCallback, 'KiipPlugin', 'startSession', []);
};

Kiip.prototype.endSession = function(successCallback, failureCallback) {
	return cordova.exec(successCallback, failureCallback, 'KiipPlugin', 'endSession', []);
};

Kiip.prototype.listenContent = function(successCallback, failureCallback) {
	return cordova.exec(successCallback, failureCallback, 'KiipPlugin', 'onContent', []);
};

Kiip.prototype.listenToVideo = function(successCallback, failureCallback) {
	return corodova.exec(callback, 'KiipPlugin', 'onVideo', []);
};

kiip = new Kiip();
