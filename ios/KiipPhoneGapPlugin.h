//
//  KiipPhoneGapPlugin.h
//  PhoneGapTest
//
//  Created by Nick HS on 10/30/12.
//
//

#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>

@interface KiipPhoneGapPlugin : CDVPlugin

@property (retain, nonatomic) NSString *contentCallbackId;
@property (retain, nonatomic) NSString *swarmCallbackId;

@end