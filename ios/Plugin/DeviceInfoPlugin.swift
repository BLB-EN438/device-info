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
    
    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
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
    
    @objc func getId(_ call: CAPPluginCall) {
        
        call.resolve([
            "value": implementation.echo("demo123456")
        ])
    }
    
    struct Info: Codable {
        let serial: String
        let ssid: String
        let batteryLevel:Int
        let platform: String
        let operatingSystem: String
        
        let model: String
        let manufacturer: String
        let osVersion: String
        let networkConnected: Bool
        let networkType: String
        let batteryCharging: Bool
    }
    
    @objc func get(_ call: CAPPluginCall) {
        
        print("Hello Bhargav! Fetching device info")
        
        UIDevice.current.isBatteryMonitoringEnabled = true
        var batteryLevel: Float { UIDevice.current.batteryLevel }
        
        print("blb charging ")
        print(UIDevice.current.batteryState)
        
        
        let device = UIDevice()
        
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
        
    }
        
        call.resolve([
            "results": [
                "serial": "i-12345",
                "ssid": "",
                "batteryLevel": batteryLevel*100,
                "platform": "Mobile",
                "operatingSystem": "IOS",
                "model": device.model,
                "manufacturer": "Apple Inc",
                "osVersion": device.systemVersion,
                "networkConnected": isConnectedToNetwork(),
                "networkType": networkType(),
                "batteryCharging":charging
            ]
        ])
        
    }
            
}

