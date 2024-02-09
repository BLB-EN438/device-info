import Foundation
import Capacitor
import Combine
import Network
import SystemConfiguration

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(DeviceInfoPlugin)
public class DeviceInfoPlugin: CAPPlugin {
    private let implementation = DeviceInfo()
    
    func isConnectedToNetwork() -> Bool {

            var zeroAddress = sockaddr_in(sin_len: 0, sin_family: 0, sin_port: 0, sin_addr: in_addr(s_addr: 0), sin_zero: (0, 0, 0, 0, 0, 0, 0, 0))
            zeroAddress.sin_len = UInt8(MemoryLayout.size(ofValue: zeroAddress))
            zeroAddress.sin_family = sa_family_t(AF_INET)

            let defaultRouteReachability = withUnsafePointer(to: &zeroAddress) {
                $0.withMemoryRebound(to: sockaddr.self, capacity: 1) {zeroSockAddress in
                    SCNetworkReachabilityCreateWithAddress(nil, zeroSockAddress)
                }
            }

            var flags: SCNetworkReachabilityFlags = SCNetworkReachabilityFlags(rawValue: 0)
            if SCNetworkReachabilityGetFlags(defaultRouteReachability!, &flags) == false {
                return false
            }

            // Working for Cellular and WIFI
            let isReachable = (flags.rawValue & UInt32(kSCNetworkFlagsReachable)) != 0
            let needsConnection = (flags.rawValue & UInt32(kSCNetworkFlagsConnectionRequired)) != 0
            let ret = (isReachable && !needsConnection)

            return ret

        }
    func networkType() -> String{
        var zeroAddress = sockaddr_in(sin_len: 0, sin_family: 0, sin_port: 0, sin_addr: in_addr(s_addr: 0), sin_zero: (0, 0, 0, 0, 0, 0, 0, 0))
        zeroAddress.sin_len = UInt8(MemoryLayout.size(ofValue: zeroAddress))
        zeroAddress.sin_family = sa_family_t(AF_INET)

        let defaultRouteReachability = withUnsafePointer(to: &zeroAddress) {
            $0.withMemoryRebound(to: sockaddr.self, capacity: 1) {zeroSockAddress in
                SCNetworkReachabilityCreateWithAddress(nil, zeroSockAddress)
            }
        }

        var flags: SCNetworkReachabilityFlags = SCNetworkReachabilityFlags(rawValue: 0)
        if SCNetworkReachabilityGetFlags(defaultRouteReachability!, &flags) == false {
            return ""
        }
        //Only Working for WIFI
        let isReachable = flags == .reachable
        let needsConnection = flags == .connectionRequired
        
        if(isReachable && !needsConnection){
            return "WiFI"
        }else if(isConnectedToNetwork()){
            return "Mobile"
        }

        return ""
    }
    
    @objc func serialId(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": "i-serial"
        ])
    }
    @objc func batteryLevel(_ call: CAPPluginCall) {
        UIDevice.current.isBatteryMonitoringEnabled = true
        var batteryLevel: Float { UIDevice.current.batteryLevel }
        call.resolve([
            "value": batteryLevel*100
        ])
    }
    @objc func isBatteryCharging(_ call: CAPPluginCall) {
        UIDevice.current.isBatteryMonitoringEnabled = true
        var charging:Bool=false
        
        UIDevice.current.isBatteryMonitoringEnabled = true
        
        
        switch UIDevice.current.batteryState {
        case .unknown:
            charging=false
        case .charging:
            charging=true
        case .full:
            charging=true
        case .unplugged:
            charging=false
        default:
            charging=false
            call.resolve([
                "value": charging
            ])
        }
    }
    @objc func manufacturer(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": "Apple Inc"
        ])
    }
    @objc func model(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": UIDevice().model
        ])
    }
    @objc func operatingSystem(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": "IOS"
        ])
    }
    @objc func osVersion(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": UIDevice().systemVersion
        ])
    }
    @objc func platform(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": "Mobile"
        ])
    }
    @objc func isNetworkConnected(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": isConnectedToNetwork()
        ])
    }
    @objc func networkType(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": networkType()
        ])
    }
    @objc func wifiSSID(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": "i-wifi"
        ])
    }
    
}

