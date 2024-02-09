#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(DeviceInfoPlugin, "DeviceInfo",
           CAP_PLUGIN_METHOD(serialId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(batteryLevel, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isBatteryCharging, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(manufacturer, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(operatingSystem, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(osVersion, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(platform, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isNetworkConnected, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(networkType, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(wifiSSID, CAPPluginReturnPromise);
)
