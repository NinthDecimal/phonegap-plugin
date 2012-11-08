//
//  KiipPhoneGapPlugin.m
//  PhoneGapTest
//
//  Created by Nick HS on 10/30/12.
//
//

#import "KiipPhoneGapPlugin.h"
#import <KiipSDK/KiipSDK.h>

@interface KiipPhoneGapPlugin() <KiipDelegate>

@end

@implementation KiipPhoneGapPlugin

- (id)init {
    self = [super init];
    if (self) {
    }
    return self;
}

- (void)dealloc {
    [self.contentCallbackId release];
    [self.swarmCallbackId release];
    [super dealloc];
}

- (void) initializeKiip:(CDVInvokedUrlCommand*)command
{
    NSString* APP_KEY = [command.arguments objectAtIndex:0];
    NSString* APP_SECRET = [command.arguments objectAtIndex:1];
    
    Kiip *kiip = [[Kiip alloc] initWithAppKey:APP_KEY andSecret:APP_SECRET];
    kiip.delegate = self;
    [Kiip setSharedInstance:kiip];
    NSLog(@"Kiip inited");
    
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    NSString* javascript = [pluginResult toSuccessCallbackString:command.callbackId];
    
    [self writeJavascript:javascript];
}

- (void) saveMoment:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* javaScript = nil;
    
    NSLog(@"Saving Moment");
    
    @try {
        NSString* momentId = [command.arguments objectAtIndex:0];
        
        [[Kiip sharedInstance] saveMoment:momentId withCompletionHandler:nil];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        javaScript = [pluginResult toSuccessCallbackString:command.callbackId];
    }
    @catch (NSException *exception) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_JSON_EXCEPTION
                                         messageAsString:[exception reason]];
        javaScript = [pluginResult toErrorCallbackString:command.callbackId];
    }
    
    [self writeJavascript:javaScript];
}

- (void) onContent:(CDVInvokedUrlCommand*)command
{
    NSLog(@"listenContent called");
    CDVPluginResult* pluginResult = nil;
    NSString* javaScript = nil;
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_NO_RESULT];
    [pluginResult setKeepCallbackAsBool:YES];
    self.contentCallbackId = command.callbackId;
    
    javaScript = [pluginResult toSuccessCallbackString:command.callbackId];
    [self writeJavascript:javaScript];
}

- (void) onSwarm:(CDVInvokedUrlCommand*)command
{
    NSLog(@"listenSwarm called");
    self.swarmCallbackId = command.callbackId;
    
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_NO_RESULT];
    [pluginResult setKeepCallbackAsBool:YES];
    NSString* javascript = [pluginResult toSuccessCallbackString:command.callbackId];
    [self writeJavascript:javascript];
}

- (void) kiip:(Kiip *)kiip didReceiveContent:(NSString *)content quantity:(int)quantity transactionId:(NSString *)transactionId signature:(NSString *)signature
{
    NSLog(@"CONTENT RECEIVED %@ %d %@ %@", content, quantity, transactionId, signature);
    NSDictionary* dict = [NSDictionary dictionaryWithObjectsAndKeys:
                          [NSString stringWithFormat:@"%d", quantity], @"quantity",
                          content, @"content",
                          transactionId, @"transactionId",
                          signature, @"signature",
                          nil];

    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dict];
    [pluginResult setKeepCallbackAsBool:YES];
    
    NSString* javascript = [pluginResult toSuccessCallbackString:self.contentCallbackId];
    [self writeJavascript:javascript];
}

- (void) kiip:(Kiip *)kiip didStartSwarm:(NSString *)momentId
{
    NSLog(@"Swarm started");
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:momentId];
    [pluginResult setKeepCallbackAsBool:YES];
    
    NSString* js = [pluginResult toSuccessCallbackString:self.swarmCallbackId];
    [self writeJavascript:js];
}

@end